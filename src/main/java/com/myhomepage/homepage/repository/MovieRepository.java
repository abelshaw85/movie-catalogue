package com.myhomepage.homepage.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myhomepage.homepage.entity.Movie;
import com.myhomepage.homepage.entity.User;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer>, MovieRepositoryCustom {
	Slice<Movie> findByUser(User user);
	Slice<Movie> findByUser(User user, Pageable pageable);
	Slice<Movie> findByUserAndTitleContainingIgnoreCase(User user, String title, Pageable pageable);
}
