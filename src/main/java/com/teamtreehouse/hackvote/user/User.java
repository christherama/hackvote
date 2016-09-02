package com.teamtreehouse.hackvote.user;

import com.teamtreehouse.hackvote.core.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
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
}