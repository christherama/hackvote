package com.teamtreehouse.hackvote.idea;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource(excerptProjection = IdeaProjection.class)
@PreAuthorize("hasRole('USER')")
public interface IdeaRepository extends PagingAndSortingRepository<Idea,Long>{
}