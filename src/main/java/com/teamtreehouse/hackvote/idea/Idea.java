package com.teamtreehouse.hackvote.idea;

import com.teamtreehouse.hackvote.comment.Comment;
import com.teamtreehouse.hackvote.core.AbstractEntity;
import com.teamtreehouse.hackvote.user.User;
import com.teamtreehouse.hackvote.vote.Vote;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.teamtreehouse.hackvote.vote.Vote.Status;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Idea extends AbstractEntity {

    @ManyToOne
    private User user;
    private LocalDateTime postedDate;
    private String title;
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Vote> votes;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    protected Idea() {
        super();
        this.postedDate = LocalDateTime.now();
        this.comments = new ArrayList<>();
        this.votes = new HashSet<>();
    }

    public Idea(User user, String title, String description) {
        this();
        this.user = user;
        this.title = title;
        this.description = description;
    }

    public int getNumComments() {
        return comments.size();
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void vote(Vote vote) {
        votes.add(vote);
    }

    public long getUpVotes() {
        return votes.stream().filter(v -> v.getStatus() == Status.UP).count();
    }

    public long getDownVotes() {
        return votes.stream().filter(v -> v.getStatus() == Status.DOWN).count();
    }
}