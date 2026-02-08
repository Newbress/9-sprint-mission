package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.DTO.Channel.*;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    Channel create(ChannelType type, String name, String description);
    Channel createDTO(ChannelCreateDTO dto);
    Channel createPrivateDTO(ChannelCreatePrivateDTO dto);

    Channel find(UUID channelId);
    ChannelFindDTO findDTO(UUID channelId);

    List<Channel> findAll();
    List<ChannelFindDTO> findAllDTO(UUID userId);

    Channel update(UUID channelId, String newName, String newDescription);
    ChannelFindDTO updateDTO(UUID id,ChannelUpdateDTO dto);

    void delete(UUID channelId);
}
