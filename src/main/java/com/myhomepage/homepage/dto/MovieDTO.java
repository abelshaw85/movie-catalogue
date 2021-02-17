package com.myhomepage.homepage.dto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.myhomepage.homepage.entity.Actor;
import com.myhomepage.homepage.entity.Genre;
import com.myhomepage.homepage.entity.User;
import com.myhomepage.homepage.validator.ImdbId;
import com.myhomepage.homepage.validator.ReleaseDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDTO {

	private int id;
	
	@NotNull(message = "Title field is required.")
	@Size(min = 1, message = "Title field is required.")
	private String title;
	
	@NotNull(message = "Date is required")
	@ReleaseDate(value = "1885-11-01", message = "First ever movie was released 1st November 1895.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd MMM yyyy")
	private LocalDate releaseDate;
	
	@NotNull(message = "Plot summary is required")
	@Size(min = 10, message = "Plot summary is too short (must be at least 10 characters).")
	private String plot;
	
	private String poster;
	
	@ImdbId(message = "Invalid IMDb format. Must begin 'tt' followed by 5-6 numbers.")
	private String imdbId;
	
	@NotNull(message = "Media Type is required.")
	private String mediaType;
	
	@NotNull(message = "Recommended is required.")
	private boolean recommended;
	private User user;
	private List<Genre> genres;
	private List<Actor> actors;
	private String actorsString;
	//OMDb returns single Genre string, map to String.
	private String genre;
	//omdb search returns year rather than full date
	private String year;
	private boolean response;

	public MovieDTO() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@JsonProperty("Title")
	public String getTitle() {
		return title;
	}

	@JsonProperty("Title")
	public void setTitle(String title) {
		this.title = title;
	}

	@JsonProperty("Released")
	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	@JsonProperty("Released")
	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	@JsonProperty("Plot")
	public String getPlot() {
		return plot;
	}

	@JsonProperty("Plot")
	public void setPlot(String plot) {
		this.plot = plot;
	}

	@JsonProperty("Poster")
	public String getPoster() {
		return poster;
	}

	@JsonProperty("Poster")
	public void setPoster(String poster) {
		this.poster = poster;
	}

	@JsonProperty("imdbID")
	public String getImdbId() {
		return imdbId;
	}

	@JsonProperty("imdbID")
	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public boolean isRecommended() {
		return recommended;
	}

	public void setRecommended(boolean recommended) {
		this.recommended = recommended;
	}

	public List<Genre> getGenres() {
		return genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}

	public List<Actor> getActors() {
		return actors;
	}

	public void setActors(List<Actor> actors) {
		this.actors = actors;
		this.actorsString = this.getActorsAsList();
	}

	public void addActor(Actor actor) {
		if (this.actors == null) {
			this.actors = new ArrayList<>();
		}
		if (this.getActorByName(actor.getName()) == null) {
			this.actors.add(actor);
		}
	}

	public void addGenre(Genre genre) {
		if (this.genres == null) {
			this.genres = new ArrayList<>();
		}
		if (this.getGenreByName(genre.getName()) == null) {
			this.genres.add(genre);
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	//actorsString used for displaying actors on form.
	@JsonProperty("Actors")
	public String getActorsString() {
		return actorsString;
	}

	@JsonProperty("Actors")
	public void setActorsString(String actorsString) {
		this.actorsString = actorsString;
	}
	
	private String getActorsAsList() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.actors.size(); i++) {
			sb.append(this.actors.get(i).getName());
			if (i < this.actors.size() - 1) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}
	
	public Actor getActorByName(String name) {
		return this.actors == null ? null
				: this.actors.stream().filter(actor -> actor.getName().equals(name)).findFirst().orElse(null);
	}

	public Genre getGenreByName(String name) {
		return this.genres == null ? null
				: this.genres.stream().filter(genre -> genre.getName().equals(name)).findFirst().orElse(null);
	}

	public boolean hasGenre(String name) {
		boolean result = this.getGenreByName(name) != null;
		return result;
	}

	@JsonProperty("Genre")
	public String getGenre() {
		return genre;
	}

	@JsonProperty("Genre")
	public void setGenre(String genre) {
		this.genre = genre;
	}

	@JsonProperty("Year")
	public String getYear() {
		return year;
	}

	@JsonProperty("Year")
	public void setYear(String year) {
		this.year = year;
	}

	@JsonProperty("Response")
	public boolean getResponse() {
		return response;
	}

	@JsonProperty("Response")
	public void setResponse(boolean response) {
		this.response = response;
	}
	
	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", releaseDate=" + releaseDate + ", plot=" + plot + ", poster=" + poster
				+ ", imdbId=" + imdbId + ", mediaType=" + mediaType + ", recommended=" + recommended + ", user="
				+ user.getUserName() + ", genres=" + genres + ", actors=" + actors + "]";
	}
	
}
