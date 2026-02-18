package com.sprint.mission.discodeit.entity.DTO.User;

import java.util.UUID;


public record CreateUserRequest(
        String username,
        String email,
        String password,
        UUID profileImage
) {}
