package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.DTO.ReadStatus.ReadStatusCreateDTO;
import com.sprint.mission.discodeit.entity.DTO.ReadStatus.ReadStatusResponseDTO;
import com.sprint.mission.discodeit.entity.DTO.ReadStatus.ReadStatusUpdateDTO;
import com.sprint.mission.discodeit.entity.ReadStatus;

import java.util.List;
import java.util.UUID;

public interface ReadStatusService {
    ReadStatus createDTO(ReadStatusCreateDTO dto);
    ReadStatus find(UUID id);
    List<ReadStatus> findAllByUserId(UUID userId);
    ReadStatus updateDTO(UUID id, ReadStatusUpdateDTO dto);
    void delete(UUID id);
}
