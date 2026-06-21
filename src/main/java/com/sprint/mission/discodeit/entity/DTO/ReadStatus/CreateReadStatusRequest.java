package com.sprint.mission.discodeit.entity.DTO.ReadStatus;

import java.time.Instant;
import java.util.UUID;

public record CreateReadStatusRequest(
        UUID id,
        UUID userId,
        UUID channelId,
        Instant lastRead
) {
}
