package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.DTO.BinaryContent.CreateBinaryContentRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface BinaryContentService {
    BinaryContent create(CreateBinaryContentRequest request);
    BinaryContent find(UUID id);
    List<BinaryContent> findAll(UUID id);
    List<BinaryContent> findAllByIdIn(Set<UUID> id);
    void delete(UUID id);
    BinaryContent uploadFile(MultipartFile file);
}
