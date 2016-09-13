package com.teamtreehouse.hackvote.security;


import com.teamtreehouse.hackvote.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
    private static final String CLAIM_KEY_USERNAME = "subject";
    private static final String CLAIM_KEY_DATE_CREATED = "created";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateFromUserDetails(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_DATE_CREATED, new Date());
        return new TokenBuilder(secret,expiration).withClaims(claims).build();
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

    public LocalDateTime getTimeCreated(String token) {
        try {
            return LocalDateTime.parse(getClaims(token).get(CLAIM_KEY_DATE_CREATED).toString());
        } catch (Exception e) {
            return null;
        }
    }

    public LocalDateTime getExpirationTime(String token) {
        try {
            return localDateTimeFromDate(getClaims(token).getExpiration());
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
        return getExpirationTime(token).isBefore(LocalDateTime.now());
    }

    private boolean isCreatedBeforeLastPasswordReset(String token, User user) {
        LocalDateTime lastPwChange = user.getLastPasswordChange();
        return lastPwChange != null && getTimeCreated(token).isBefore(lastPwChange);
    }

    public boolean canTokenBeRefreshed(String token, User user) {
        return !isCreatedBeforeLastPasswordReset(token, user)
                && (!isTokenExpired(token));
    }

    public String refresh(String token) {
        try {
            final Claims claims = getClaims(token);
            claims.put(CLAIM_KEY_DATE_CREATED, LocalDateTime.now());
            return new TokenBuilder(secret,expiration).withClaims(claims).build();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isValid(String token, User user) {
        return
                getUsername(token).equals(user.getUsername())
                        && !isTokenExpired(token)
                        && !isCreatedBeforeLastPasswordReset(token, user);
    }

    // Temporary until Jwts updates to java.time API
    private static Date dateFromLocalDateTime(LocalDateTime ldt) {
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }
    private static LocalDateTime localDateTimeFromDate(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(),ZoneId.systemDefault());
    }

    private static class TokenBuilder {
        private String secret;
        private Long expiration;
        private Map<String,Object> claims;

        private TokenBuilder(String secret, Long expiration) {
            this.secret = secret;
            this.expiration = expiration;
        }

        private TokenBuilder withClaims(Map<String,Object> claims) {
            this.claims = claims;
            return this;
        }

        private String build() {
            return Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(dateFromLocalDateTime(generateExpirationDate()))
                    .signWith(SignatureAlgorithm.HS512, secret)
                    .compact();
        }

        private LocalDateTime generateExpirationDate() {
            System.out.printf("%n%nCurrent time: %s%n", LocalDateTime.now());
            System.out.printf("%n%nExpiration: %s%n%n", expiration);
            return LocalDateTime.now().plusSeconds(expiration);
        }
    }
}
