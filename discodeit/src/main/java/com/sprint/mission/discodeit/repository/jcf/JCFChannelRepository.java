package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.util.*;

public class JCFChannelRepository implements ChannelRepository {
    private final Map<UUID, Channel> data = new HashMap<>();

    @Override
    public Channel saveCh(Channel channel) {
        data.put(channel.getId(), channel);
        return channel;
    }


    @Override
    public Channel findCh(String channelName) {
        for(Channel channel : data.values()) {
            channel.findChannelName();
            if(channel.findChannelName().equals(channelName)) {
                return channel;
            }
        }
        throw new IllegalArgumentException("해당 채널 없습니다.");
    }

    @Override
    public List<Channel> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Channel editCh(UUID id, String newChatroom) {
        Channel ch = data.get(id);
        if(ch == null) {
            return null;
        } else{
            ch.update(newChatroom);
            return ch;
        }
    }

    @Override
    public boolean delCh(UUID id) {
        return data.remove(id) != null;
    }
}
