package com.teamtreehouse.hackvote.idea;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = IdeaProjection.class)
public interface IdeaRepository extends PagingAndSortingRepository<Idea,Long>{
}