package com.sprint.mission.discodeit.entity.DTO.UserStatus;

import java.time.Instant;

public record UserStatusUpdateDTO(
        Instant lastConnection
) {
}
