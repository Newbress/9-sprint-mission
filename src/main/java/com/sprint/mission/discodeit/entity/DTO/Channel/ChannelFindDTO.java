package com.sprint.mission.discodeit.entity.DTO.Channel;

import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record ChannelFindDTO(
        UUID id,
        List<UUID> userIds,
        ChannelType type,
        String name,
        String description,
        Optional<Message> lastMessageCreatedAT
) {}
