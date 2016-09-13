package com.teamtreehouse.hackvote.idea;

import com.teamtreehouse.hackvote.core.AbstractEventHandler;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler(Idea.class)
public class IdeaEventHandler extends AbstractEventHandler {
    @HandleBeforeCreate
    public void addCreator(Idea idea) {
        idea.setUser(getAuthenticatedUser());
    }
}
