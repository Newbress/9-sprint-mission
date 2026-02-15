package com.sprint.mission.discodeit.entity.DTO.BinaryContent;

public record AttachedFiles(
        String contentType,
        byte[] bytes
) {
}
