package com.myhomepage.homepage.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TitleSearch {
	
	private int id;
	@NotNull(message = "Title field cannot be empty.")
	@Size(min = 1, message = "Title field cannot be empty.")
	private String term = "";
	
	public TitleSearch() {
		
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
