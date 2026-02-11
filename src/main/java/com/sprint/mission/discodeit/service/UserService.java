package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.DTO.User.UserCreateDTO;
import com.sprint.mission.discodeit.entity.DTO.User.UserFindDTO;
import com.sprint.mission.discodeit.entity.DTO.User.UserUpdateDTO;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User create(String username, String email, String password);
    User createDTO(UserCreateDTO dto);
    User find(UUID userId);
    UserFindDTO findDTO(UUID userId);
    List<User> findAll();
    List<UserFindDTO> findAllDTO();
    User update(UUID userId, String newUsername, String newEmail, String newPassword);
    User updateDTO(UUID userId, UserUpdateDTO dto);
    void delete(UUID userId);

}
