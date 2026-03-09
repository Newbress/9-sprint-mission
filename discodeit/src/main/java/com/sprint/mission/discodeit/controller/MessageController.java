package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.data.MessageDto;
import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.dto.response.PageResponse;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Tag(name = "Message",
    description = "Message api")
@RequestMapping("/api/messages")
@RestController
@RequiredArgsConstructor
public class MessageController {

  private final MessageService messageService;

  @Operation(summary = "Message 생성", operationId = "create_2", responses = {
      @ApiResponse(
          responseCode = "201",
          description = "Message가 성공적으로 생성됨"
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Channel 또는 User를 찾을 수 없음"
      )
  })
  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<MessageDto> create(
      @Parameter(description = "Message 생성 정보")
      @RequestPart("messageCreateRequest") MessageCreateRequest messageCreateRequest,
      @Parameter(description = "Message 첨부 파일들")
      @RequestPart(value = "attachments", required = false) List<MultipartFile> attachments
  ) {
    List<BinaryContentCreateRequest> attachmentRequests = Optional.ofNullable(attachments)
        .map(files -> files.stream()
            .map(file -> {
              try {
                return new BinaryContentCreateRequest(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
                );
              } catch (IOException e) {
                throw new RuntimeException(e);
              }
            })
            .toList())
        .orElse(new ArrayList<>());
    MessageDto createdMessage = messageService.create(messageCreateRequest, attachmentRequests);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(createdMessage);
  }

  @Operation(summary = "Message 내용 수정", operationId = "update_2", responses = {
      @ApiResponse(
          responseCode = "200",
          description = "Message가 성공적으로 수정됨"
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Message를 찾을 수 없음"
      )
  })
  @PatchMapping(path = "{messageId}")
  public ResponseEntity<MessageDto> update(
      @Parameter(description = "수정할 Message ID")
      @PathVariable("messageId") UUID messageId,
      @RequestBody MessageUpdateRequest request) {
    MessageDto updatedMessage = messageService.update(messageId, request);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(updatedMessage);
  }

  @Operation(summary = "Message 삭제", operationId = "delete", responses = {
      @ApiResponse(
          responseCode = "204",
          description = "Message가 성공적으로 삭제됨"
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Message를 찾을 수 없음"
      )
  })
  @DeleteMapping(path = "{messageId}")
  public ResponseEntity<Void> delete(
      @Parameter(description = "삭제할 Message ID")
      @PathVariable("messageId") UUID messageId) {
    messageService.delete(messageId);
    return ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .build();
  }

  @Operation(summary = "Channel의 Message 목록 조회", operationId = "findAllByChannelId", responses = {
      @ApiResponse(
          responseCode = "200",
          description = "Message 목록 조회 성공" // 복구
      )
  })
  @GetMapping
  public ResponseEntity<PageResponse<MessageDto>> findAllByChannelId(
      @Parameter(description = "조회할 Channel ID")
      @RequestParam("channelId") UUID channelId,
      @PageableDefault(
          size = 50,
          page = 0,
          sort = "createdAt",
          direction = Direction.DESC
      )
      Pageable pageable)
      {
    PageResponse<MessageDto> messages = messageService.findAllByChannelId(channelId, pageable);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(messages);
  }
}
