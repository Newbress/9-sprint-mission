package com.sprint.mission.discodeit.entity.DTO.Channel;

import java.util.List;
import java.util.UUID;

public record ChannelCreatePrivateDTO(
        List<UUID> userIds
) {}
