package com.sprint.mission.discodeit.entity.DTO.Message;

import com.sprint.mission.discodeit.entity.DTO.BinaryContent.AttachedFiles;

import java.util.List;
import java.util.UUID;

public record CreateMessageRequest(
        String content,
        UUID userId,
        UUID channelId,
        List<AttachedFiles> attachedFiles

) {}
