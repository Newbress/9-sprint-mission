package com.sprint.mission.discodeit.security.JWT;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InMemoryJwtRegistry implements JwtRegistry {

  // <userId, Queue<JwtInformation>>
  private final Map<UUID, Queue<JwtInformation>> origin = new ConcurrentHashMap<>();
  private final int maxActiveJwtCount = 1;
  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public void registerJwtInformation(JwtInformation jwtInformation) {
    UUID userId = jwtInformation.getUserId();
    origin.putIfAbsent(userId, new ArrayDeque<>());
    Queue<JwtInformation> queue = origin.get(userId);

    // 최대 동시 로그인 수 초과 시 가장 오래된 세션 제거
    while (queue.size() >= maxActiveJwtCount) {
      queue.poll();
    }
    queue.add(jwtInformation);
  }

  @Override
  public void invalidateJwtInformationByUserId(UUID userId) {
    origin.remove(userId);
  }

  @Override
  public boolean hasActiveJwtInformationByUserId(UUID userId) {
    Queue<JwtInformation> queue = origin.get(userId);
    return queue != null && !queue.isEmpty();
  }

  @Override
  public boolean hasActiveJwtInformationByAccessToken(String accessToken) {
    return origin.values().stream()
        .flatMap(Queue::stream)
        .anyMatch(info -> info.getAccessToken().equals(accessToken));
  }

  @Override
  public boolean hasActiveJwtInformationByRefreshToken(String refreshToken) {
    return origin.values().stream()
        .flatMap(Queue::stream)
        .anyMatch(info -> info.getRefreshToken().equals(refreshToken));
  }

  @Override
  public void rotateJwtInformation(String refreshToken, JwtInformation newJwtInformation) {
    origin.values().stream()
        .flatMap(Queue::stream)
        .filter(info -> info.getRefreshToken().equals(refreshToken))
        .findFirst()
        .ifPresent(info -> info.rotate(
            newJwtInformation.getAccessToken(),
            newJwtInformation.getRefreshToken()
        ));
  }

  @Scheduled(fixedDelay = 1000 * 60 * 5)
  @Override
  public void clearExpiredJwtInformation() {
    origin.forEach((userId, queue) -> {
      queue.removeIf(info -> !jwtTokenProvider.validateToken(info.getAccessToken())
          && !jwtTokenProvider.validateToken(info.getRefreshToken()));
      if (queue.isEmpty()) {
        origin.remove(userId);
      }
    });
    log.debug("만료된 JWT 정보 정리 완료");
  }
}