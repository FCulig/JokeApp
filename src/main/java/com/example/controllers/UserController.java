package com.example.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.Joke;
import com.example.entities.User;
import com.example.services.UserService;

@RestController
@RequestMapping("users")
public class UserController {
	private UserService service;

	@Autowired
	public UserController(UserService service) {
		this.service = service;
	}

	@GetMapping("")
	public List<User> getAllUsers() {
		return UserService.getAllUsers();
	}

	@GetMapping("/{id}")
	public User getUser(@PathVariable("id") long userId) {
		return UserService.getUser(userId);
	}

	@PostMapping("")
	public User addUser(@RequestBody User newUser) {
		return service.addUser(newUser);
	}

	@DeleteMapping("/{id}")
	public User deleteUser(@PathVariable("id") long userId) {
		return service.deleteUser(userId);
	}

	@GetMapping("/mostactive")
	public User getMostActiveUser() {
		return service.getMostActiveUser();
	}

	@GetMapping("/{id}/jokes")
	public List<Joke> getUserJokes(@PathVariable("id") long userId) {
		return service.getUserJokes(userId);
	}
}
