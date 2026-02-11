package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@ConditionalOnProperty(name = "discodeit.repository.type",havingValue = "jcf", matchIfMissing = true)
public class JCFReadStatusRepository implements ReadStatusRepository {
    private final Map<UUID, ReadStatus> data;

    public JCFReadStatusRepository() {
        this.data = new HashMap<>();
    }

    @Override
    public ReadStatus save(ReadStatus readStatus) {
        this.data.put(readStatus.getId(),readStatus);
        return readStatus;
    }

    @Override
    public Optional<ReadStatus> findById(UUID id) {
        return Optional.ofNullable(this.data.get(id));
    }

    @Override
    public Optional<ReadStatus> findByUserId(UUID userId) {
        return Optional.ofNullable(this.data.get(userId));
    }

    @Override
    public Optional<ReadStatus> findByChannelId(UUID channelId) {
        return Optional.ofNullable(this.data.get(channelId));
    }

    @Override
    public List<ReadStatus> findAll() {
        return this.data.values().stream().toList();
    }

    @Override
    public List<ReadStatus> findAllByUserId(UUID userId) {
        return this.data.values().stream()
                .filter(readStatus -> readStatus.getUserId().equals(userId))
                .toList();
    }

    @Override
    public Optional<ReadStatus> findUserIdByChannelId(UUID channelId) {
        return this.data.values().stream()
                .filter(readStatus -> readStatus.getChannelId().equals(channelId))
                .max(Comparator.comparing(ReadStatus::getCreatedAt));
    }

    @Override
    public void deleteById(UUID id) {
        this.data.remove(id);

    }

    @Override
    public void deleteAllByChannelId(UUID channelId) {
        this.data.values().removeIf(readStatus -> readStatus.getChannelId().equals(channelId));
    }

    @Override
    public boolean existById(UUID id) {
        return this.data.containsKey(id);
    }

    @Override
    public boolean existByUserIdAndChannelId(UUID userId, UUID channelId) {
        var entity = this.data.get(userId);
        if(entity == null){
            return false;
        }
        return entity.getUserId().equals(channelId);
    }
}
