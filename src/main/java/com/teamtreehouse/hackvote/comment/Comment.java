package com.teamtreehouse.hackvote.comment;

import com.teamtreehouse.hackvote.core.AbstractEntity;
import com.teamtreehouse.hackvote.user.User;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
public class Comment extends AbstractEntity {
    @ManyToOne
    private final User user;
    private final LocalDateTime postedDate;
    private final String comment;

    public Comment() {
        this(null,null);
    }

    public Comment(User user, String comment) {
        this.user = user;
        this.comment = comment;
        this.postedDate = LocalDateTime.now();
    }
}