package com.teamtreehouse.hackvote.user;

import com.teamtreehouse.hackvote.core.AbstractEntity;
import lombok.Getter;

import javax.persistence.Entity;

@Entity
@Getter
public class User extends AbstractEntity {
    private final String username;

    public User() {
        this(null);
    }

    public User(String username) {
        this.username = username;
    }
}