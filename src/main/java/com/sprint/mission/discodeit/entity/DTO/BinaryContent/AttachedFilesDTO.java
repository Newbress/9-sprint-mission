package com.sprint.mission.discodeit.entity.DTO.BinaryContent;

public record AttachedFilesDTO(
        String contentType,
        byte[] bytes
) {
}
