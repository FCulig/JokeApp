package com.example.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.example.services.UserService;

@Entity
@Component
@Table(name = "joke")
public class Joke {
	private String joke;
	private @Id long id;
	private int likes;
	private int dislikes;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User author;
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

	public Joke(User author, String joke) {
		super();
		this.joke = joke;
		this.author = author;
		this.timestamp = LocalDate.now();
	}

	public Joke(long userId, String joke) {
		super();
		this.joke = joke;
		this.timestamp = LocalDate.now();
		this.author = UserService.getUser(userId);
	}

	public Joke() {
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

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "Joke [joke=" + joke + ", id=" + id + ", likes=" + likes + ", dislikes=" + dislikes + ", author="
				+ author + ", timestamp=" + timestamp + "]";
	}

}
