package com.teamtreehouse.hackvote.vote;

import com.teamtreehouse.hackvote.core.AbstractEntity;
import com.teamtreehouse.hackvote.idea.Idea;
import com.teamtreehouse.hackvote.user.User;
import lombok.Getter;

import javax.persistence.Entity;

@Entity
@Getter
public class Vote extends AbstractEntity {
    private final User user;
    private final Idea idea;
    private Status status;

    public Vote() {
        this(null,null,null);
    }

    public Vote(Idea idea, User user, Status status) {
        this.idea = idea;
        this.user = user;
        this.status = status;
    }

    public void toggle() {
        status = status == Status.UP ? Status.DOWN : Status.UP;
    }

    public static enum Status {
        UP,DOWN;
    }
}