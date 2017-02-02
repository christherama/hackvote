package com.teamtreehouse.hackvote.comment;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasRole('ROLE_USER')")
public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
}