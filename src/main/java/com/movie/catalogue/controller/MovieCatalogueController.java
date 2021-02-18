package com.movie.catalogue.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.movie.catalogue.dto.MovieDTO;
import com.movie.catalogue.entity.Actor;
import com.movie.catalogue.entity.Genre;
import com.movie.catalogue.entity.Movie;
import com.movie.catalogue.entity.User;
import com.movie.catalogue.model.ErrorContainer;
import com.movie.catalogue.model.ErrorLevel;
import com.movie.catalogue.model.TitleSearch;
import com.movie.catalogue.service.ActorService;
import com.movie.catalogue.service.GenreService;
import com.movie.catalogue.service.MovieService;
import com.movie.catalogue.service.OmdbService;
import com.movie.catalogue.service.UserService;

@Controller
@RequestMapping("/movie-catalogue")
public class MovieCatalogueController {	
	
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private GenreService genreService;
	
	@Autowired
	private OmdbService omdbService;
	
	//List of genres used to populate form
	private List<Genre> genres;
	
	@Autowired
	public MovieCatalogueController(GenreService genreService) {
		this.genreService = genreService;
		this.genres = genreService.findAll();
	}
	
	/* This will trim whitespace from all fields, which prevents empty fields that contain only spaces from being submitted. 
	 * Also prevents superflous white spaces e.g. "     title" */
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		// true = trim to null if all whitespace
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	
	// Movie Catalogue root
	@GetMapping("")
	public String getMovieList(
			Model model, 
			Principal principal, 
			@RequestParam Optional<Integer> page) {
		String username = principal.getName();		
		User user = userService.findByUserName(username);
		ModelMapper modelMapper = new ModelMapper();
		
		int pageNumber = page.orElse(0);
		Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by("title")); // Page number, how many elements. Starts at page 0.

		Slice<Movie> sliceMovieDtos = movieService.findByUser(user, pageable);
		List<MovieDTO> movieDtos = sliceMovieDtos
				  .stream()
				  .map(movie -> modelMapper.map(movie, MovieDTO.class))
				  .collect(Collectors.toList());
		
		model.addAttribute("message", "Your Movies");
		model.addAttribute("movieList", movieDtos);
		if (!model.containsAttribute("search")) {
	        model.addAttribute("search", new TitleSearch());
	    }
		model.addAttribute("page", pageNumber);
		model.addAttribute("hasNextPage", sliceMovieDtos.hasNext());
		
		return "movie-catalogue/movie-list";
	}
	
	// Add new Movie
	@GetMapping("/add")
	public String showAddMovieForm(Model model) {
		MovieDTO movieDTO = new MovieDTO();
		
		model.addAttribute("genres", genres);
		model.addAttribute("movieDTO", movieDTO);
		
		return "movie-catalogue/movie-form";
	}
	
	@GetMapping("/add/{imdbId}")
	public String addByOmdb(Model model, @PathVariable String imdbId) {
		MovieDTO movieDto = omdbService.findByImdbId(imdbId);
		List<Genre> genresToSelect = genreService.findGenresByCommaSeparatedValues(movieDto.getGenre());
		movieDto.setGenres(genresToSelect);
		
		model.addAttribute("movieDTO", movieDto);
		model.addAttribute("genres", genres);
		model.addAttribute("search", new TitleSearch());
		return "movie-catalogue/movie-form";
	}
	
	// Update existing movie
	@GetMapping("/edit/{movieId}")
	public String showUpdateMovieForm(Model model, @PathVariable Integer movieId, Principal principal) {
		Movie movie = movieService.findById(movieId);
		if (movie == null) {
			return this.showErrorPage(model, 404);
		}
		
		if (!principal.getName().equals(movie.getUser().getUserName())) {
			return this.showErrorPage(model, 403);
		}
		ModelMapper modelMapper = new ModelMapper();
		
		MovieDTO movieDto = modelMapper.map(movie, MovieDTO.class);		
		
		model.addAttribute("movieDTO", movieDto);
		model.addAttribute("genres", genres);
		
		return "movie-catalogue/movie-form";
	}
	
	@GetMapping("/edit/{movieId}/{imdbId}")
	public String showUpdateMovieFormWithImdbId(Model model, @PathVariable Integer movieId, @PathVariable String imdbId) {
		MovieDTO movieFromImdb = omdbService.findByImdbId(imdbId);
		movieFromImdb.setId(movieId);
		List<Genre> genresToSelect = genreService.findGenresByCommaSeparatedValues(movieFromImdb.getGenre());
		movieFromImdb.setGenres(genresToSelect);
		
		model.addAttribute("movieDTO", movieFromImdb);
		model.addAttribute("genres", genres);
		
		return "movie-catalogue/movie-form";
	}
	
	// Save a movie
	@PostMapping("/save")
	public String saveMovie(
			Principal principal,
			Model model,
			@Valid @ModelAttribute("movieDTO") MovieDTO movieDto,
			BindingResult bindingResult) {
		// BindingResult must follow the @Valid property we are validating
		if (bindingResult.hasErrors()) {
			model.addAttribute("genres", genres);
			return "movie-catalogue/movie-form";
		}
		
		String username = principal.getName();
		User user = userService.findByUserName(username);
		
		movieDto.setUser(user);
		
		List<Actor> actors = actorService.findActorsByCommaSeparatedValues(movieDto.getActorsString());
		movieDto.setActors(actors);
		
		// Note that Spring will automatically add/delete join table entries for Genre depending on if the Genre is present or not.
		ModelMapper modelMapper = new ModelMapper();
		Movie movieToSave = modelMapper.map(movieDto, Movie.class);
		movieService.save(movieToSave);
		
		return "redirect:/movie-catalogue"; 
	}
	
	// View existing movie
	@GetMapping("/movie/{movieId}")
	public String viewMovieDetails(Model model, @PathVariable String movieId, Principal principal) {
		String numberRegex = "[0-9]+";
		
		if (!movieId.matches(numberRegex)) {
			return showErrorPage(model, 400);
		}
		
		int id = Integer.valueOf(movieId);
		
		Movie movie = movieService.findById(id);;
		if (movie == null) {
			return this.showErrorPage(model, 404);
		}
		
		// Users may only view their own movies
		if (!principal.getName().equals(movie.getUser().getUserName())) {
			return this.showErrorPage(model, 403);
		}
		
		ModelMapper modelMapper = new ModelMapper();
		MovieDTO movieDto = modelMapper.map(movie, MovieDTO.class);
		model.addAttribute("movieDTO", movieDto);
		return "movie-catalogue/movie-details";
	}

	// Get user's movies by actor
	@GetMapping("/actor/{actorId}")
	public String getMovieListByActor(Model model, Principal principal, @PathVariable Integer actorId) {
		String username = principal.getName();
		User user = userService.findByUserName(username);
		Actor actor = actorService.findById(actorId);
		
		ModelMapper modelMapper = new ModelMapper();
		List<MovieDTO> movieDtos = movieService.findByUserAndActor(user, actor)
				.stream()
				  .map(movie -> modelMapper.map(movie, MovieDTO.class))
				  .collect(Collectors.toList());
		if (!model.containsAttribute("search")) {
	        model.addAttribute("search", new TitleSearch());
	    }
		model.addAttribute("movieList", movieDtos);
		model.addAttribute("message", "List of movies for actor " + actor.getName());
		
		return "movie-catalogue/movie-list";
	}
	
	@GetMapping("/delete/{movieId}")
	public String deleteMovieById(Model model, Principal principal, @PathVariable Integer movieId) {
		Movie movie = movieService.findById(movieId);
		if (movie != null) {
			if (!movie.getUser().getUserName().equals(principal.getName())) {
				return showErrorPage(model, 403);
			}
			String title = movie.getTitle(); // Store title for success message.
			movie.setActors(new ArrayList<>());
			movie.setGenres(new ArrayList<>());
			movieService.save(movie); // By saving movie with empty arrays, Hibernate will delete entries from Join table
			movieService.delete(movie); // Delete movie
			model.addAttribute("title", title);
			return "movie-catalogue/movie-deleted";
		}
		return showErrorPage(model, 404);
		
	}
	
	// Helper method for error handling
	public String showErrorPage(Model model, int code) {
		ErrorContainer error;
		switch(code) {
			case 400:
				error = new ErrorContainer(code, "Bad Request", "There was an issue with your request. Check the ID and try again.", ErrorLevel.WARNING);
				break;
			case 403:
				error = new ErrorContainer(code, "Forbidden", "You are not authorised to view this movie. You can only view movies from your own list.", ErrorLevel.DANGER);
				break;
			case 404:
				error = new ErrorContainer(code, "Movie not found", "The movie you are looking for is not in the database.", ErrorLevel.WARNING);
				break;
			default:
				error = new ErrorContainer(code, "Internal Server Error", "Something went wrong...", ErrorLevel.WARNING);
				break;
		}
		model.addAttribute("error", error);
		return "movie-catalogue/error";
	}

}
