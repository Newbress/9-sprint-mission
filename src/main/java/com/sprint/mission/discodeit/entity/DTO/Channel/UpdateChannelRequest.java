package com.sprint.mission.discodeit.entity.DTO.Channel;

public record UpdateChannelRequest(
        String newName,
        String newDescription
) {}
