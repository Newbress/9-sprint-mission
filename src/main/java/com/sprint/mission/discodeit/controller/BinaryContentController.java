package com.sprint.mission.discodeit.controller;


import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.service.BinaryContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class BinaryContentController {
    private final BinaryContentService binaryContentService;

    @RequestMapping(value = "/api/binary", method = RequestMethod.GET)
    public ResponseEntity<List<BinaryContent>> findBinaryContent(@PathVariable UUID binaryContentId) {
        List<BinaryContent> findBinaryContent = binaryContentService.findAll(binaryContentId);
        return ResponseEntity.ok(findBinaryContent);
    }
}
