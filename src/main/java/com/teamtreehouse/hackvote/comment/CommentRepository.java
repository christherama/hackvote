package com.teamtreehouse.hackvote.comment;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentRepository extends PagingAndSortingRepository<Comment,Long> {
}