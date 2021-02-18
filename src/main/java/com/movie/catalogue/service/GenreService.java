package com.movie.catalogue.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.catalogue.entity.Genre;
import com.movie.catalogue.repository.GenreRepository;
import com.movie.catalogue.utility.CommaSeparatedValueConverter;

@Service
public class GenreService {
	
	@Autowired
	private GenreRepository genreRepository;
	
	public List<Genre> findGenresByCommaSeparatedValues(String string) {
		List<Genre> genres = new ArrayList<>();
		
		if (string != null && !string.isBlank()) {	
			String[] genresArray = CommaSeparatedValueConverter.stringToArray(string);
			
			for (String genreName: genresArray) {
				Genre genre = genreRepository.findByName(genreName);
				if (genre == null) {
					System.out.println("===>> " + genreName + " WAS NOT FOUND. Add to Genres table?");
				} else {
					genres.add(genre);
				}
			}
		}
		return genres;
	}
	
	public Genre findByName(String name) {
		return genreRepository.findByName(name);
	}
	
	public List<Genre> findAll() {
		return genreRepository.findAll();
	}

}
