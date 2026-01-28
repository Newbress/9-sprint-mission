package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class basicMessageService implements MessageService {
    private final Map<UUID, User> data = new HashMap<>();
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;

    public basicMessageService(MessageRepository messageRepository, UserRepository userRepository, ChannelRepository channelRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.channelRepository = channelRepository;
    }


    @Override
    public Message sendMsg(String channelName, String userName, String content) {
        Message msg = new Message(channelName, userName, content);
        return messageRepository.saveMsg(msg);
    }

    @Override
    public Message findMsgId(String msgId) {
        return messageRepository.findMsgId(msgId);
    }

    @Override
    public Message getUserMsg(String userMsg) {
        return messageRepository.getUserMsg(userMsg);
    }

    @Override
    public Message getChannelMsg(String channelMsg) {
        return null;
    }

    @Override
    public List<Message> findAllMstId() {
        return messageRepository.findAllMsgId();
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
        return messageRepository.delMsg(id);
    }
}
