package com.example.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Component
@Table(name = "users")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private @Id long id;
	private String username;
	private String password;
	
	@ManyToMany
	@JoinTable(name = "favorite_jokes", joinColumns = @JoinColumn(name = "joke_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	@JsonIgnore
	private Set<Joke> favoritedJokes;

	@ManyToMany
	@JoinTable(name = "liked_jokes", 
	joinColumns = @JoinColumn(name = "joke_id"), 
	inverseJoinColumns = @JoinColumn(name = "user_id"))
	@JsonIgnore
	private Set<Joke> likedJokes;

	@ManyToMany
	@JoinTable(name = "disliked_jokes", joinColumns = @JoinColumn(name = "joke_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	@JsonIgnore
	private Set<Joke> dislikedJokes;

	public User(long id, String username) {
		super();
		this.id = id;
		this.username = username;
	}
	
	public User(long id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public User(long id) {
		super();
		this.id = id;
	}

	public User(String username) {
		super();
		this.username = username;
	}

	public User() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void addFavorite(Joke jk) {
		this.favoritedJokes.add(jk);
	}

	public void removeFavorite(Joke jk) {
		this.favoritedJokes.remove(jk);
	}

	public Set<Joke> getFavoritedJokes() {
		return favoritedJokes;
	}

	public void setFavoritedJokes(Set<Joke> favoritedJokes) {
		this.favoritedJokes = favoritedJokes;
	}

	public Set<Joke> getLikedJokes() {
		return likedJokes;
	}

	public void setLikedJokes(Set<Joke> likedJokes) {
		this.likedJokes = likedJokes;
	}

	public Set<Joke> getDislikedJokes() {
		return dislikedJokes;
	}

	public void setDislikedJokes(Set<Joke> dislikedJokes) {
		this.dislikedJokes = dislikedJokes;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", favoritedJokes=" + favoritedJokes + ", likedJokes="
				+ likedJokes + ", dislikedJokes=" + dislikedJokes + "]";
	}
}
