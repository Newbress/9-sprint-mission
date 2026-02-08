package com.sprint.mission.discodeit.entity.DTO.Message;

import com.sprint.mission.discodeit.entity.DTO.BinaryContent.AttachedFilesDTO;

import java.util.List;

public record MessageUpdateDTO(
        String content,
        List<AttachedFilesDTO> attachedFiles
) {
}
