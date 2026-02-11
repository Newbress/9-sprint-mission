package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.DTO.UserStatus.UserStatusCreateDTO;
import com.sprint.mission.discodeit.entity.DTO.UserStatus.UserStatusResponseDTO;
import com.sprint.mission.discodeit.entity.DTO.UserStatus.UserStatusUpdateDTO;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.service.UserStatusService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JCFUserStatusService implements UserStatusService {
    private final Map<UUID, UserStatus> data;
    public JCFUserStatusService() {
        this.data = new HashMap<>();
    }

    @Override
    public UserStatus createDTO(UserStatusCreateDTO dto) {
        return null;
    }

    @Override
    public UserStatus find(UUID id) {
        return null;
    }

    @Override
    public List<UserStatus> findAll() {
        return List.of();
    }

    @Override
    public UserStatus updateDTO(UUID id, UserStatusUpdateDTO dto) {
        return null;
    }

    @Override
    public UserStatus updateByUserId(UUID userId, UserStatusUpdateDTO dto) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
