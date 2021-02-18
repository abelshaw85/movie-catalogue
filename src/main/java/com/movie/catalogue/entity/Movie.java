package com.movie.catalogue.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="movies")
public class Movie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="release_date", columnDefinition = "DATE")
	private LocalDate releaseDate;
	
	@Column(name="plot")
	private String plot;
	
	@Column(name="poster")
	private String poster;
	
	@Column(name="imdb_id")
	private String imdbId;
	
	@Column(name="media_type")
	private String mediaType;
	
	@Column(name="recommended")
	private boolean recommended;
	
	@OneToOne(cascade= {
			CascadeType.PERSIST,
			CascadeType.MERGE,
			CascadeType.DETACH,
			CascadeType.REFRESH
	})
	@JoinColumn(name="username")
	private User user;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade= {
			CascadeType.PERSIST,
			CascadeType.MERGE,
			CascadeType.DETACH,
			CascadeType.REFRESH
	})
	@JoinTable(name="movies_genres",
			   joinColumns = @JoinColumn(name="movie_id"),
			   inverseJoinColumns = @JoinColumn(name="genre_name"))
	private List<Genre> genres;
	
	@ManyToMany(fetch=FetchType.EAGER, cascade= {
			CascadeType.MERGE,
			CascadeType.PERSIST,
			CascadeType.DETACH,
			CascadeType.REFRESH
	})
	@JoinTable(name="movies_actors",
			   joinColumns=@JoinColumn(name="movie_id"),
			   inverseJoinColumns=@JoinColumn(name="actor_id"))
	private List<Actor> actors;
	
	public Movie() {
		
	}

	public Movie(int id, String title, LocalDate releaseDate, String plot, String poster, String imdbId, String mediaType,
			boolean recommended, User user, List<Genre> genres, List<Actor> actors) {
		this.id = id;
		this.title = title;
		this.releaseDate = releaseDate;
		this.plot = plot;
		this.poster = poster;
		this.imdbId = imdbId;
		this.mediaType = mediaType;
		this.recommended = recommended;
		this.user = user;
		this.genres = genres;
		this.actors = actors;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate release) {
		this.releaseDate = release;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getImdbId() {
		return imdbId;
	}

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", releaseDate=" + releaseDate + ", plot=" + plot + ", poster=" + poster
				+ ", imdbId=" + imdbId + ", mediaType=" + mediaType + ", recommended=" + recommended + ", user=" + user
				+ ", genres=" + genres + ", actors=" + actors + "]";
	}

}
