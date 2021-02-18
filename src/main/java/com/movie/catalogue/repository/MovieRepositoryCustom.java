package com.movie.catalogue.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.movie.catalogue.entity.Actor;
import com.movie.catalogue.entity.Movie;
import com.movie.catalogue.entity.User;

@Repository
public interface MovieRepositoryCustom {
	// Finds movies by actor, but only for the logged in user
	List<Movie> findByUserAndActor(User user, Actor actor);	
}
