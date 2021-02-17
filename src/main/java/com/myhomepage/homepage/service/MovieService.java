package com.myhomepage.homepage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.myhomepage.homepage.entity.Actor;
import com.myhomepage.homepage.entity.Movie;
import com.myhomepage.homepage.entity.User;
import com.myhomepage.homepage.repository.MovieRepository;

@Service
public class MovieService {
	@Autowired
	private MovieRepository movieRepository;
	
	public Slice<Movie> findByUser(User user, Pageable pageable) {
		return movieRepository.findByUser(user, pageable);
	}
	
	public Movie findById(int movieId) {
		return movieRepository.findById(movieId).orElse(null);
	}
	
	public void save(Movie movie) {
		movieRepository.save(movie);
	}
	
	public List<Movie> findByUserAndActor(User user, Actor actor) {
		return movieRepository.findByUserAndActor(user, actor);
	}
	
	public Slice<Movie> findByUserAndTitle(User user, String title, Pageable pageable) {
		return movieRepository.findByUserAndTitleContainingIgnoreCase(user, title, pageable);
	}
	
	public void delete(Movie movie) {
		movieRepository.delete(movie);
	}

}
