package com.teamtreehouse.hackvote.vote;

import com.teamtreehouse.hackvote.core.AbstractEventHandler;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

@RepositoryEventHandler(Vote.class)
public class VoteEventHandler extends AbstractEventHandler<Vote> {
}
