package com.sprint.mission.discodeit.entity.DTO.BinaryContent;

import java.util.UUID;

public record CreateBinaryContentRequest(
        String fileName,
        String contentType,
        byte[] data
) {
}
