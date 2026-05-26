package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.data.JwtDto;
import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.dto.request.UserRoleUpdateRequest;
import com.sprint.mission.discodeit.exception.ErrorResponse;
import com.sprint.mission.discodeit.security.DiscodeitUserDetails;
import com.sprint.mission.discodeit.security.JwtTokenProvider;
import com.sprint.mission.discodeit.service.AuthService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.basic.BasicUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@Tag(name = "Auth",
    description = "인증 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;
  private final BasicUserService basicUserService;
  private final JwtTokenProvider jwtTokenProvider;
  private final UserDetailsService userDetailsService;

  @GetMapping("/csrf-token")
  public ResponseEntity<Void> getCsrfToken(CsrfToken csrfToken) {
    String tokenValue = csrfToken.getToken();
    log.debug("CSRF 토큰 요청: {}", tokenValue);
    return ResponseEntity.status(203).build();
  }

  @GetMapping("/me")
  public ResponseEntity<UserDto> me(
      @AuthenticationPrincipal DiscodeitUserDetails userDetails) {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(userDetails.getUserDto());
  }

  @PutMapping("/role")
  public ResponseEntity<UserDto> updateRole(
      @RequestBody UserRoleUpdateRequest request) {
    UserDto updatedUser = basicUserService.updateRole(request.userId(), request.newRole());
    return ResponseEntity.ok(updatedUser);
  }

  @PostMapping("/refresh")
  public ResponseEntity<?> refresh(
      @CookieValue(value = "REFRESH_TOKEN", required = false) String refreshToken, HttpServletResponse response) {

    if (refreshToken == null || !jwtTokenProvider.validateToken(refreshToken)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new ErrorResponse(
              Instant.now(),
              "INVALID_REFRESH_TOKEN",
              "리프레시 토큰이 유효하지 않습니다.",
              null,
              "InvalidRefreshTokenException",
              401
          ));
    }

    String username = jwtTokenProvider.getUsername(refreshToken);
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    DiscodeitUserDetails discodeitUserDetails = (DiscodeitUserDetails) userDetails;

    // 토큰 Rotation - 새 액세스 토큰 + 새 리프레시 토큰 발급
    String newAccessToken = jwtTokenProvider.generateAccessToken(
        username, discodeitUserDetails.getUserDto().id());
    String newRefreshToken = jwtTokenProvider.generateRefreshToken(
        username, discodeitUserDetails.getUserDto().id());

    // 새 리프레시 토큰 쿠키에 저장
    Cookie refreshCookie = new Cookie("REFRESH_TOKEN", newRefreshToken);
    refreshCookie.setHttpOnly(true);
    refreshCookie.setPath("/");
    refreshCookie.setMaxAge(7 * 24 * 60 * 60);
    response.addCookie(refreshCookie);

    return ResponseEntity.ok(new JwtDto(discodeitUserDetails.getUserDto(), newAccessToken));
  }
}
