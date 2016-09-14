package com.teamtreehouse.hackvote.comment;

import com.teamtreehouse.hackvote.core.AbstractEntity;
import com.teamtreehouse.hackvote.core.UserData;
import com.teamtreehouse.hackvote.idea.Idea;
import com.teamtreehouse.hackvote.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Comment extends AbstractEntity implements UserData {
    @ManyToOne
    private Idea idea;

    @ManyToOne
    private User user;
    private LocalDateTime postedDate;
    private String comment;

    protected Comment() {
        super();
        postedDate = LocalDateTime.now();
    }

    public Comment(User user, String comment) {
        this();
        this.user = user;
        this.comment = comment;
    }
}