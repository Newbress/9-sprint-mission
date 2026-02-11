package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.DTO.Channel.ChannelCreateDTO;
import com.sprint.mission.discodeit.entity.DTO.Channel.ChannelCreatePrivateDTO;
import com.sprint.mission.discodeit.entity.DTO.Channel.ChannelFindDTO;
import com.sprint.mission.discodeit.entity.DTO.Channel.ChannelUpdateDTO;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ChannelController {
    private final ChannelService channelService;
    private final UserService userService;

    @RequestMapping(value = "/api/channel", method = RequestMethod.POST)
    public ResponseEntity<Channel> createPublicChannel(@RequestBody ChannelCreateDTO dto) {
        Channel channel = channelService.createDTO(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(channel);
    }

    @RequestMapping(value = "/api/channel/private" , method = RequestMethod.POST)
    public ResponseEntity<Channel> createPrivateChannel(@RequestBody ChannelCreatePrivateDTO dto) {
        Channel channel = channelService.createPrivateDTO(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(channel);
    }

    @RequestMapping(value = "/api/channel/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Channel> updatePublicChannel(@PathVariable UUID id, @RequestBody ChannelUpdateDTO dto){
        Channel channel = channelService.updateDTO(id, dto);
        return ResponseEntity.ok(channel);
    }

    @RequestMapping(value = "/api/channel/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteChannel(@PathVariable UUID id){
        channelService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/api/channel/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<Channel>> findChannelByUserId(@PathVariable UUID userId) {
        List<Channel> channel = channelService.findChannelByUserID(userId);
        return ResponseEntity.ok(channel);
    }



}
