package com.sprint.mission.discodeit.controller;


import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.service.BinaryContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class BinaryContentController {
    private final BinaryContentService binaryContentService;


    @RequestMapping(value = "/api/binaryContent/find", method = RequestMethod.GET)
    public ResponseEntity<List<BinaryContent>> findBinaryContent(@RequestParam UUID id) {
        List<BinaryContent> findBinaryContent = binaryContentService.findAll(id);
        return ResponseEntity.ok(findBinaryContent);
    }
}
