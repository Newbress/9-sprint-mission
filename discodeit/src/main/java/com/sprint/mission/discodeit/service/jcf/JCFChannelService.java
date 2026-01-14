package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JCFChannelService implements ChannelService {
    private final Map<UUID, Channel> data = new HashMap<>();

    @Override
    public Channel addCh(Channel ch) {
        return data.put(ch.getId(),ch);
    }

    @Override
    public Channel getCh(UUID id) {
        return data.get(id);
    }

    @Override
    public List<Channel> getall() {
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
