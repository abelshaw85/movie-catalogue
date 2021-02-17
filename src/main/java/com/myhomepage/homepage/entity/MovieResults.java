package com.myhomepage.homepage.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.myhomepage.homepage.dto.MovieResultDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieResults {
	
	public MovieResults() {
		
	}
	
	private List<MovieResultDTO> movieResultDtos;
	private boolean response;
	private String error;
	private int totalResults;

	@JsonProperty("Search")
	public List<MovieResultDTO> getMovieResultDtos() {
		return movieResultDtos;
	}

	@JsonProperty("Search")
	public void setMovieResultDtos(List<MovieResultDTO> movieDtos) {
		this.movieResultDtos = movieDtos;
	}
	
	@JsonProperty("Response")
	public boolean getResponse() {
		return response;
	}

	@JsonProperty("Response")
	public void setResponse(boolean response) {
		this.response = response;
	}

	@JsonProperty("Error")
	public String getError() {
		return error;
	}

	@JsonProperty("Error")
	public void setError(String error) {
		this.error = error;
	}
	
	@JsonProperty("totalResults")
	public int getTotalResults() {
		return totalResults;
	}

	@JsonProperty("totalResults")
	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}	

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (MovieResultDTO m : movieResultDtos) {
			sb.append(m.getTitle() + "\n");
		}
		return sb.toString();
	}

}
