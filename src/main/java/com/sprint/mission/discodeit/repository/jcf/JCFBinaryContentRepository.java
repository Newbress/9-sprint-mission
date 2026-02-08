package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
@ConditionalOnProperty(name = "discodeit.repository.type",havingValue = "jcf", matchIfMissing = true)
public class JCFBinaryContentRepository implements BinaryContentRepository {
    private final Map<UUID, BinaryContent> data;

    public JCFBinaryContentRepository() {
        this.data = new HashMap<>();
    }

    @Override
    public BinaryContent save(BinaryContent content) {
        this.data.put(content.getId(), content);
        return content;
    }

    @Override
    public Optional<BinaryContent> findId(UUID id) {
        return Optional.ofNullable(this.data.get(id));
    }

    @Override
    public Optional<BinaryContent> findUserId(UUID userId) {
        return Optional.ofNullable(this.data.get(userId));
    }

    @Override
    public Optional<BinaryContent> findMessageId(UUID messageId) {
        return Optional.ofNullable(this.data.get(messageId));
    }

    @Override
    public List<BinaryContent> findAll() {
        return this.data.values().stream().toList();
    }

    @Override
    public boolean existsById(UUID id) {
        return this.data.containsKey(id);
    }

    @Override
    public void deleteById(UUID id) {
        this.data.remove(id);
    }

    @Override
    public void deleteAllByUserId(UUID userId) {
        this.data.remove(userId);
    }

    @Override
    public void deleteAllByMessageId(UUID messageId) {
        this.data.remove(messageId);
    }
}
