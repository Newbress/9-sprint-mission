package com.sprint.mission.discodeit.entity.DTO.BinaryContent;

public record ProfileImage(
        String contentType,
        byte[] data
) {
}
