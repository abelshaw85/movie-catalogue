package com.movie.catalogue.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="genres")
public class Genre {
	
	@Id
	@Column(name="name")
	private String name;
	
	public Genre() {
		
	}
	
	public Genre(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Genre [name=" + name + "]";
	}

}
