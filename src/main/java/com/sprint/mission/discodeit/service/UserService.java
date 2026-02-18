package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.DTO.User.CreateUserRequest;
import com.sprint.mission.discodeit.entity.DTO.User.ResponseUser;
import com.sprint.mission.discodeit.entity.DTO.User.UpdateUserRequest;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User create(CreateUserRequest request, UUID profileImageId);
    ResponseUser find(UUID userId);
    List<ResponseUser> findAll();
    User update(UUID userId, UpdateUserRequest request);
    void delete(UUID userId);

}
