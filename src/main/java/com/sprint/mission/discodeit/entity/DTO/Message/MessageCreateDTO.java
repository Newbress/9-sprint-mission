package com.sprint.mission.discodeit.entity.DTO.Message;

import com.sprint.mission.discodeit.entity.DTO.BinaryContent.AttachedFilesDTO;

import java.util.List;
import java.util.UUID;

public record MessageCreateDTO(
        String content,
        UUID userId,
        UUID channelId,
        List<AttachedFilesDTO> attachedFiles

) {}
