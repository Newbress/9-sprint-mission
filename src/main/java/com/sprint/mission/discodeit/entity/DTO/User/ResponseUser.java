package com.sprint.mission.discodeit.entity.DTO.User;


import java.time.Instant;
import java.util.UUID;

public record ResponseUser(
        UUID id,
        Instant createdAt,
        Instant updatedAt,
        String username,
        String email,
        UUID profileId,
        Boolean online
) {}
