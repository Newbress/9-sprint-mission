package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.DTO.User.UserCreateDTO;
import com.sprint.mission.discodeit.entity.DTO.User.UserFindDTO;
import com.sprint.mission.discodeit.entity.DTO.User.UserUpdateDTO;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final BinaryContentRepository binaryContentRepository;

    @Override
    public User create(String username, String email, String password) {
        User user = new User(username, email, password);
        return userRepository.save(user);
    }

    @Override
    public User createDTO(UserCreateDTO dto) {
        if(userRepository.existByUsername(dto.username())){
            throw new IllegalArgumentException("이미 존재하는 사용자이름입니다.");
        }
        if(userRepository.existByEmail(dto.email())){
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        User user = new User(dto.username(), dto.email(), dto.password());

        if(dto.profileImage() != null){
            BinaryContent profile = new BinaryContent(
                    UUID.randomUUID(),
                    user.getId(),
                    null,
                    dto.profileImage().content(),
                    dto.profileImage().contentType()
            );
            binaryContentRepository.save(profile);
        }

        UserStatus userStatus = new UserStatus(user.getId(), Instant.now()); //생성시간
        userStatusRepository.save(userStatus);
        userRepository.save(user);
        return user;
    }

    @Override
    public User find(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
    }

    @Override
    public UserFindDTO findDTO(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id" + userId + "not found"));

        boolean isOnline = userStatusRepository.findId(userId)
                .map(UserStatus::isOnline)
                .orElse(false);

        return new UserFindDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                isOnline
        );
    }

    @Override
    public List<User> findAll()  {
        return userRepository.findAll();
    }

    @Override
    public List<UserFindDTO> findAllDTO() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> {
                    boolean isOnline = userStatusRepository.findId(user.getId())
                            .map(UserStatus::isOnline)
                            .orElse(false);

                    return new UserFindDTO(
                            user.getId(),
                            user.getUsername(),
                            user.getEmail(),
                            isOnline
                    );
                })
                .toList();
    }

    @Override
    public User update(UUID userId, String newUsername, String newEmail, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
        user.update(newUsername, newEmail, newPassword);
        return userRepository.save(user);
    }

    @Override
    public User updateDTO(UUID userId, UserUpdateDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
        user.update(dto.newUsername(), dto.newEmail(), dto.newPassword());

        if(dto.profileImage() != null){
            BinaryContent profile = new BinaryContent(
                    UUID.randomUUID(),  //Binary Content는 0과 1로 되어있는 원시 데이터라 수정이 아니라 덮어 쓰기임
                    user.getId(),
                    null,
                    dto.profileImage().content(),
                    dto.profileImage().contentType()
            );
            binaryContentRepository.save(profile);
        }
        userRepository.save(user);
        return user;
    }

    @Override
    public void delete(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new NoSuchElementException("User with id " + userId + " not found");
        }

        binaryContentRepository.deleteAllByUserId(userId);
        userStatusRepository.deleteAllByUserId(userId);
        userRepository.deleteById(userId);
    }
}
