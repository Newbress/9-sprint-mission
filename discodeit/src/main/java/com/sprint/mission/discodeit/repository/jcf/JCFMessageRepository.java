package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JCFMessageRepository implements MessageRepository {
    private final Map<UUID, Channel> data = new HashMap<>();

    @Override
    public Message saveMsg(Message msg) {
        return null;
    }

    @Override
    public Message findMsgId(String id) {
        return null;
    }

    @Override
    public Message getUserMsg(String userMsg) {
        return null;
    }

    @Override
    public Message getChannelMsg(String channelMsg) {
        return null;
    }

    @Override
    public List<Message> findAllMsgId() {
        return List.of();
    }

    @Override
    public List<Message> getUserAll(String inputUser) {
        return List.of();
    }

    @Override
    public List<Message> getChannelAll(String inputChannel) {
        return List.of();
    }

    @Override
    public Message editMsg(UUID id, String newContent) {
        return null;
    }

    @Override
    public boolean delMsg(UUID id) {
        return false;
    }
}
