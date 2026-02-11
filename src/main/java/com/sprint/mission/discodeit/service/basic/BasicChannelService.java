package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.DTO.Channel.*;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {
    private final ChannelRepository channelRepository;
    private final MessageRepository messageRepository;
    private final ReadStatusRepository readStatusRepository;

    @Override
    public Channel create(ChannelType type, String name, String description) {
        Channel channel = new Channel(type, name, description);
        return channelRepository.save(channel);
    }

    //public
    @Override
    public Channel createDTO(ChannelCreateDTO dto) {
        Channel publicChannel = new Channel(ChannelType.PUBLIC,dto.name(),dto.description());
        return channelRepository.save(publicChannel);

    }

    //private
    @Override
    public Channel createPrivateDTO(ChannelCreatePrivateDTO dto) {
        Channel privateChannel = new Channel(ChannelType.PRIVATE,null,null);
        List<UUID> userIds = dto.userIds();
        for(UUID userId : userIds){
            ReadStatus readStatus = new ReadStatus(
                    userId,
                    privateChannel.getId(),
                    Instant.now()
            );
            readStatusRepository.save(readStatus);
        }

        return channelRepository.save(privateChannel);
    }

    @Override
    public Channel find(UUID channelId) {
        return channelRepository.findById(channelId)
                .orElseThrow(() -> new NoSuchElementException("Channel with id " + channelId + " not found"));
    }

    @Override
    public ChannelFindDTO findDTO(UUID id) {
        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Channel with id " + id + " not found"));
        Optional<Message> lastMessageCreatedAt = messageRepository.findLastMessageByChannelId(id);

        //private
        List<UUID> userIds = null;
        if(channel.getType() == ChannelType.PRIVATE){
            userIds = readStatusRepository.findUserIdByChannelId(id).stream()
                    .map(ReadStatus::getUserId)
                    .collect(Collectors.toList());

        }
        return new ChannelFindDTO(
                channel.getId(),
                userIds,
                channel.getType(),
                channel.getName(),
                channel.getDescription(),
                lastMessageCreatedAt
        );
    }

    @Override
    public List<Channel> findChannelByUserID(UUID userId) {
        List<UUID> readStatus = readStatusRepository.findAllByUserId(userId).stream()
                .map(ReadStatus::getChannelId)
                .toList();
        return channelRepository.findAll().stream()
                .filter(channel -> channel.getType().equals(ChannelType.PUBLIC)
                        || readStatus.contains(channel.getId()))
                .toList();
        }


    @Override
    public List<Channel> findAll() {
        return channelRepository.findAll();
    }

    @Override
    public List<ChannelFindDTO> findAllDTO(UUID userId) {
        List<Channel> publicChannel = channelRepository.findAllByType(ChannelType.PUBLIC);

        List<ReadStatus> readStatus = readStatusRepository.findAllByUserId(userId);
        Set<UUID> privateChannelIds = readStatus.stream()
                .map(ReadStatus::getChannelId)
                .collect(Collectors.toSet());
        List<Channel> privateChannel = channelRepository.findAllById(privateChannelIds);

        List<Channel> allChannel = new ArrayList<>();
        allChannel.addAll(publicChannel);
        allChannel.addAll(privateChannel);

        List<ChannelFindDTO> result = allChannel.stream()
                .map(channel -> {
                    Optional<Message> lastMessageCreatedAt = messageRepository.findLastMessageByChannelId(channel.getId());
                    List<UUID> userIds = null;
                    if(channel.getType() == ChannelType.PRIVATE){
                        userIds = readStatusRepository.findUserIdByChannelId(channel.getId()).stream()
                                .map(ReadStatus::getUserId)
                                .collect(Collectors.toList());

                    }
                    return new ChannelFindDTO(
                            channel.getId(),
                            userIds,
                            channel.getType(),
                            channel.getName(),
                            channel.getDescription(),
                            lastMessageCreatedAt
                    );
                }).toList();
        return result;
    }

    @Override
    public Channel update(UUID channelId, String newName, String newDescription) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new NoSuchElementException("Channel with id " + channelId + " not found"));
        channel.update(newName, newDescription);
        return channelRepository.save(channel);
    }

    @Override
    public Channel updateDTO(UUID id, ChannelUpdateDTO dto) {
        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Channel with id " + id + " not found"));

        if(channel.getType() == ChannelType.PRIVATE){
            throw new IllegalArgumentException("Private 채널 수정 할 수 없습니다.");
        }

        channel.update(dto.newName(), dto.newDescription());

        channelRepository.save(channel);
        Optional<Message> lastMessageCreatedAt = messageRepository.findLastMessageByChannelId(id);
        return channel;
    }

    @Override
    public void delete(UUID channelId) {
        if (!channelRepository.existsById(channelId)) {
            throw new NoSuchElementException("Channel with id " + channelId + " not found");
        }
        messageRepository.deleteAllByChannelId(channelId);
        readStatusRepository.deleteAllByChannelId(channelId);
        channelRepository.deleteById(channelId);
    }
}
