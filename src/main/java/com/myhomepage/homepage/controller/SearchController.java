package com.myhomepage.homepage.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myhomepage.homepage.dto.MovieDTO;
import com.myhomepage.homepage.dto.MovieResultDTO;
import com.myhomepage.homepage.entity.Movie;
import com.myhomepage.homepage.entity.MovieResults;
import com.myhomepage.homepage.entity.User;
import com.myhomepage.homepage.model.ErrorContainer;
import com.myhomepage.homepage.model.ErrorLevel;
import com.myhomepage.homepage.model.ImdbSearch;
import com.myhomepage.homepage.model.TitleSearch;
import com.myhomepage.homepage.service.MovieService;
import com.myhomepage.homepage.service.OmdbService;
import com.myhomepage.homepage.service.UserService;


@Controller
@RequestMapping("/movie-catalogue/search")
public class SearchController {
	
	@Autowired
	private OmdbService omdbService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MovieService movieService;

	
	// This will trim whitespace from all fields, which prevents empty fields that contain only spaces from being submitted. Also prevents superflous white spaces e.g. "     title"
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		//true = trim to null if all whitespace
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	// OMDB Search methods
	@GetMapping("/omdb")
	public String showOmdbSearchPage(Model model, @RequestParam Optional<Integer> editId) {
		int id = editId.isEmpty() ? 0 : editId.orElse(0);
		TitleSearch titleSearch = new TitleSearch();
		titleSearch.setId(id);
		ImdbSearch imdbSearch = new ImdbSearch();
		imdbSearch.setId(id);
		model.addAttribute("titleSearch", titleSearch);
		model.addAttribute("imdbSearch", imdbSearch);
		return "movie-catalogue/movie-search";
	}
	
	@PostMapping("/omdb/title")
	public String processSearchResultsByTitle(Model model, @Valid @ModelAttribute("titleSearch") TitleSearch titleSearch, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("imdbSearch", new ImdbSearch());
			return "movie-catalogue/movie-search";
		}
		StringBuilder redirectBuilder = new StringBuilder();
		redirectBuilder.append("redirect:/movie-catalogue/search/omdb/title/");
		redirectBuilder.append(titleSearch.getTerm());
		if (titleSearch.getId() > 0) {
			redirectBuilder.append("?editId=");
			redirectBuilder.append(titleSearch.getId());
		}
		return redirectBuilder.toString();
	}
	
	@GetMapping("/omdb/title/{title}")
	public String showOmdbSearchResultsByTitle(Model model, @PathVariable String title, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> editId, HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		if (editId.orElse(0) > 0) {
			url += "?editId=" + editId.get();
		}
		System.out.println();
		int pageNumber = page.orElse(1);
		//Format search to be url-friendly
		MovieResults movieResults = omdbService.findByTitle(title.replaceAll("\\s", "%20"), pageNumber);
		if (!movieResults.getResponse()) {
			ErrorContainer error = new ErrorContainer(500, "OMDb Service Error", "<h4>The OMDb API has failed to return a valid response:</h4>" + movieResults.getError(), ErrorLevel.WARNING);
			model.addAttribute("error", error);
			return "movie-catalogue/error";
		}
		List<MovieResultDTO> movieResultDtos = movieResults.getMovieResultDtos().stream().limit(10).collect(Collectors.toList());
		
		// Omdb pages have 10 results, so divide numbers of results by 10.
		// If value is larger than the current page number, then there is a next page.
		double numberOfPages = (double)movieResults.getTotalResults() / 10;
		boolean hasNextPage = pageNumber < numberOfPages && numberOfPages > 1;

		model.addAttribute("movieList", movieResultDtos);
		model.addAttribute("editId", editId.orElse(0));
		model.addAttribute("page", pageNumber);
		model.addAttribute("hasNextPage", hasNextPage);
		model.addAttribute("currentUrl", url);
		
		return "movie-catalogue/movie-search-list";
	}
	
	@PostMapping("/omdb/imdbid")
	public String showOmdbSearchResultsByImdbId(Model model, @Valid @ModelAttribute("imdbSearch") ImdbSearch imdbSearch, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("titleSearch", new TitleSearch());
			return "movie-catalogue/movie-search";
		}
		//Format search to be url-friendly
		MovieDTO movieDto = omdbService.findByImdbId(imdbSearch.getTerm().replaceAll("\\s", "%20"));
		List<MovieDTO> movieDtos = new ArrayList<>();
		if (movieDto != null) {
			movieDtos.add(movieDto);
		}
		
		model.addAttribute("movieList", movieDtos);
		model.addAttribute("editId", imdbSearch.getId());
		return "movie-catalogue/movie-search-list";
	}
	
	// User Search Methods
	@PostMapping("/user/title")
	public String postSearchResults(
			Principal principal,
			Model model, 
			@Valid @ModelAttribute("search") TitleSearch search, 
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {
			System.out.println("ERROR in showUserSearchResultsByTitle!");
			// RedirectAttributes allows for binding before a redirect.
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.search", bindingResult);
			redirectAttributes.addFlashAttribute("search", search);
			return "redirect:/movie-catalogue";
		}
		return "redirect:title/" + search.getTerm().replaceAll("\\s", "%20");
	}
	
	@GetMapping("/user/title/{movieTitle}")
	public String showUserSearchResultsByTitle(
			Principal principal,
			@RequestParam Optional<Integer> page,
			@PathVariable String movieTitle,
			Model model) {
		String username = principal.getName();		
		User user = userService.findByUserName(username);
		
		ModelMapper modelMapper = new ModelMapper();
		
		int pageNumber = page.isEmpty() ? 0 : page.orElse(0);
		Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by("title")); // Page number, how many elements

		Slice<Movie> sliceMovieDtos = movieService.findByUserAndTitle(user, movieTitle, pageable);
		List<MovieDTO> movieDtos = sliceMovieDtos
				  .stream()
				  .map(movie -> modelMapper.map(movie, MovieDTO.class))
				  .collect(Collectors.toList());
		
		model.addAttribute("movieList", movieDtos);
		model.addAttribute("search", new TitleSearch());
		model.addAttribute("page", pageNumber);
		model.addAttribute("hasNext", sliceMovieDtos.hasNext());
		model.addAttribute("message", "Search results for: " + movieTitle);
		return "movie-catalogue/movie-list";
	}
}
