package com.teamtreehouse.hackvote.comment;

import com.teamtreehouse.hackvote.core.AbstractEventHandler;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler(Comment.class)
public class CommentEventHandler extends AbstractEventHandler<Comment> {
}
