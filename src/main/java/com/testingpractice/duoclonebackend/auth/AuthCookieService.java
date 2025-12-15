package com.testingpractice.duoclonebackend.auth;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthCookieService {

  @Value("${auth.cookie.name:jwt}")
  private String cookieName;

  @Value("${auth.cookie.path:/}")
  private String cookiePath;

  @Value("${auth.cookie.domain}")
  private String cookieDomain;

  @Value("${auth.cookie.same-site:none}")
  private String sameSite;

  @Value("${auth.cookie.secure:true}")
  private boolean secure;

  @Value("${auth.cookie.max-age-seconds:86400}")
  private long defaultMaxAge;

  public void setJwt(HttpServletResponse response, String jwt) {
    addCookie(response, jwt, defaultMaxAge);
  }

  public void setJwt(HttpServletResponse response, String jwt, long maxAgeSeconds) {
    addCookie(response, jwt, maxAgeSeconds);
  }

  public void clearJwt(HttpServletResponse response) {
    addCookie(response, "", 0);
  }

  public @Nullable String readJwt(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null) return null;
    for (Cookie c : cookies) {
      if (cookieName.equals(c.getName())) return c.getValue();
    }
    return null;
  }

  private void addCookie(HttpServletResponse response, String value, long maxAgeSeconds) {
    ResponseCookie cookie =
        ResponseCookie.from(cookieName, value)
            .httpOnly(true)
            .secure(secure)
            .sameSite(sameSite)
            .path(cookiePath)
            .maxAge(maxAgeSeconds)
            .domain(cookieDomain.isBlank() ? null : cookieDomain)
            .build();

    response.addHeader("Set-Cookie", cookie.toString());
  }
}
