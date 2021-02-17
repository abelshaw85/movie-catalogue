package com.myhomepage.homepage.model;

import javax.validation.constraints.NotNull;

import com.myhomepage.homepage.validator.ImdbId;

public class ImdbSearch {
	
	private int id;
	
	@NotNull(message = "Search term cannot be empty.")
	@ImdbId(message = "Invalid IMDb format. Must begin 'tt' followed by 5-6 numbers.")
	private String term = "";
	
	public ImdbSearch() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

}
