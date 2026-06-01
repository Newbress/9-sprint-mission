package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.ReadStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadStatusRepository {
    ReadStatus save(ReadStatus readStatus);
    Optional<ReadStatus> findById(UUID id);
    Optional<ReadStatus> findByUserId(UUID userId);
    Optional<ReadStatus> findByChannelId(UUID channelId);
    List<ReadStatus> findAll();
    List<ReadStatus> findAllByUserId(UUID userId);
    Optional<ReadStatus> findUserIdByChannelId(UUID channelId);
    void deleteById(UUID id);
    void deleteAllByChannelId(UUID channelId);
    boolean existById(UUID id);
    boolean existByUserIdAndChannelId(UUID userId, UUID channelId);
}
