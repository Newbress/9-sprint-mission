package com.sprint.mission.discodeit.dto.data;

import java.time.Instant;
import java.util.UUID;

public record ReadStatusDto(
   UUID id,
   UserDto user,
   ChannelDto channel,
   Instant lastReadAt
) {}
