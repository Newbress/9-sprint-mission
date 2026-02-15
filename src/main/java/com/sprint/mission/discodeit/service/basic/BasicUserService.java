package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.DTO.BinaryContent.CreateBinaryContentRequest;
import com.sprint.mission.discodeit.entity.DTO.User.CreateUserRequest;
import com.sprint.mission.discodeit.entity.DTO.User.ResponesUser;
import com.sprint.mission.discodeit.entity.DTO.User.UpdateUserRequest;
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
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final BinaryContentRepository binaryContentRepository;


    @Override
    public User create(CreateUserRequest request, UUID profileImageId) {
        if(userRepository.existByUsername(request.username())){
            throw new IllegalArgumentException("이미 존재하는 사용자이름입니다.");
        }
        if(userRepository.existByEmail(request.email())){
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        User user = new User(request.username(), request.email(), request.password());
        user.updateProfile(profileImageId);


        UserStatus userStatus = new UserStatus(user.getId(), Instant.now()); //생성시간
        userStatusRepository.save(userStatus);
        userRepository.save(user);
        return user;
    }


    @Override
    public ResponesUser find(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id" + userId + "not found"));

        boolean isOnline = userStatusRepository.findId(userId)
                .map(UserStatus::isOnline)
                .orElse(false);

        return new ResponesUser(
                user.getId(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getUsername(),
                user.getEmail(),
                user.getProfileId(),
                isOnline
        );
    }


    @Override
    public List<ResponesUser> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> {
                    boolean isOnline = userStatusRepository.findId(user.getId())
                            .map(UserStatus::isOnline)
                            .orElse(false);

                    return new ResponesUser(
                            user.getId(),
                            user.getCreatedAt(),
                            user.getUpdatedAt(),
                            user.getUsername(),
                            user.getEmail(),
                            user.getProfileId(),
                            isOnline
                    );
                })
                .toList();
    }

    @Override
    public User update(UUID userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
        user.update(request.newUsername(), request.newEmail(), request.newPassword());

        if(request.profileImage() != null){
            binaryContentRepository.findId(request.profileImage())
                    .orElseThrow(()-> new IllegalArgumentException("존재 하지 않음"));
            user.updateProfile(request.profileImage());
        }
        userRepository.save(user);
        return user;
    }

    @Override
    public void delete(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));

        Optional.ofNullable(user.getProfileId())
                .ifPresent(binaryContentRepository::deleteById);
        userStatusRepository.deleteAllByUserId(userId);
        userRepository.deleteById(userId);
    }
}
