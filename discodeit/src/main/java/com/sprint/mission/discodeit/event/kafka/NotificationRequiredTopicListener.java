package com.sprint.mission.discodeit.event.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.mission.discodeit.entity.Notification;
import com.sprint.mission.discodeit.entity.Role;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.event.MessageCreatedEvent;
import com.sprint.mission.discodeit.event.RoleUpdatedEvent;
import com.sprint.mission.discodeit.event.S3UploadFailedEvent;
import com.sprint.mission.discodeit.repository.NotificationRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class NotificationRequiredTopicListener {

  private final ObjectMapper objectMapper;
  private final NotificationRepository notificationRepository;
  private final ReadStatusRepository readStatusRepository;
  private final UserRepository userRepository;

  @Transactional
  @KafkaListener(topics = "discodeit.MessageCreatedEvent")
  public void onMessageCreatedEvent(String kafkaEvent) {
    try {
      MessageCreatedEvent event = objectMapper.readValue(kafkaEvent, MessageCreatedEvent.class);
      var message = event.getMessage();
      var channel = message.getChannel();
      var author = message.getAuthor();

      String title = author.getUsername() + " (#" + channel.getName() + ")";
      String content = message.getContent();

      readStatusRepository.findAllByChannelId(channel.getId()).stream()
          .filter(rs -> rs.isNotificationEnabled())
          .filter(rs -> !rs.getUser().getId().equals(author.getId()))
          .forEach(rs -> notificationRepository.save(
              new Notification(rs.getUser(), title, content)
          ));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Transactional
  @KafkaListener(topics = "discodeit.RoleUpdatedEvent")
  public void onRoleUpdatedEvent(String kafkaEvent) {
    try {
      RoleUpdatedEvent event = objectMapper.readValue(kafkaEvent, RoleUpdatedEvent.class);
      User user = userRepository.findById(event.getUserId()).orElseThrow();

      String title = "권한이 변경되었습니다.";
      String content = event.getOldRole().name() + " -> " + event.getNewRole().name();
      notificationRepository.save(new Notification(user, title, content));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Transactional
  @KafkaListener(topics = "discodeit.S3UploadFailedEvent")
  public void onS3UploadFailedEvent(String kafkaEvent) {
    try {
      S3UploadFailedEvent event = objectMapper.readValue(kafkaEvent, S3UploadFailedEvent.class);

      userRepository.findAll().stream()
          .filter(user -> user.getRole() == Role.ADMIN)
          .forEach(admin -> {
            String title = "파일 업로드 실패";
            String content = String.format(
                "RequestId: %s\nBinaryContentId: %s\nError: %s",
                event.getRequestId(), event.getBinaryContentId(), event.getErrorMessage()
            );
            notificationRepository.save(new Notification(admin, title, content));
          });
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}