package com.sprint.mission.discodeit.service.jcf;

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

import java.util.*;

@RequiredArgsConstructor
public class JCFUserService implements UserService {
    private final Map<UUID, User> data = new HashMap<>();
    private final UserStatusRepository userStatusRepository;
    private final BinaryContentRepository binaryContentRepository;

    @Override
    public User create(String username, String email, String password) {
        User user = new User(username, email, password);
        this.data.put(user.getId(), user);

        return user;
    }

    @Override
    public User createDTO(UserCreateDTO userCreateDTO) {
        User user = new User(userCreateDTO.username(),userCreateDTO.email(), userCreateDTO.password());
        this.data.put(user.getId(),user);
        return user;
    }

    @Override
    public User find(UUID userId) {
        User userNullable = this.data.get(userId);

        return Optional.ofNullable(userNullable)
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
    }

    @Override
    public UserFindDTO findDTO(UUID userId) {
        User userNullable = this.data.get(userId);
        Optional.ofNullable(userNullable)
                .orElseThrow(()->new NoSuchElementException("User with id " + userId + " not found"));
        boolean isOnline = userStatusRepository.findId(userId)
                .map(UserStatus::isOnline)
                .orElse(false);

        return new UserFindDTO(
                userNullable.getId(),
                userNullable.getUsername(),
                userNullable.getEmail(),
                isOnline
        );
    }

    @Override
    public List<User> findAll() {
        return this.data.values().stream().toList();
    }

    @Override
    public List<UserFindDTO> findAllDTO() {
        return this.data.values().stream()
                .map(user -> {
                    Optional<UserStatus> userStatus = userStatusRepository.findByUserId(user.getId());
                    boolean isOnline = userStatus
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
        User userNullable = this.data.get(userId);
        User user = Optional.ofNullable(userNullable)
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
        user.update(newUsername, newEmail, newPassword);

        return user;
    }

    @Override
    public User updateDTO(UUID userId, UserUpdateDTO dto) {
        User userNullable = this.data.get(userId);
        User user = Optional.ofNullable(userNullable)
                .orElseThrow(()-> new NoSuchElementException("User with id " + userId + " not found"));
        user.update(dto.newUsername(),dto.newEmail(), dto.newPassword());

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

        return user;
    }

    @Override
    public void delete(UUID userId) {
        if (!this.data.containsKey(userId)) {
            throw new NoSuchElementException("User with id " + userId + " not found");
        }
        this.data.remove(userId);
    }
}
