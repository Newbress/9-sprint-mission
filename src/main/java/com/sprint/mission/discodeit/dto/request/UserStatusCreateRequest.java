package com.sprint.mission.discodeit.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.UUID;

@Schema(description = "수정할 User 정보")
public record UserStatusCreateRequest(
    UUID userId,
    Instant lastActiveAt
) {

}
