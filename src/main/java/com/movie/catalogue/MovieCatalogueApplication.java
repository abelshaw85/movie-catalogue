package com.movie.catalogue;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootApplication
public class MovieCatalogueApplication {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@PostConstruct
	public void setUp() {
		// Allows mapping to LocalDate objects for Jackson
		objectMapper.registerModule(new JavaTimeModule());
	}

	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogueApplication.class, args);
	}

}
