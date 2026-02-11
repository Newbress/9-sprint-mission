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

    @RequestMapping(value = "/api/readStatus", method = RequestMethod.POST)
    public ResponseEntity<ReadStatus> createReadStatus(@RequestBody ReadStatusCreateDTO dto) {
        ReadStatus readStatus = readStatusService.createDTO(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(readStatus);
    }

    @RequestMapping(value = "/api/readStatus/{readStatusId}", method = RequestMethod.PUT)
    public ResponseEntity<ReadStatus> updateReadStatus(@PathVariable UUID readStatusId,@RequestBody ReadStatusUpdateDTO dto) {
        ReadStatus readStatus = readStatusService.updateDTO(readStatusId, dto);
        return ResponseEntity.ok(readStatus);
    }

        @RequestMapping(value = "/api/readStatus/{userId}", method = RequestMethod.GET)
        public ResponseEntity<List<ReadStatus>> findReadStatus(@PathVariable UUID userId) {
            List<ReadStatus> readStatus = readStatusService.findAllByUserId(userId);
            return ResponseEntity.ok(readStatus);
        }

}
