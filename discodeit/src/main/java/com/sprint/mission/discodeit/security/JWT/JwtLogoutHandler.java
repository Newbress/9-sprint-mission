package com.sprint.mission.discodeit.security.JWT;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtLogoutHandler implements LogoutHandler {

  private final JwtRegistry jwtRegistry;
  private final JwtTokenProvider jwtTokenProvider;


  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) {

    if (request.getCookies() != null) {
      Arrays.stream(request.getCookies())
          .filter(cookie -> cookie.getName().equals("REFRESH_TOKEN"))
          .findFirst()
          .ifPresent(cookie -> {
            String refreshToken = cookie.getValue();
            if (jwtTokenProvider.validateToken(refreshToken)) {
              String username = jwtTokenProvider.getUsername(refreshToken);
              jwtRegistry.invalidateJwtInformationByUserId(
                  jwtTokenProvider.getUserId(refreshToken)
              );
            }
            // 쿠키 삭제
            cookie.setValue(null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
          });
    }
  }
}