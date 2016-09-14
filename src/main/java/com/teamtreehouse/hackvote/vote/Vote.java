package com.teamtreehouse.hackvote.vote;

import com.teamtreehouse.hackvote.core.AbstractEntity;
import com.teamtreehouse.hackvote.core.UserData;
import com.teamtreehouse.hackvote.idea.Idea;
import com.teamtreehouse.hackvote.user.User;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
public class Vote extends AbstractEntity implements UserData {
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

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Vote)) return false;
        Vote v = (Vote)o;
        return getIdea().equals(v.getIdea()) && getUser().equals(v.getUser());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (idea != null ? idea.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    public static enum Status {
        UP,DOWN;
    }
}