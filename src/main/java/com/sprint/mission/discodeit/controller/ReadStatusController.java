package com.sprint.mission.discodeit.controller;


import com.sprint.mission.discodeit.entity.DTO.ReadStatus.ReadStatusCreateDTO;
import com.sprint.mission.discodeit.entity.DTO.ReadStatus.ReadStatusResponseDTO;
import com.sprint.mission.discodeit.entity.DTO.ReadStatus.ReadStatusUpdateDTO;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ReadStatusController {
    private final ReadStatusService readStatusService;

    @RequestMapping(value = "/api/channel/{channelId}/readStatus", method = RequestMethod.POST)
    public ResponseEntity<ReadStatus> createReadStatus(@RequestBody ReadStatusCreateDTO dto) {
        ReadStatus readStatus = readStatusService.createDTO(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(readStatus);
    }

    @RequestMapping(value = "/api/channel/{channelId}/readStats", method = RequestMethod.PUT)
    public ResponseEntity<ReadStatusResponseDTO> updateReadStatus(@PathVariable UUID channelId,@RequestBody ReadStatusUpdateDTO dto) {
        ReadStatusResponseDTO readStatus = readStatusService.updateDTO(channelId, dto);
        return ResponseEntity.ok(readStatus);
    }

    @RequestMapping(value = "/api/users/{userId}/readStatus", method = RequestMethod.GET)
    public ResponseEntity<List<ReadStatus>> findReadStatus(@PathVariable UUID userId) {
        List<ReadStatus> readStatus = readStatusService.findAllByUserId(userId);
        return ResponseEntity.ok(readStatus);
    }

}
