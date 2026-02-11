package com.sprint.mission.discodeit.entity.DTO.User;


import java.time.Instant;
import java.util.UUID;

public record UserFindDTO(
        UUID id,
        Instant createdAt,
        Instant updatedAt,
        String username,
        String email,
        UUID profileId,
        boolean isOnline
) {}
