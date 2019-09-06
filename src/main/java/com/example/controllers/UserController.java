package com.example.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.Joke;
import com.example.entities.User;
import com.example.services.UserService;

@RestController
@RequestMapping("users")
@CrossOrigin(origins = "http://localhost:4200")
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
	public User getUser(@PathVariable("id") String idString) {
		return UserService.getUser(service.convertLongToString(idString));
	}

	@PostMapping("")
	public User addUser(@RequestBody User newUser) {
		return service.addUser(newUser);
	}
	
	@PostMapping("/{id}")
	public User editUser(@PathVariable("id") String idString, @RequestBody User newUser) {
		return service.editUser(newUser, service.convertLongToString(idString));
	}
	
	@GetMapping(value = "", params = "username")
	public List<User> getUserByUsername(@RequestParam String username) {
		return service.getUserByUsername(username);
	}

	@DeleteMapping("/{id}")
	public User deleteUser(@PathVariable("id") String idString) {
		return service.deleteUser(service.convertLongToString(idString));
	}

	@GetMapping("/mostactive")
	public User getMostActiveUser() {
		return service.getMostActiveUser();
	}

	@GetMapping("/{id}/jokes")
	public List<Joke> getUserJokes(@PathVariable("id") String idString) {
		return service.getUserJokes(service.convertLongToString(idString));
	}
	
	@PostMapping("/{usrid}/favorite/{jokeid}")
	public Joke favoriteJoke(@PathVariable("usrid") String userId, @PathVariable("jokeid") String jokeId) {
		return service.favoriteJoke(service.convertLongToString(userId), service.convertLongToString(jokeId));
	}
	
	@PostMapping("/{usrid}/unfavorite/{jokeid}")
	public Joke unfavoriteJoke(@PathVariable("usrid") String userId, @PathVariable("jokeid") String jokeId) {
		return service.unfavoriteJoke(service.convertLongToString(userId), service.convertLongToString(jokeId));
	}
	
	@GetMapping("/{id}/favorites")
	public Set<Joke> getUserFavorites(@PathVariable("id") String idString) {
		return service.getUserFavorites(service.convertLongToString(idString));
	}
	
	@GetMapping("/{usrid}/isfavorite/{jokeid}")
	public boolean isFavorite(@PathVariable("usrid") String userId, @PathVariable("jokeid") String jokeId) {
		return service.isFavorite(service.convertLongToString(userId), service.convertLongToString(jokeId));
	}
}
