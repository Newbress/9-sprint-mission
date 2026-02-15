package com.sprint.mission.discodeit.controller;


import com.sprint.mission.discodeit.entity.DTO.ReadStatus.CreateReadStatusRequest;
import com.sprint.mission.discodeit.entity.DTO.ReadStatus.UpdateReadStatusRequest;
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
    public ResponseEntity<ReadStatus> createReadStatus(@RequestBody CreateReadStatusRequest request) {
        ReadStatus readStatus = readStatusService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(readStatus);
    }

    @RequestMapping(value = "/api/readStatus/{readStatusId}", method = RequestMethod.PUT)
    public ResponseEntity<ReadStatus> updateReadStatus(@PathVariable UUID readStatusId,@RequestBody UpdateReadStatusRequest request) {
        ReadStatus readStatus = readStatusService.update(readStatusId, request);
        return ResponseEntity.ok(readStatus);
    }

        @RequestMapping(value = "/api/readStatus/{userId}", method = RequestMethod.GET)
        public ResponseEntity<List<ReadStatus>> findReadStatus(@PathVariable UUID userId) {
            List<ReadStatus> readStatus = readStatusService.findAllByUserId(userId);
            return ResponseEntity.ok(readStatus);
        }

}
