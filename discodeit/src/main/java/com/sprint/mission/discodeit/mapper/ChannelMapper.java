package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.data.ChannelDto;
import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelMapper {

  private final ReadStatusRepository readStatusRepository;
  private final MessageRepository messageRepository;
  private final UserMapper userMapper;

  public ChannelDto toDto(Channel channel) {

    List<UserDto> participantIds = new ArrayList<>();
    if (channel.getType().equals(ChannelType.PRIVATE)) {
       readStatusRepository.findAllByChannelId(channel.getId())
          .stream()
           .map(ReadStatus::getUser)
           .map(userMapper::toDto)
           .forEach(participantIds::add);


    } //TODO 아마 userMapper를 사용해야 하는거 같음

    Instant lastMessageAt = messageRepository.findTopByChannelIdOrderByCreatedAtDesc(channel.getId())
        .stream()
        .sorted(Comparator.comparing(Message::getCreatedAt).reversed())
        .map(Message::getCreatedAt)
        .limit(1)
        .findFirst()
        .orElse(Instant.MIN);

    return new ChannelDto(
        channel.getId(),
        channel.getType(),
        channel.getName(),
        channel.getDescription(),
        participantIds,
        lastMessageAt
    );
  }
}
