package com.teamtreehouse.hackvote.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teamtreehouse.hackvote.core.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends AbstractEntity implements UserDetails {
  public static PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

  private String username;

  private String password;

  @JsonIgnore
  private LocalDateTime lastPasswordChange;

  protected User() {
    super();
  }

  public User(String username, String password) {
    this();
    this.username = username;
    setPassword(password);
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isEnabled() {
    return true;
  }

  @JsonIgnore
  public String getRole() {
    return "ROLE_USER";
  }

  @Override
  @JsonIgnore
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return AuthorityUtils.createAuthorityList(getRole());
  }

  public void setPassword(String password) {
    this.password = PASSWORD_ENCODER.encode(password);
    this.lastPasswordChange = LocalDateTime.now();
  }
}