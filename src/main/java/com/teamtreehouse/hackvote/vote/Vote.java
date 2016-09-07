package com.teamtreehouse.hackvote.vote;

import com.teamtreehouse.hackvote.core.AbstractEntity;
import com.teamtreehouse.hackvote.idea.Idea;
import com.teamtreehouse.hackvote.user.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Idea getIdea() {
        return idea;
    }

    public void setIdea(Idea idea) {
        this.idea = idea;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        Vote v = (Vote)o;
        return getIdea().equals(v.getIdea()) && getUser().equals(v.getUser());
    }

    public static enum Status {
        UP,DOWN;
    }
}