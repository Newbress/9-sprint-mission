package com.sprint.mission.discodeit.entity.DTO.User;

import com.sprint.mission.discodeit.entity.DTO.BinaryContent.ProfileImageDTO;


public record UserCreateDTO(
        String username,
        String email,
        String password,
        ProfileImageDTO profileImage

) {}
