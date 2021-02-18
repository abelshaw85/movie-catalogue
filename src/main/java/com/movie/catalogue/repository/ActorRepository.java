package com.movie.catalogue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movie.catalogue.entity.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {
	Actor findByName(String name);
}
