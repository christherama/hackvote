package com.teamtreehouse.hackvote.comment;

import com.teamtreehouse.hackvote.core.AbstractEntity;
import com.teamtreehouse.hackvote.idea.Idea;
import com.teamtreehouse.hackvote.user.User;
import lombok.Getter;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Getter
public class Comment extends AbstractEntity {
    private final User user;
    private final Idea idea;
    private final LocalDateTime postedDate;

    public Comment() {
        this(null,null);
    }

    public Comment(User user, Idea idea) {
        this.user = user;
        this.idea = idea;
        this.postedDate = LocalDateTime.now();
    }
}
