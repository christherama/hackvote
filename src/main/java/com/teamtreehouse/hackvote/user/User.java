package com.teamtreehouse.hackvote.user;

import com.teamtreehouse.hackvote.core.AbstractEntity;

import javax.persistence.Entity;

@Entity
public class User extends AbstractEntity {
    private String username;
    private String password;

    protected User() {
        super();
    }

    public User(String username, String password) {
        this();
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        User u = (User)o;
        return getId().equals(u.getId());
    }
}