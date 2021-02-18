package com.movie.catalogue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.movie.catalogue.entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, String> {
	Genre findByName(String name);
}
