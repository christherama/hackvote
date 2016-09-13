package com.teamtreehouse.hackvote.vote;


import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends CrudRepository<Vote,Long> {
}
