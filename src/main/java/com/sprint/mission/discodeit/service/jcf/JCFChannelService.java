package com.sprint.mission.discodeit.service.jcf;

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

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class JCFChannelService implements ChannelService {
    private final Map<UUID, Channel> data = new HashMap<>();
    private final ReadStatusRepository readStatusRepository;
    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;

    @Override
    public Channel create(ChannelType type, String name, String description) {
        Channel channel = new Channel(type, name, description);
        this.data.put(channel.getId(), channel);

        return channel;
    }

    @Override
    public Channel createDTO(ChannelCreateDTO dto) {
        Channel publicChannel = new Channel(ChannelType.PUBLIC, dto.name(), dto.description());
        return this.data.put(publicChannel.getId(), publicChannel);
    }

    @Override
    public Channel createPrivateDTO(ChannelCreatePrivateDTO dto) {
        Channel privateChannel = new Channel(ChannelType.PRIVATE, null,null);
        List<UUID> userIds = dto.userIds();
        for(UUID userId : userIds){
            ReadStatus readStatus = new ReadStatus(
                    userId,
                    privateChannel.getId(),
                    Instant.now()
            );
            readStatusRepository.save(readStatus);
        }
        return this.data.put(privateChannel.getId(), privateChannel);
    }

    @Override
    public Channel find(UUID channelId) {
        Channel channelNullable = this.data.get(channelId);
        return Optional.ofNullable(channelNullable)
                .orElseThrow(() -> new NoSuchElementException("Channel with id " + channelId + " not found"));
    }

    @Override
    public ChannelFindDTO findDTO(UUID channelId) {
        Channel channelNullable = this.data.get(channelId);
        Optional.ofNullable(channelNullable)
                .orElseThrow(()-> new NoSuchElementException("Channel with id " + channelId + " not found"));
        Optional<Message> lastMessageCreatedAt = messageRepository.findLastMessageByChannelId(channelId);

        //private
        List<UUID> userIds = null;
        if(channelNullable.getType() == ChannelType.PRIVATE){
            userIds = readStatusRepository.findUserIdByChannelId(channelId).stream()
                    .map(ReadStatus::getUserId)
                    .toList();
        }
        return new ChannelFindDTO(
                channelNullable.getId(),
                userIds,
                channelNullable.getType(),
                channelNullable.getName(),
                channelNullable.getDescription(),
                lastMessageCreatedAt
        );
    }

    @Override
    public List<Channel> findChannelByUserID(UUID userId) {
        return List.of();
    }


    @Override
    public List<Channel> findAll() {
        return this.data.values().stream().toList();
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
        Channel channelNullable = this.data.get(channelId);
        Channel channel = Optional.ofNullable(channelNullable)
                .orElseThrow(() -> new NoSuchElementException("Channel with id " + channelId + " not found"));
        channel.update(newName, newDescription);

        return channel;
    }

    @Override
    public Channel updateDTO(UUID id, ChannelUpdateDTO dto) {
        return null;
    }

    @Override
    public void delete(UUID channelId) {
        if (!this.data.containsKey(channelId)) {
            throw new NoSuchElementException("Channel with id " + channelId + " not found");
        }
        this.data.remove(channelId);
    }
}
