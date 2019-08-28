package com.example.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
	private @Id long id;
	private String username;

	public User(long id, String username) {
		super();
		this.id = id;
		this.username = username;
	}

	public User(long id) {
		super();
		this.id = id;
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

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + "]";
	}
}
