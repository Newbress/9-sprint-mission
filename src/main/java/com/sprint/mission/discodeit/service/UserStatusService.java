package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.DTO.UserStatus.CreateUserStatusRequest;
import com.sprint.mission.discodeit.entity.DTO.UserStatus.UpdateUserStatusRequest;
import com.sprint.mission.discodeit.entity.UserStatus;

import java.util.List;
import java.util.UUID;

public interface UserStatusService {
    UserStatus create(CreateUserStatusRequest request);
    UserStatus find(UUID id);
    List<UserStatus> findAll();
    UserStatus update(UUID id, UpdateUserStatusRequest request);
    UserStatus updateByUserId(UUID userId, UpdateUserStatusRequest request);
    void delete(UUID id);
}
