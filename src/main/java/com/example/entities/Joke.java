package com.example.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Entity
@Component
public class Joke {
	private String joke;
	private @Id long id;
	private int likes;
	private int dislikes;
	private long userId;
	private LocalDate timestamp = LocalDate.now();
	
	public Joke(String joke, long id, int nmbrLikes, int nmbrDislikes) {
		super();
		this.joke = joke;
		this.id = id;
		this.likes = nmbrLikes;
		this.dislikes = nmbrDislikes;
		this.timestamp = LocalDate.now();
	}
	
	public Joke(String joke, int nmbrLikes, int nmbrDislikes) {
		super();
		this.joke = joke;
		this.likes = nmbrLikes;
		this.dislikes = nmbrDislikes;
		this.timestamp = LocalDate.now();
	}
	
	public Joke(String joke, long id) {
		super();
		this.joke = joke;
		this.id = id;
		this.likes = 0;
		this.dislikes = 0;
		this.timestamp = LocalDate.now();
	}
	
	public Joke(String joke) {
		super();
		this.joke = joke;
		this.likes = 0;
		this.dislikes = 0;
		this.timestamp = LocalDate.now();
	}
	
	public Joke(long userId, String joke) {
		super();
		this.joke = joke;
		this.userId = userId;
	}

	public Joke() {}
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public LocalDate getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDate timestamp) {
		this.timestamp = timestamp;
	}

	public String getJoke() {
		return joke;
	}

	public void setJoke(String joke) {
		this.joke = joke;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int nmbrLikes) {
		this.likes = nmbrLikes;
	}

	public int getDislikes() {
		return dislikes;
	}

	public void setDislikes(int nmbrDislikes) {
		this.dislikes = nmbrDislikes;
	}

}
