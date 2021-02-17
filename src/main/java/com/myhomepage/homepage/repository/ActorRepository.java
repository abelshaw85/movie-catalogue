package com.myhomepage.homepage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myhomepage.homepage.entity.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {
	Actor findByName(String name);
}
