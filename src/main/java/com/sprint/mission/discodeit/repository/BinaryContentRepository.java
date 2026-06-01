package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.BinaryContent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BinaryContentRepository {
    BinaryContent save(BinaryContent content);
    Optional<BinaryContent> findId(UUID id);
    Optional<BinaryContent> findUserId(UUID userId);
    Optional<BinaryContent> findMessageId(UUID messageId);
    List<BinaryContent> findAll();
    boolean existsById(UUID id);
    void deleteById(UUID id);
    //void deleteAllByUserId(UUID userId);
    //void deleteAllByMessageId(UUID messageId);
}
