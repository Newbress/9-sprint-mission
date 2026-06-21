package com.sprint.mission.discodeit.entity.DTO.ReadStatus;

import java.time.Instant;

public record UpdateReadStatusRequest(
        Instant lastRead
) {}
