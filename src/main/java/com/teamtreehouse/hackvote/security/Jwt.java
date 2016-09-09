package com.teamtreehouse.hackvote.security;


import com.teamtreehouse.hackvote.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Jwt {
    private static final String CLAIM_KEY_USERNAME = "subject";
    private static final String CLAIM_KEY_DATE_CREATED = "created";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private String token;

    public Jwt(String token) {
        this.token = token;
    }

    public Jwt fromUserDetails(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_DATE_CREATED, new Date());
        return new JwtBuilder(secret,expiration).withClaims(claims).build();
    }

    public String getUsername() {
        String username;
        try {
            final Claims claims = getClaims();
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public LocalDateTime getTimeCreated() {
        try {
            return LocalDateTime.parse(getClaims().get(CLAIM_KEY_DATE_CREATED).toString());
        } catch (Exception e) {
            return null;
        }
    }

    public LocalDateTime getExpirationTime() {
        try {
            return localDateTimeFromDate(getClaims().getExpiration());
        } catch (Exception e) {
            return null;
        }
    }

    private Claims getClaims() {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    private boolean isTokenExpired() {
        return getExpirationTime().isBefore(LocalDateTime.now());
    }

    private boolean isCreatedBeforeLastPasswordReset(User user) {
        LocalDateTime lastPwChange = user.getLastPasswordChange();
        return lastPwChange != null && getTimeCreated().isBefore(lastPwChange);
    }

    public boolean canTokenBeRefreshed(User user) {
        return !isCreatedBeforeLastPasswordReset(user)
                && (!isTokenExpired());
    }

    public void refresh() {
        try {
            final Claims claims = getClaims();
            claims.put(CLAIM_KEY_DATE_CREATED, LocalDateTime.now());
            this.token = new JwtBuilder(secret,expiration).withClaims(claims).build().getToken();
        } catch (Exception e) {
            this.token = null;
        }
    }

    public boolean isValid(User user) {
        return
                getUsername().equals(user.getUsername())
                        && !isTokenExpired()
                        && !isCreatedBeforeLastPasswordReset(user);
    }

    public String getToken() {
        return token;
    }

    // Temporary until Jwts updates to java.time API
    private static Date dateFromLocalDateTime(LocalDateTime ldt) {
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }
    private static LocalDateTime localDateTimeFromDate(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(),ZoneId.systemDefault());
    }

    private static class JwtBuilder {
        private String secret;
        private Long expiration;
        private Map<String,Object> claims;

        private JwtBuilder(String secret, Long expiration) {
            this.secret = secret;
            this.expiration = expiration;
        }

        private JwtBuilder withClaims(Map<String,Object> claims) {
            this.claims = claims;
            return this;
        }

        private Jwt build() {
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(dateFromLocalDateTime(generateExpirationDate()))
                    .signWith(SignatureAlgorithm.HS512, secret)
                    .compact();
            return new Jwt(token);
        }

        private LocalDateTime generateExpirationDate() {
            return LocalDateTime.now().plusSeconds(expiration);
        }
    }
}
