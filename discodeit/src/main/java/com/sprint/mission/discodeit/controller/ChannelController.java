package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.data.ChannelDto;
import com.sprint.mission.discodeit.dto.request.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelUpdateRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Channel",
    description = "Channel api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/channels")
public class ChannelController {

  private final ChannelService channelService;

  @Operation(summary = "Public Channel 생성", operationId = "create_3", responses = {
      @ApiResponse(
          responseCode = "201",
          description = "Public Channel이 성공적으로 생성됨"
      )
  })
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping(path = "public")
  public ResponseEntity<ChannelDto> create(@RequestBody PublicChannelCreateRequest request) {
    ChannelDto createdChannel = channelService.create(request);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(createdChannel);
  }

  @Operation(summary = "Private Channel 생성", operationId = "create_4", responses = {
      @ApiResponse(
          responseCode = "201",
          description = "Private Channel이 성공적으로 생성됨"
      )
  })
  @PostMapping(path = "private")
  public ResponseEntity<ChannelDto> create(@RequestBody PrivateChannelCreateRequest request) {
    ChannelDto createdChannel = channelService.create(request);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(createdChannel);
  }

  @Operation(summary = "Channel 정보 수정", operationId = "update_3", responses = {
      @ApiResponse(
          responseCode = "200",
          description = "Channel 정보가 성공적으로 수정됨"
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Private Channel은 수정할 수 없음",
          content = @Content(examples = @ExampleObject(value = "Private channel cannot be updated"))
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Channel을 찾을 수 없음",
          content = @Content(examples = @ExampleObject(value = "Channel with id {channelId} not found"))
      )
  })
  @PatchMapping(path = "{channelId}")
  public ResponseEntity<ChannelDto> update(
      @Parameter(description = "수정할 Channel ID")
      @PathVariable("channelId") UUID channelId,
      @RequestBody PublicChannelUpdateRequest request) {
    ChannelDto udpatedChannel = channelService.update(channelId, request);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(udpatedChannel);
  }

  @Operation(summary = "Channel 삭제", operationId = "delete_2", responses = {
      @ApiResponse(
          responseCode = "204",
          description = "Channel이 성공적으로 삭제됨"
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Channel을 찾을 수 없음",
          content = @Content(examples = @ExampleObject(value = "Channel with id {channelId} not found"))
      )
  })
  @DeleteMapping(path = "{channelId}")
  public ResponseEntity<Void> delete(
      @Parameter(description = "삭제할 Channel ID")
      @PathVariable("channelId") UUID channelId) {
    channelService.delete(channelId);
    return ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .build();
  }

  @Operation(summary = "User가 참여 중인 Channel 목록 조회", operationId = "findAll_1", responses = {
      @ApiResponse(
          responseCode = "200",
          description = "Channel 목록 조회 성공"
      )
  })
  @GetMapping
  public ResponseEntity<List<ChannelDto>> findAll(
      @Parameter(description = "조회할 User ID")
      @RequestParam("userId") UUID userId) {
    List<ChannelDto> channels = channelService.findAllByUserId(userId);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(channels);
  }
}
