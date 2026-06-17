package com.sprint.mission.discodeit.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.UUID;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class S3UploadFailedEvent {
  private final UUID binaryContentId;
  private final String errorMessage;
  private final String requestId;

  public S3UploadFailedEvent(UUID binaryContentId, String errorMessage, String requestId) {
    this.binaryContentId = binaryContentId;
    this.errorMessage = errorMessage;
    this.requestId = requestId;
  }
}
