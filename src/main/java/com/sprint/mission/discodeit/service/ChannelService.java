package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.DTO.Channel.*;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    Channel create(CreateChannelRequest request);
    Channel createPrivate(CreatePrivateChannelRequest request);

    ResponseChannel find(UUID channelId);
    List<Channel> findChannelByUserID(UUID userId);

    List<Channel> findAll();
    List<ResponseChannel> findAllDTO(UUID userId);


    Channel update(UUID id, UpdateChannelRequest request);

    void delete(UUID channelId);
}
