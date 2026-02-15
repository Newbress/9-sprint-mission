package com.sprint.mission.discodeit.entity.DTO.UserStatus;

import java.time.Instant;
import java.util.UUID;

public record CreateUserStatusRequest(
        UUID id,
        UUID userId,
        Instant lastConnection
) {
}
