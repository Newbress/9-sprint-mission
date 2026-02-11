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

    @RequestMapping(value = "/api/BinaryContent/find/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<BinaryContent>> findBinaryContent(@PathVariable UUID id) {
        List<BinaryContent> findBinaryContent = binaryContentService.findAll(id);
        return ResponseEntity.ok(findBinaryContent);
    }
}
