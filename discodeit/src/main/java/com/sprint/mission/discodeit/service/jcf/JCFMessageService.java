package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;

import com.sprint.mission.discodeit.entity.User;
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

    private final UserService userService;
    private final ChannelService channelService;

    public JCFMessageService (UserService userService, ChannelService channelService) {
        this.userService = userService;
        this.channelService = channelService;
    }

    @Override
    public Message sendMsg(UUID chatRoomid, UUID authorid, String content) {
        User author = userService.getUser(authorid);
        if(author == null) {
            throw new IllegalArgumentException("존재하지 않는 아이디");
        }
        Channel channel = channelService.getCh(chatRoomid);
        if(channel == null) {
            throw new IllegalArgumentException("존재하지 않는 채널");
        }
        Message msg = new Message(chatRoomid, authorid, content);
        data.put(msg.getId(), msg);
        return msg;
    }

    @Override
    public Message getMsg(UUID id) {
        return data.get(id);
    }

    @Override
    public List<Message> getall() {
        return new ArrayList<>(data.values());
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
