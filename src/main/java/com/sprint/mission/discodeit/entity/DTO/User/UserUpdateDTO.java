package com.sprint.mission.discodeit.entity.DTO.User;

import com.sprint.mission.discodeit.entity.DTO.BinaryContent.ProfileImageDTO;

public record UserUpdateDTO(
        String newUsername,
        String newEmail,
        String newPassword,
        ProfileImageDTO profileImage
) {
}
