package com.teamtreehouse.hackvote.idea;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "ideaSummary", types = Idea.class)
public interface IdeaProjection {
    String getTitle();
    String getNumComments();
    int getUpVotes();
    int getDownVotes();
}