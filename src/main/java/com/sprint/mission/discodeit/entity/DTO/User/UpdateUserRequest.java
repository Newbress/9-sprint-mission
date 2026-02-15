package com.sprint.mission.discodeit.entity.DTO.User;

import java.util.UUID;

public record UpdateUserRequest(
        String newUsername,
        String newEmail,
        String newPassword,
        UUID profileImage
) {
}
