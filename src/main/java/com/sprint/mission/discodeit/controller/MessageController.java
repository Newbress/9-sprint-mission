package com.sprint.mission.discodeit.controller;


import com.sprint.mission.discodeit.entity.DTO.Message.MessageCreateDTO;
import com.sprint.mission.discodeit.entity.DTO.Message.MessageUpdateDTO;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @RequestMapping(value = "/api/message", method = RequestMethod.POST)
    public ResponseEntity<Message> createMessage(@RequestBody MessageCreateDTO dto) {
        Message message = messageService.createDTO(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @RequestMapping(value = "/api/message/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Message> updateMessage(@PathVariable UUID id, @RequestBody MessageUpdateDTO dto){
        Message message = messageService.updateDTO(id, dto);
        return ResponseEntity.ok(message);
    }

    @RequestMapping(value = "/api/message/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteMessage(@PathVariable UUID id) {
        messageService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/api/message/findAllByChannelId/{channelId}", method = RequestMethod.GET)
    public ResponseEntity<List<Message>> findMessageByChannelId(@PathVariable UUID channelId) {
        List<Message> message = messageService.findAllByChannelId(channelId);
        return ResponseEntity.ok(message);
    }
}
