package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.DTO.BinaryContent.AttachedFilesDTO;
import com.sprint.mission.discodeit.entity.DTO.Message.MessageCreateDTO;
import com.sprint.mission.discodeit.entity.DTO.Message.MessageUpdateDTO;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final BinaryContentRepository binaryContentRepository;

    @Override
    public Message create(String content, UUID channelId, UUID authorId) {
        if (!channelRepository.existsById(channelId)) {
            throw new NoSuchElementException("Channel not found with id " + channelId);
        }
        if (!userRepository.existsById(authorId)) {
            throw new NoSuchElementException("Author not found with id " + authorId);
        }

        Message message = new Message(content, channelId, authorId);
        return messageRepository.save(message);
    }

    @Override
    public Message createDTO(MessageCreateDTO dto) {
        if(!channelRepository.existsById(dto.channelId())){
            throw new NoSuchElementException("Channel not found with id " + dto.channelId());
        }
        if(!userRepository.existsById(dto.userId())){
            throw new NoSuchElementException("User not found with id " + dto.userId());
        }

        Message message = new Message(dto.content(), dto.channelId(), dto.userId());
        messageRepository.save(message);

        if(dto.attachedFiles() != null && !dto.attachedFiles().isEmpty()) {
            for(AttachedFilesDTO attachedFilesDTO : dto.attachedFiles()){
                BinaryContent files = new BinaryContent(
                        UUID.randomUUID(),
                        null,
                        message.getId(),
                        attachedFilesDTO.content(),
                        attachedFilesDTO.contentType()
                );
                binaryContentRepository.save(files);
            }
        }
        return message;
    }

    @Override
    public Message find(UUID messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("Message with id " + messageId + " not found"));
    }

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public List<Message> findAllByChannelId(UUID channelId) {
        if (!channelRepository.existsById(channelId)) {
            throw new NoSuchElementException("Channel not found with id " + channelId);
        }
        return messageRepository.findAllByChannelId(channelId);
    }

    @Override
    public Message update(UUID messageId, String newContent) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("Message with id " + messageId + " not found"));
        message.update(newContent);
        return messageRepository.save(message);
    }

    @Override
    public Message updateDTO(UUID messageId, MessageUpdateDTO dto) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("Message with id " + messageId + " not found"));
        message.update(dto.content());
        messageRepository.save(message);

        if(dto.attachedFiles() != null && !dto.attachedFiles().isEmpty()) {
            for(AttachedFilesDTO attachedFilesDTO : dto.attachedFiles()){
                BinaryContent content = new BinaryContent(
                        UUID.randomUUID(),
                        null,
                        message.getId(),
                        attachedFilesDTO.content(),
                        attachedFilesDTO.contentType()
                );
                binaryContentRepository.save(content);
            }
        }
        return message;
    }

    @Override
    public void delete(UUID id) {
        if (!messageRepository.existsById(id)) {
            throw new NoSuchElementException("Message with id " + id + " not found");
        }
        binaryContentRepository.deleteAllByMessageId(id);
        messageRepository.deleteById(id);
    }
}
