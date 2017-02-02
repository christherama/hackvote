package com.teamtreehouse.hackvote.idea;

import com.teamtreehouse.hackvote.comment.Comment;
import com.teamtreehouse.hackvote.user.User;
import com.teamtreehouse.hackvote.user.UserRepository;
import com.teamtreehouse.hackvote.vote.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Arrays;

import static com.teamtreehouse.hackvote.vote.Vote.Status;

@Component
public class IdeaInitializer implements ApplicationRunner {
  private IdeaRepository ideas;
  private UserRepository users;

  @Autowired
  public IdeaInitializer(IdeaRepository ideas, UserRepository users) {
    Assert.notNull(ideas, "IdeaRepository must not be null.");
    Assert.notNull(users, "IdeaRepository must not be null.");

    this.ideas = ideas;
    this.users = users;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    User admin = new User("admin", "password");
    runAsUser(admin, this::addIdeas);
  }

  private void addIdeas() {
    if (ideas.count() > 0 || users.count() > 0) {
      return;
    }
    User user1 = new User("user1", "password");
    User user2 = new User("user2", "password");
    User user3 = new User("user3", "password");
    User user4 = new User("user4", "password");

    users.save(Arrays.asList(user1, user2, user3, user4));

    Idea vr = new Idea(user1, "VR game for learning Java", "Creating a VR game for learning to code would give students a truly immersive experience into the syntax of a programming language.");
    vr.addComment(new Comment(user2, "Cool idea!"));
    vr.vote(new Vote(vr, user4, Status.UP));
    vr.vote(new Vote(vr, user2, Status.UP));
    vr.vote(new Vote(vr, user3, Status.DOWN));
    ideas.save(vr);

    vr = new Idea(user2, "TP roll empty detector system", "Ever run out of TP at exactly the wrong time? Never. Again.");
    vr.addComment(new Comment(user3, "Cleanliness is next to godliness."));
    vr.vote(new Vote(vr, user3, Status.UP));
    vr.vote(new Vote(vr, user1, Status.UP));
    vr.vote(new Vote(vr, user4, Status.UP));
    ideas.save(vr);

    vr = new Idea(user4, "Get notified about anything", "Like Uber for notifications about events, device releases, concerts, sales, or anything that has to do with anything.");
    vr.addComment(new Comment(user2, "How is this anything like Uber?"));
    vr.addComment(new Comment(user4, "What is Uber?"));
    vr.vote(new Vote(vr, user1, Status.DOWN));
    vr.vote(new Vote(vr, user2, Status.UP));
    vr.vote(new Vote(vr, user3, Status.DOWN));
    ideas.save(vr);
  }

  private void runAsUser(User user, Runnable runnable) {
    SecurityContextHolder.clearContext();
    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
        user.getUsername(),
        user.getPassword(),
        AuthorityUtils.createAuthorityList(user.getRole())
    ));
    runnable.run();
    SecurityContextHolder.clearContext();
  }
}