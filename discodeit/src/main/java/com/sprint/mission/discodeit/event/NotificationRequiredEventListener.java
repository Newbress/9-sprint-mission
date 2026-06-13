package com.sprint.mission.discodeit.event;

import com.sprint.mission.discodeit.entity.Notification;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.NotificationRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class NotificationRequiredEventListener {

  private final NotificationRepository notificationRepository;
  private final ReadStatusRepository readStatusRepository;
  private final UserRepository userRepository;
  private final CacheManager cacheManager;

  @Async
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void on(MessageCreatedEvent event) {
    var message = event.getMessage();
    var channel = message.getChannel();
    var author = message.getAuthor();

    String title = author.getUsername() + " (#" + channel.getName() + ")";
    String content = message.getContent();

    readStatusRepository.findAllByChannelId(channel.getId()).stream()
        .filter(rs -> rs.isNotificationEnabled())
        .filter(rs -> !rs.getUser().getId().equals(author.getId()))
        .forEach(rs -> {
          Notification notification = new Notification(rs.getUser(), title, content);
          notificationRepository.save(notification);
        });

    readStatusRepository.findAllByChannelId(channel.getId()).stream()
        .filter(rs -> rs.isNotificationEnabled())
        .filter(rs -> !rs.getUser().getId().equals(author.getId()))
        .forEach(rs -> {
          Cache cache = cacheManager.getCache("userNotifications");
          if (cache != null) {
            cache.evict(rs.getUser().getId());
          }
        });
  }

  @Async
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void on(RoleUpdatedEvent event) {
    User user = userRepository.findById(event.getUserId())
        .orElseThrow();

    String title = "권한이 변경되었습니다.";
    String content = event.getOldRole().name() + " -> " + event.getNewRole().name();

    Notification notification = new Notification(user, title, content);
    notificationRepository.save(notification);
  }
}