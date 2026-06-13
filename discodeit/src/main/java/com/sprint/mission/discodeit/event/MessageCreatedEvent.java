package com.sprint.mission.discodeit.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sprint.mission.discodeit.entity.Message;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageCreatedEvent {
  private final Message message;

  public MessageCreatedEvent(Message message) {
    this.message = message;
  }
}