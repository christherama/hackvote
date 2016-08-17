package com.teamtreehouse.hackvote.idea;

import com.teamtreehouse.hackvote.core.AbstractEntity;
import com.teamtreehouse.hackvote.user.User;
import com.teamtreehouse.hackvote.vote.Vote;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

import static com.teamtreehouse.hackvote.vote.Vote.Status;

@Entity
@Getter
public class Idea extends AbstractEntity {

    private final User user;
    private final LocalDateTime postedDate;
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vote> votes;

    public Idea() {
        this(null,null);
    }

    public Idea(User user, String description) {
        this.user = user;
        this.description = description;
        this.postedDate = LocalDateTime.now();
    }

    public long getUpVotes() {
        return votes.stream().filter(v -> v.getStatus() == Status.UP).count();
    }

    public long getDownVotes() {
        return votes.stream().filter(v -> v.getStatus() == Status.DOWN).count();
    }
}
