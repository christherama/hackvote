package com.teamtreehouse.hackvote.user;

import com.teamtreehouse.hackvote.core.AbstractEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
public class User extends AbstractEntity implements UserDetails {
    public static PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    private String username;
    private String password;
    private LocalDateTime lastPasswordChange;

    protected User() {
        super();
    }

    public User(String username, String password) {
        this();
        this.username = username;
        setPassword(password);
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getRole() {
        return "ROLE_USER";
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(getRole());
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = PASSWORD_ENCODER.encode(password);
        this.lastPasswordChange = LocalDateTime.now();
    }

    public LocalDateTime getLastPasswordChange() {
        return lastPasswordChange;
    }

    public void setLastPasswordChange(LocalDateTime lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }

    @Override
    public boolean equals(Object o) {
        User u = (User)o;
        return getId().equals(u.getId());
    }
}