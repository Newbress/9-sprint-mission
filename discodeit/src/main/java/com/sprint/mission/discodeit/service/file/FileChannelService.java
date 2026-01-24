package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ChannelService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.*;

public class FileChannelService implements ChannelService {
    private final Path DIRECTORY;
    private final String EXTENSION = ".ser";
    private final ChannelRepository channelRepository;

    private final Path channelIndexPath = Paths.get("data", "channel index");

    //파일명 id로 하기
    private Path resolvePath(UUID id) {
        return DIRECTORY.resolve(id + EXTENSION);
    }
    

    public FileChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
        this.DIRECTORY = Paths.get(System.getProperty("user.dir"), "file-data-map", Channel.class.getSimpleName());
        if(Files.notExists(DIRECTORY)) {
            try{
                Files.createDirectories(DIRECTORY);
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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
