package com.teamtreehouse.hackvote.vote;

import com.teamtreehouse.hackvote.core.AbstractEventHandler;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler(Vote.class)
public class VoteEventHandler extends AbstractEventHandler<Vote> {
}
