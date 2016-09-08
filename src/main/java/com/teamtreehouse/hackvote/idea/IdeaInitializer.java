package com.teamtreehouse.hackvote.idea;

import com.teamtreehouse.hackvote.comment.Comment;
import com.teamtreehouse.hackvote.user.User;
import com.teamtreehouse.hackvote.user.UserRepository;
import com.teamtreehouse.hackvote.vote.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Arrays;

import static com.teamtreehouse.hackvote.vote.Vote.Status;

@Service
public class IdeaInitializer {

    @Autowired
    public IdeaInitializer(IdeaRepository ideas, UserRepository users) {
        Assert.notNull(ideas, "IdeaRepository must not be null.");
        Assert.notNull(users, "IdeaRepository must not be null.");

        if(ideas.count() > 0 || users.count() > 0) {
            return;
        }

        User user1 = new User("user1","password");
        User user2 = new User("user2","password");
        User user3 = new User("user3","password");
        users.save(Arrays.asList(user1,user2,user3));

        Idea vr = new Idea(user1,"VR game for learning Java","Creating a VR game for learning to code would give students a truly immersive experience into the syntax of a programming language.");
        vr.addComment(new Comment(user1,"Cool idea!"));
        vr.vote(new Vote(vr,user1,Status.UP));
        vr.vote(new Vote(vr,user2,Status.UP));
        vr.vote(new Vote(vr,user3,Status.DOWN));
        ideas.save(vr);
    }
}