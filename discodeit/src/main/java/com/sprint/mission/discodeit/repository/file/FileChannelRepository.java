package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileChannelRepository implements ChannelRepository {
    private final Path DIRECTORY;
    private final String EXTENSION = ".ser";
    private Path resolvePath(UUID id) {
        return DIRECTORY.resolve(id + EXTENSION);
    }
    private final Path channelIndexPath ;

    public FileChannelRepository() {
        this.DIRECTORY = Paths.get(System.getProperty("user.dir"), "file-data-map", Channel.class.getSimpleName());
        if(Files.notExists(DIRECTORY)) {
            try{
                Files.createDirectories(DIRECTORY);
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.channelIndexPath = this.DIRECTORY.resolve("channel_index.map");
    }



    // 채널 아이디 이름으로 찾기
    private UUID findChannelIdByName(String channelName) {

        if(Files.exists(channelIndexPath)) {
            try(FileInputStream fis = new FileInputStream(channelIndexPath.toFile());
                ObjectInputStream ois = new ObjectInputStream(fis)){
                Map<String, UUID> indexMap = (Map<String, UUID>) ois.readObject();
                return indexMap.get(channelName);
            }catch (IOException |ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
        return null;
    }

    // 파일 정보 업데이트하기
    private void updateIndexMap(String channelName, UUID id) {
        Map<String, UUID> indexMap = new HashMap<>();


        if(Files.exists(channelIndexPath)) {
            try(FileInputStream fis = new FileInputStream(channelIndexPath.toFile());
                ObjectInputStream ois= new ObjectInputStream(fis)) {
                Object readObj = ois.readObject();
                if(readObj instanceof Map){
                    indexMap = (Map<String, UUID>) readObj;
                }
            }catch (IOException |ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        indexMap.put(channelName, id);

        try (FileOutputStream fos = new FileOutputStream(channelIndexPath.toFile());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(indexMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public Channel saveCh(Channel channel) {
        Path path = resolvePath(channel.getId());
        try(FileOutputStream fos = new FileOutputStream(path.toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos) ){
            oos.writeObject(channel);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        updateIndexMap(channel.findChannelName(),channel.getId());
        return channel;
    }


    @Override
    public Channel findCh(String channelName) {
        UUID id = findChannelIdByName(channelName);
        if(id== null) {
            throw new NoSuchElementException("찾을 수 없는 채널 " + channelName);
        }

        Path path = resolvePath(id);
        if (Files.exists(path)) {
            try (
                    FileInputStream fis = new FileInputStream(path.toFile());
                    ObjectInputStream ois = new ObjectInputStream(fis)
            ) {
                return (Channel) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        throw new NoSuchElementException("파일이 존재하지 않습니다 ID: " + id);
    }

    @Override
    public List<Channel> findAll() {
        try{
            return Files.list(DIRECTORY)
                    .filter(path -> path.toString().endsWith(EXTENSION))
                    .map(path -> {
                        try(FileInputStream fis = new FileInputStream(path.toFile());
                            ObjectInputStream ois = new ObjectInputStream(fis)) {
                            return (Channel) ois.readObject();
                        }catch (IOException | ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toList();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Channel editCh(UUID id, String newChatroom) {
        Channel userNullable = null;
        Path path = resolvePath(id);
        if(Files.exists(path)) {
            try(FileInputStream fis = new FileInputStream(path.toFile());
                ObjectInputStream ois = new ObjectInputStream(fis)) {
                userNullable = (Channel) ois.readObject();
            }catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        Channel channel = Optional.ofNullable(userNullable)
                .orElseThrow(() -> new NoSuchElementException("Channel with id " + id + " not found"));
        channel.update(newChatroom);

        try(FileOutputStream fos = new FileOutputStream(path.toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(channel);
        }catch(IOException e) {
            throw new RuntimeException(e);
        }
        return channel;
    }

    @Override
    public boolean delCh(UUID id) {
        Path path = resolvePath(id);
        if(Files.notExists(path)) {
            throw new NoSuchElementException("Channel with id" + id + "not found");
        }
        try{
            Files.delete(path);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
