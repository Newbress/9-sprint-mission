package com.sprint.mission.discodeit.entity.DTO.Message;

import com.sprint.mission.discodeit.entity.DTO.BinaryContent.AttachedFiles;

import java.util.List;

public record UpdateMessageRequest(
        String content,
        List<AttachedFiles> attachedFiles
) {
}
