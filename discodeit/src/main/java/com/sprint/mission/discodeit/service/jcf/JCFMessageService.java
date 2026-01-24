package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JCFMessageService implements MessageService {
    private final Map<UUID, Message> data = new HashMap<>();
    private final MessageRepository messageRepository;

    private final UserService userService;
    private final ChannelService channelService;

    public JCFMessageService (MessageRepository messageRepository, UserService userService, ChannelService channelService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.channelService = channelService;
    }

    @Override
    public Message sendMsg(String channelName, String userName, String content) {
        User author = userService.findUserName(userName);
        if(author == null) {
            throw new IllegalArgumentException("존재하지 않는 아이디");
        }
        Channel channel = channelService.findCh(channelName);
        if(channel == null) {
            throw new IllegalArgumentException("존재하지 않는 채널");
        }
        Message msg = new Message(channelName, userName, content);
        data.put(msg.getId(), msg);
        return msg;
    }

    @Override
    public Message findMsgId(String msgId) {
        return null;
    }

    @Override
    //유저의 메세지 조회
    public Message getUserMsg(String userMsg ) {
        for(Message msg : data.values()) {
            String userName = msg.getUserName();
            if(userName != null && userName.equals(userMsg))
                return msg;
        }
        return null;
    }
    @Override
    //채널의 메세지 조회
    public Message getChannelMsg(String channelMsg) {
        for(Message msg : data.values()) {
            String channelName = msg.getChannelName();
            if(channelName != null && channelName.equals(channelMsg)) {
                return msg;
            }
        }
        return null;
    }

    @Override
    public List<Message> findAllMstId() {
        return List.of();
    }

    @Override
    public List<Message> getUserAll(String inputUser) {
        for(Message msg : data.values())
        {
            String userName = msg.getUserName();
            if(userName != null && userName.equals(userName)) {
                return new ArrayList<>(data.values());
            }
        }
        return null;
    }

    @Override
    public List<Message> getChannelAll(String inputChannel) {
        for(Message msg : data.values()) {
            String channelName = msg.getChannelName();
            if(channelName != null && channelName.equals(channelName)) {
                return new ArrayList<>(data.values());
            }
        }
        return null;
    }


    @Override
    public Message editMsg(UUID id, String newContent) {
        Message msg = data.get(id);
        if(msg == null) {
            return null;
        } else {
            msg.update(newContent);
        }
        return null;
    }

    @Override
    public boolean delMsg(UUID id) {
        return false;
    }
}
