package com.sprint.mission.discodeit.entity.DTO.BinaryContent;

public record ProfileImageDTO(
        byte[] content,
        String contentType
) {
}
