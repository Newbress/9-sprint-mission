package com.sprint.mission.discodeit.entity.DTO.User;

import com.sprint.mission.discodeit.entity.DTO.BinaryContent.ProfileImageDTO;

import java.util.UUID;

public record UpdateUserRequest(
        String newUsername,
        String newEmail,
        String newPassword,
        UUID profileImage
) {
}
