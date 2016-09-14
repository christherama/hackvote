package com.teamtreehouse.hackvote.idea;

import com.teamtreehouse.hackvote.comment.Comment;
import com.teamtreehouse.hackvote.core.AbstractEntity;
import com.teamtreehouse.hackvote.core.UserData;
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
@EqualsAndHashCode(callSuper = false, exclude = {"votes","comments"})
public class Idea extends AbstractEntity implements UserData {

    @ManyToOne
    private User user;
    private LocalDateTime postedDate;
    private String title;
    private String description;

    @OneToMany(mappedBy = "idea", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Vote> votes;

    @OneToMany(mappedBy = "idea", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public int getNumComments() {
        return comments.size();
    }

    public void vote(Vote vote) {
        votes.add(vote);
    }

    public long getNumUpVotes() {
        return votes.stream().filter(v -> v.getStatus() == Status.UP).count();
    }

    public long getNumDownVotes() {
        return votes.stream().filter(v -> v.getStatus() == Status.DOWN).count();
    }
}