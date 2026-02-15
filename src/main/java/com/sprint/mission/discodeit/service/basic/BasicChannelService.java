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
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {
    private final ChannelRepository channelRepository;
    private final MessageRepository messageRepository;
    private final ReadStatusRepository readStatusRepository;

    //public
    @Override
    public Channel create(CreateChannelRequest request) {
        Channel publicChannel = new Channel(ChannelType.PUBLIC,request.name(),request.description());
        return channelRepository.save(publicChannel);

    }

    //private
    @Override
    public Channel createPrivate(CreatePrivateChannelRequest request) {
        Channel privateChannel = new Channel(ChannelType.PRIVATE,null,null);
        List<UUID> userIds = request.userIds();
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
    public ResponseChannel find(UUID id) {
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
        return new ResponseChannel(
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
    public List<ResponseChannel> findAllDTO(UUID userId) {
        List<Channel> publicChannel = channelRepository.findAllByType(ChannelType.PUBLIC);

        List<ReadStatus> readStatus = readStatusRepository.findAllByUserId(userId);
        Set<UUID> privateChannelIds = readStatus.stream()
                .map(ReadStatus::getChannelId)
                .collect(Collectors.toSet());
        List<Channel> privateChannel = channelRepository.findAllById(privateChannelIds);

        List<Channel> allChannel = new ArrayList<>();
        allChannel.addAll(publicChannel);
        allChannel.addAll(privateChannel);

        List<ResponseChannel> result = allChannel.stream()
                .map(channel -> {
                    Optional<Message> lastMessageCreatedAt = messageRepository.findLastMessageByChannelId(channel.getId());
                    List<UUID> userIds = null;
                    if(channel.getType() == ChannelType.PRIVATE){
                        userIds = readStatusRepository.findUserIdByChannelId(channel.getId()).stream()
                                .map(ReadStatus::getUserId)
                                .collect(Collectors.toList());

                    }
                    return new ResponseChannel(
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
    public Channel update(UUID id, UpdateChannelRequest request) {
        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Channel with id " + id + " not found"));

        if(channel.getType() == ChannelType.PRIVATE){
            throw new IllegalArgumentException("Private 채널 수정 할 수 없습니다.");
        }

        channel.update(request.newName(), request.newDescription());

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
