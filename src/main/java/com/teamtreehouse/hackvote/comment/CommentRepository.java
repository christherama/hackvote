package com.teamtreehouse.hackvote.comment;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CommentRepository extends PagingAndSortingRepository<Comment,Long> {
}