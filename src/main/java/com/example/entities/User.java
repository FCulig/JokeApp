package com.example.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name = "users")
public class User {
	@Column(name = "user_id")
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

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + "]";
	}
}
