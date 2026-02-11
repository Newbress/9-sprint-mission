package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.DTO.ReadStatus.ReadStatusCreateDTO;
import com.sprint.mission.discodeit.entity.DTO.ReadStatus.ReadStatusResponseDTO;
import com.sprint.mission.discodeit.entity.DTO.ReadStatus.ReadStatusUpdateDTO;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.service.ReadStatusService;

import java.util.List;
import java.util.UUID;


public class FileReadStatusService implements ReadStatusService {

    @Override
    public ReadStatus createDTO(ReadStatusCreateDTO dto) {
        return null;
    }

    @Override
    public ReadStatus find(UUID id) {
        return null;
    }

    @Override
    public List<ReadStatus> findAllByUserId(UUID UserId) {
        return List.of();
    }

    @Override
    public ReadStatus updateDTO(UUID id, ReadStatusUpdateDTO dto) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
