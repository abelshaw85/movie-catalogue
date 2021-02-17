package com.myhomepage.homepage.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.myhomepage.homepage.entity.Actor;
import com.myhomepage.homepage.entity.Movie;
import com.myhomepage.homepage.entity.User;

@Repository
public interface MovieRepositoryCustom {
	// Finds movies by actor, but only for the logged in user
	List<Movie> findByUserAndActor(User user, Actor actor);	
}
