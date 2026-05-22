package com.sprint.mission.discodeit.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jwt.*;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class JwtTokenProvider {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.access-token-expiry:3600000}") // 1시간
  private long accessTokenExpiry;

  @Value("${jwt.refresh-token-expiry:604800000}") // 7일
  private long refreshTokenExpiry;

  private SecretKey getSecretKey() {
    return new SecretKeySpec(
        secret.getBytes(StandardCharsets.UTF_8),
        "HmacSHA256"
    );
  }

  public String generateAccessToken(String username, UUID userId) {
    return generateToken(username, userId, accessTokenExpiry);
  }

  public String generateRefreshToken(String username, UUID userId) {
    return generateToken(username, userId, refreshTokenExpiry);
  }

  private String generateToken(String username, UUID userId, long expiry) {
    try {
      JWTClaimsSet claims = new JWTClaimsSet.Builder()
          .subject(username)
          .claim("userId", userId.toString())
          .issueTime(new Date())
          .expirationTime(Date.from(Instant.now().plusMillis(expiry)))
          .build();

      SignedJWT signedJWT = new SignedJWT(
          new JWSHeader(JWSAlgorithm.HS256),
          claims
      );
      signedJWT.sign(new MACSigner(getSecretKey()));
      return signedJWT.serialize();
    } catch (Exception e) {
      throw new RuntimeException("JWT 생성 실패", e);
    }
  }

  public boolean validateToken(String token) {
    try {
      SignedJWT signedJWT = SignedJWT.parse(token);
      boolean verified = signedJWT.verify(new MACVerifier(getSecretKey()));
      boolean notExpired = signedJWT.getJWTClaimsSet()
          .getExpirationTime().after(new Date());
      return verified && notExpired;
    } catch (Exception e) {
      log.debug("JWT 검증 실패: {}", e.getMessage());
      return false;
    }
  }

  public String getUsername(String token) {
    try {
      return SignedJWT.parse(token).getJWTClaimsSet().getSubject();
    } catch (Exception e) {
      throw new RuntimeException("JWT 파싱 실패", e);
    }
  }
}