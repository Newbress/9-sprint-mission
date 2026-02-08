package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.DTO.BinaryContent.BinaryContentCreateDTO;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface BinaryContentService {
    BinaryContent createDTO(BinaryContentCreateDTO dto);
    BinaryContent find(UUID id);
    List<BinaryContent> findAllByIdIn(Set<UUID> id);
    void delete(UUID id);
}
