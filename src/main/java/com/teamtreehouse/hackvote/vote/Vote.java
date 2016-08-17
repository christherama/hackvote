package com.teamtreehouse.hackvote.vote;

import com.teamtreehouse.hackvote.core.AbstractEntity;
import com.teamtreehouse.hackvote.idea.Idea;
import com.teamtreehouse.hackvote.user.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@EqualsAndHashCode(of = {"user","idea"}, callSuper = true)
public class Vote extends AbstractEntity {
    @ManyToOne
    private final User user;

    @ManyToOne
    private Idea idea;

    @Setter
    private Status status;

    public Vote() {
        this(null,null,null);
    }

    public Vote(Idea idea, User user, Status status) {
        this.idea = idea;
        this.user = user;
        this.status = status;
    }

    public static enum Status {
        UP,DOWN;
    }
}