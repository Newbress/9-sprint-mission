package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.DTO.BinaryContent.AttachedFiles;
import com.sprint.mission.discodeit.entity.DTO.Message.CreateMessageRequest;
import com.sprint.mission.discodeit.entity.DTO.Message.UpdateMessageRequest;
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
@RequiredArgsConstructor
public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final BinaryContentRepository binaryContentRepository;


    @Override
    public Message create(CreateMessageRequest request) {
        if(!channelRepository.existsById(request.channelId())){
            throw new NoSuchElementException("Channel not found with id " + request.channelId());
        }
        if(!userRepository.existsById(request.userId())){
            throw new NoSuchElementException("User not found with id " + request.userId());
        }

        Message message = new Message(request.content(), request.channelId(), request.userId());
        messageRepository.save(message);

        if(request.attachedFiles() != null && !request.attachedFiles().isEmpty()) {
            for(AttachedFiles attachedFilesDTO : request.attachedFiles()){
                BinaryContent files = new BinaryContent(
                        null,
                        attachedFilesDTO.contentType(),
                        attachedFilesDTO.bytes()
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
    public Message update(UUID messageId, UpdateMessageRequest request) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("Message with id " + messageId + " not found"));
        message.update(request.content());
        messageRepository.save(message);

        if(request.attachedFiles() != null && !request.attachedFiles().isEmpty()) {
            for(AttachedFiles attachedFilesDTO : request.attachedFiles()){
                BinaryContent content = new BinaryContent(
                        null,
                        attachedFilesDTO.contentType(),
                        attachedFilesDTO.bytes()
                );
                binaryContentRepository.save(content);
            }
        }
        return message;
    }

    @Override
    public void delete(UUID id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException("Message with id " + id + " not found"));
        message.getAttachmentIds()
                .forEach(binaryContentRepository::deleteById);
        messageRepository.deleteById(id);
    }
}
