package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.DTO.Message.MessageCreateDTO;
import com.sprint.mission.discodeit.entity.DTO.Message.MessageUpdateDTO;
import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    Message create(String content, UUID channelId, UUID authorId);
    Message createDTO(MessageCreateDTO dto);

    Message find(UUID messageId);
    List<Message> findAll();
    List<Message> findAllByChannelId(UUID channelId);

    Message update(UUID messageId, String newContent);
    Message updateDTO(UUID messageId, MessageUpdateDTO dto);

    void delete(UUID messageId);
}
