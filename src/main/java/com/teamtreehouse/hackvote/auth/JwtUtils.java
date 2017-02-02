package com.teamtreehouse.hackvote.auth;


import com.teamtreehouse.hackvote.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtUtils {
  private static final String CLAIM_KEY_ROLE = "role";

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private Long expiration;

  public String generateFromUser(User user) {
    return new TokenBuilder(secret, expiration).withUser(user).build();
  }

  public String getUsername(String token) {
    String username;
    try {
      final Claims claims = getClaims(token);
      username = claims.getSubject();
    } catch (Exception e) {
      username = null;
    }
    return username;
  }

  public Date getDateIssued(String token) {
    try {
      return getClaims(token).getIssuedAt();
    } catch (Exception e) {
      return null;
    }
  }

  public Date getExpirationDate(String token) {
    try {
      return getClaims(token).getExpiration();
    } catch (Exception e) {
      return null;
    }
  }

  public String getRole(String token) {
    try {
      return getClaims(token).get(CLAIM_KEY_ROLE, String.class);
    } catch (Exception e) {
      return null;
    }
  }

  private Claims getClaims(String token) {
    try {
      return Jwts.parser()
          .setSigningKey(secret)
          .parseClaimsJws(token)
          .getBody();
    } catch (Exception e) {
      return null;
    }
  }

  private boolean isTokenExpired(String token) {
    return getExpirationDate(token).before(new Date());
  }

  private boolean wasIssuedBeforeLastPasswordReset(String token, User user) {
    LocalDateTime lastPwChange = user.getLastPasswordChange();
    return lastPwChange != null && getDateIssued(token).before(Date.from(lastPwChange.atZone(ZoneId.systemDefault()).toInstant()));
  }

  public boolean canTokenBeRefreshed(String token, User user) {
    return !wasIssuedBeforeLastPasswordReset(token, user)
        && (!isTokenExpired(token));
  }

  public String refresh(String token) {
    try {
      Claims claims = getClaims(token);
      return new TokenBuilder(secret, expiration).withClaims(claims).build();
    } catch (Exception e) {
      return null;
    }
  }

  public boolean isValid(String token) {
    return token != null && getUsername(token) != null && !isTokenExpired(token);
  }

  private static class TokenBuilder {
    private String secret;
    private Long expiration;
    private Claims claims;

    private TokenBuilder(String secret, Long expiration) {
      this.secret = secret;
      this.expiration = expiration;
      this.claims = Jwts.claims();
    }

    private TokenBuilder withUser(User user) {
      claims.setSubject(user.getUsername());
      claims.put(CLAIM_KEY_ROLE, user.getRole());
      return this;
    }

    private TokenBuilder withClaims(Claims claims) {
      this.claims = claims;
      return this;
    }

    private String build() {
      return Jwts.builder()
          .setClaims(claims)
          .setIssuedAt(new Date())
          .setExpiration(generateExpirationDate())
          .signWith(SignatureAlgorithm.HS512, secret)
          .compact();
    }

    private Date generateExpirationDate() {
      return new Date(System.currentTimeMillis() + expiration);
    }
  }
}
