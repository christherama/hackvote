package com.teamtreehouse.hackvote.idea;

import com.teamtreehouse.hackvote.core.AbstractEventHandler;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler(Idea.class)
public class IdeaEventHandler extends AbstractEventHandler<Idea> {
}
