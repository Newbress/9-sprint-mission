package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Message;

import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.MessageService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public class FileMessageService implements MessageService {
    private final Path DIRECTORY;
    private final String EXTENSION = ".ser";
    private final MessageRepository messageRepository;

    public FileMessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
        this.DIRECTORY = Paths.get(System.getProperty("message.dir"), "file-data-map", Message.class.getSimpleName());
        if(Files.notExists(DIRECTORY)) {
            try{
                Files.createDirectories(DIRECTORY);
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Path resolvePath(UUID id){
        return DIRECTORY.resolve(id + EXTENSION);
    }

    @Override
    public Message sendMsg(String channelName, String userName, String content) {
        Message msg = new Message(channelName, userName, content);
        return messageRepository.saveMsg(msg);
    }

    @Override
    public Message findMsgId(String msgId) {
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
    public List<Message> findAllMstId() {
        return List.of();
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
        return messageRepository.delMsg(id);
    }
}
