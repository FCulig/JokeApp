package com.example.entities;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.example.services.UserService;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Component
@Table(name = "joke")
public class Joke {
	private LocalDate timestamp = LocalDate.now();
	private String joke;
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "joke_id")
	private @Id long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id")
	private User author;
	
	@ManyToMany(mappedBy = "favoritedJokes")
	@JsonIgnore
	private Set<User> users;
	
	@ManyToMany(mappedBy = "likedJokes")
	@JsonIgnore
	private Set<User> usersLiked;
	
	@ManyToMany(mappedBy = "dislikedJokes")
	@JsonIgnore
	private Set<User> usersDisliked;

	public Joke(String joke, long id) {
		super();
		this.joke = joke;
		this.id = id;
		this.timestamp = LocalDate.now();
	}

	public Joke(String joke) {
		super();
		this.joke = joke;
		this.timestamp = LocalDate.now();
	}

	public Joke(String joke, User author) {
		super();
		this.joke = joke;
		this.author = UserService.getUser(author.getId());
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

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public void addUser(User us) {
		this.users.add(us);
	}

	public void removeUser(User us) {
		this.users.remove(us);
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public Set<User> getUsersLiked() {
		return usersLiked;
	}

	public void setUsersLiked(Set<User> usersLiked) {
		this.usersLiked = usersLiked;
	}

	public Set<User> getUsersDisliked() {
		return usersDisliked;
	}

	public void setUsersDisliked(Set<User> usersDisliked) {
		this.usersDisliked = usersDisliked;
	}

	@Override
	public String toString() {
		return "Joke [joke=" + joke + ", id=" + id + ", author=" + author + 
				", timestamp=" + timestamp + ", users="
				+ users + ", usersLiked=" + usersLiked + ", usersDisliked=" + 
				usersDisliked + "]";
	}
}
