package com.teamtreehouse.hackvote.vote;

import com.teamtreehouse.hackvote.core.AbstractEntity;
import com.teamtreehouse.hackvote.idea.Idea;
import com.teamtreehouse.hackvote.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
@EqualsAndHashCode(of = {"user","idea"}, callSuper = true)
public class Vote extends AbstractEntity {
    @ManyToOne
    private User user;

    @ManyToOne
    private Idea idea;

    private Status status;

    protected Vote() {
        super();
    }

    public Vote(Idea idea, User user, Status status) {
        this();
        this.idea = idea;
        this.user = user;
        this.status = status;
    }

    public static enum Status {
        UP,DOWN;
    }
}