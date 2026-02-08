package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.DTO.UserStatus.UserStatusCreateDTO;
import com.sprint.mission.discodeit.entity.DTO.UserStatus.UserStatusResponseDTO;
import com.sprint.mission.discodeit.entity.DTO.UserStatus.UserStatusUpdateDTO;
import com.sprint.mission.discodeit.entity.UserStatus;

import java.util.List;
import java.util.UUID;

public interface UserStatusService {
    UserStatus createDTO(UserStatusCreateDTO dto);
    UserStatus find(UUID id);
    List<UserStatus> findAll();
    UserStatusResponseDTO updateDTO(UUID id,UserStatusUpdateDTO dto);
    UserStatusResponseDTO updateByUserId(UUID userId, UserStatusUpdateDTO dto);
    void delete(UUID id);
}
