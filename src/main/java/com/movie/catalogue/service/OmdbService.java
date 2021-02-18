package com.movie.catalogue.service;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.catalogue.dto.MovieDTO;
import com.movie.catalogue.entity.MovieResults;

@Service
public class OmdbService {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private Environment environment; //used to get values from application.properties
  
	
	public MovieResults findByTitle(String title, int pageNumber) {		
		String urlString = getOmdbUrl() + "&s=" + title + "&type=movie";
		if (pageNumber > 0) {
			urlString = urlString + "&page=" + pageNumber;
		}
		try {
			URL url = new URL(urlString);
			MovieResults movieSearchResults = objectMapper.readValue(url, MovieResults.class);
			return movieSearchResults;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public MovieDTO findByImdbId(String imdbId) {
		MovieDTO movieDto = new MovieDTO();
		
		String urlString = getOmdbUrl() + "&i=" + imdbId;
		
		try {
			URL url = new URL(urlString);
			movieDto = objectMapper.readValue(url, MovieDTO.class);
			if (!movieDto.getResponse()) { //if omdb gives a false/error response
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return movieDto;
	}
	
	private String getOmdbUrl() {
		return "http://www.omdbapi.com/?apikey=" + environment.getProperty("omdb.api.key");
	}
	
	/*{"Title":"The Shawshank Redemption",
	 * "Year":"1994",
	 * "Rated":"R",
	 * "Released":"14 Oct 1994",
	 * "Runtime":"142 min",
	 * "Genre":"Drama",
	 * "Director":"Frank Darabont",
	 * "Writer":"Stephen King (short story \"Rita Hayworth and Shawshank Redemption\"), Frank Darabont (screenplay)",
	 * "Actors":"Tim Robbins, Morgan Freeman, Bob Gunton, William Sadler",
	 * "Plot":"Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
	 * "Language":"English",
	 * "Country":"USA",
	 * "Awards":"Nominated for 7 Oscars. Another 21 wins & 36 nominations.",
	 * "Poster":"https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg",
	 * "Ratings":[{"Source":"Internet Movie Database","Value":"9.3/10"},{"Source":"Rotten Tomatoes","Value":"91%"},{"Source":"Metacritic","Value":"80/100"}],
	 * "Metascore":"80",
	 * "imdbRating":"9.3",
	 * "imdbVotes":"2,296,430",
	 * "imdbID":"tt0111161",
	 * "Type":"movie",
	 * "DVD":"N/A",
	 * "BoxOffice":"N/A",
	 * "Production":"Columbia Pictures, Castle Rock Entertainment",
	 * "Website":"N/A",
	 * "Response":"True"}*/
}
