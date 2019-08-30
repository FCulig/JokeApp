package com.example.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.example.services.JokeService;
import com.example.services.UserService;

@Entity
@Component
@Table(name = "favorites")
public class Favorite {

	private @Id long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(optional = false)
	@JoinColumn(name = "joke_id")
	private Joke joke;

	public Favorite(long id, long userId, long jokeId) {
		super();
		this.id = id;
		this.user = UserService.getUser(userId);
		this.joke = JokeService.getJoke(jokeId);
	}

	public Favorite() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Joke getJoke() {
		return joke;
	}

	public void setJoke(Joke joke) {
		this.joke = joke;
	}

	@Override
	public String toString() {
		return "Favorite [id=" + id + ", user=" + user + ", joke=" + joke + "]";
	}

}
