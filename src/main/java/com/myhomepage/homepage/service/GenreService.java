package com.myhomepage.homepage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myhomepage.homepage.entity.Genre;
import com.myhomepage.homepage.repository.GenreRepository;
import com.myhomepage.homepage.utility.CommaSeparatedValueConverter;

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
