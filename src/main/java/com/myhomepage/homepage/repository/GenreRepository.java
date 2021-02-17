package com.myhomepage.homepage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myhomepage.homepage.entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, String> {
	Genre findByName(String name);
}
