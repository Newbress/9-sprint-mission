package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ChannelRepository {
    Channel save(Channel channel);
    Optional<Channel> findById(UUID id);
    List<Channel> findChannelByUserId(UUID userId);
    List<Channel> findAll();
    List<Channel> findAllByType(ChannelType type);
    List<Channel> findAllById(Set<UUID> channelId);
    boolean existsById(UUID id);
    void deleteById(UUID id);
}
