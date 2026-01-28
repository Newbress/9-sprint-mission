package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class basicChannelService implements ChannelService {
    private final Map<UUID, User> data = new HashMap<>();
    private final ChannelRepository channelRepository;

    public basicChannelService(ChannelRepository channelRepository){
        this.channelRepository = channelRepository;
    }

    @Override
    public Channel addCh(String inputchannelname) {
        Channel channel = new Channel(inputchannelname);
        return channelRepository.saveCh(channel);
    }

    @Override
    public Channel findCh(String channelName) {
        return channelRepository.findCh(channelName);
    }

    @Override
    public List<Channel> findAll() {
        return channelRepository.findAll();
    }

    @Override
    public Channel editCh(UUID id, String newChatroom) {
        Channel channel = new Channel(newChatroom);
        return channelRepository.saveCh(channel);
    }

    @Override
    public boolean delCh(UUID id) {
        return channelRepository.delCh(id);
    }
}
