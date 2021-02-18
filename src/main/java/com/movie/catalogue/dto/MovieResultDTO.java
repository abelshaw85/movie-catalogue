package com.movie.catalogue.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MovieResultDTO {

	private String title;
	private String year;
	private String imdbId;
	
	public MovieResultDTO() {}

	@JsonProperty("Title")
	public String getTitle() {
		return title;
	}
	
	@JsonProperty("Title")
	public void setTitle(String title) {
		this.title = title;
	}
	
	@JsonProperty("Year")
	public String getYear() {
		return year;
	}
	
	@JsonProperty("Year")
	public void setYear(String year) {
		this.year = year;
	}
	
	@JsonProperty("imdbID")
	public String getImdbId() {
		return imdbId;
	}
	
	@JsonProperty("imdbID")
	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}
	
}
