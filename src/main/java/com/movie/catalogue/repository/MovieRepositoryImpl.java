package com.movie.catalogue.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.movie.catalogue.entity.Actor;
import com.movie.catalogue.entity.Movie;
import com.movie.catalogue.entity.User;

public class MovieRepositoryImpl implements MovieRepositoryCustom {
	
	@PersistenceContext
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Movie> findByUserAndActor(User user, Actor actor) {
		// LEFT JOIN with the FETCH keyword will eager-load this table
		Query query = entityManager.createQuery(
			"FROM Movie as m LEFT JOIN FETCH m.actors a WHERE m.user=:user AND a.id=:actorId", Movie.class);
		// Parameterised queries are safe from SQL injection.
		query.setParameter("user", user);
		query.setParameter("actorId", actor.getId());
		return query.getResultList();
	}

}