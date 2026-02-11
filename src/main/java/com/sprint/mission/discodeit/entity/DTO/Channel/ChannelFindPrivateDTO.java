package com.sprint.mission.discodeit.entity.DTO.Channel;

import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record ChannelFindPrivateDTO(
        UUID id,
        List<UUID> userIds,
        String name,
        String description,
        Optional<Message> lastMessageCreatedAT

) {
}
