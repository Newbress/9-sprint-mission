package com.sprint.mission.discodeit.entity.DTO.User;


import java.util.UUID;

public record UserFindDTO(
        UUID id,
        String username,
        String email,
        boolean isOnline
) {}
