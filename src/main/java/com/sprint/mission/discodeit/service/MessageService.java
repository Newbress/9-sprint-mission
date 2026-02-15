package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.DTO.Message.CreateMessageRequest;
import com.sprint.mission.discodeit.entity.DTO.Message.UpdateMessageRequest;
import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    Message create(CreateMessageRequest request);
    Message find(UUID messageId);
    List<Message> findAll();
    List<Message> findAllByChannelId(UUID channelId);
    Message update(UUID messageId, UpdateMessageRequest request);
    void delete(UUID messageId);
}
