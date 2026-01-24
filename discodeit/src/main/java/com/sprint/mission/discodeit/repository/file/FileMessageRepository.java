package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;


import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileMessageRepository implements MessageRepository {
    private final Path DIRECTORY;
    private final String EXTENSION = ".ser";



    private Path resolvePath(UUID id) {
        return DIRECTORY.resolve(id + EXTENSION);
    }

    public FileMessageRepository() {

        this.DIRECTORY = Paths.get(System.getProperty("user.dir"),"file-data-map", Message.class.getSimpleName());
        if(Files.notExists(DIRECTORY)) {
            try {
                Files.createDirectories(DIRECTORY);
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Message saveMsg(Message msg) {
        Path path = resolvePath(msg.getId());
        try(FileOutputStream fos = new FileOutputStream(path.toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos)){
            oos.writeObject(msg);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        return msg;
    }

    @Override
    public Message findMsgId(String id) {
        UUID uuid = UUID.fromString(id);
        Path path = resolvePath(uuid);

        if(Files.exists(path)){
            try(FileInputStream fis = new FileInputStream(path.toFile());
                ObjectInputStream ois = new ObjectInputStream(fis)) {
                return (Message) ois.readObject();

            }catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }


    @Override
    public Message getUserMsg(String userMsg) {
        return null;
    }

    @Override
    public Message getChannelMsg(String channelMsg) {
        return null;
    }

    @Override
    public List<Message> findAllMsgId() {
        try{
            return Files.list(DIRECTORY)
                    .filter(path -> path.toString().endsWith(EXTENSION))
                    .map(path -> {
                        try (FileInputStream fis = new FileInputStream(path.toFile());
                             ObjectInputStream ois = new ObjectInputStream(fis)) {
                            return (Message) ois.readObject();
                        }catch (IOException | ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toList();
        }catch (IOException e ) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Message> getUserAll(String inputUser) {
        return List.of();
    }

    @Override
    public List<Message> getChannelAll(String inputChannel) {
        return List.of();
    }

    @Override
    public Message editMsg(UUID id, String newContent) {
        Message msgNullable = null;
        Path path = resolvePath(id);
        if(Files.exists(path)) {
            try(FileInputStream fis = new FileInputStream(path.toFile());
                ObjectInputStream ois = new ObjectInputStream(fis)){
                msgNullable = (Message) ois.readObject();
            }catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        Message msg = Optional.ofNullable(msgNullable)
                .orElseThrow(() -> new NoSuchElementException("Message with id" + id + " not found"));
        msg.update(newContent);

        try(FileOutputStream fos = new FileOutputStream(path.toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(msg);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean delMsg(UUID id) {
        Path path = resolvePath(id);
        if(Files.notExists(path)){
            throw new NoSuchElementException("Message with id " + id + " not found");
        }
        try {
            Files.delete(path);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return false;
    }
}
