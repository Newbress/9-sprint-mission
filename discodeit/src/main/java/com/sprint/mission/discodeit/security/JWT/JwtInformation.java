package com.sprint.mission.discodeit.security.JWT;

import java.util.UUID;
import lombok.Getter;

@Getter
public class JwtInformation {
  private final UUID userId;
  private String accessToken;
  private String refreshToken;

  public JwtInformation(UUID userId, String accessToken, String refreshToken) {
    this.userId = userId;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

  public void rotate(String accessToken, String refreshToken) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }
}