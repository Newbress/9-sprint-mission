package com.sprint.mission.discodeit.entity.DTO.BinaryContent;

import java.util.UUID;

public record BinaryContentCreateDTO(
        UUID id,
        UUID userId,
        UUID MessageId,
        byte[] content,
        String contentType
) {
}
