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
		return UserService.getUser(service.convertStringToLong(idString));
	}

	@PostMapping("")
	public User addUser(@RequestBody User newUser) {
		return service.addUser(newUser);
	}
	
	@PostMapping("/{id}")
	public User editUser(@PathVariable("id") String idString, @RequestBody User newUser) {
		return service.editUser(newUser, service.convertStringToLong(idString));
	}
	
	@GetMapping(value = "/search", params = "username")
	public User getUserByUsername(@RequestParam String username) {
		return service.getUserByUsername(username);
	}

	@DeleteMapping("/{id}")
	public User deleteUser(@PathVariable("id") String idString) {
		return service.deleteUser(service.convertStringToLong(idString));
	}

	@GetMapping("/mostactive")
	public User getMostActiveUser() {
		return service.getMostActiveUser();
	}

	@GetMapping("/{id}/jokes")
	public List<Joke> getUserJokes(@PathVariable("id") String idString) {
		return service.getUserJokes(service.convertStringToLong(idString));
	}
	
	@PostMapping("/{usrid}/favorite/{jokeid}")
	public Joke favoriteJoke(@PathVariable("usrid") String userId, @PathVariable("jokeid") String jokeId) {
		return service.favoriteJoke(service.convertStringToLong(userId), service.convertStringToLong(jokeId));
	}
	
	@PostMapping("/{usrid}/unfavorite/{jokeid}")
	public Joke unfavoriteJoke(@PathVariable("usrid") String userId, @PathVariable("jokeid") String jokeId) {
		return service.unfavoriteJoke(service.convertStringToLong(userId), service.convertStringToLong(jokeId));
	}
	
	@PostMapping("/{usrid}/like/{jokeid}")
	public Joke likeJoke(@PathVariable("usrid") String userId, @PathVariable("jokeid") String jokeId){
		return service.likeJoke(service.convertStringToLong(userId), service.convertStringToLong(jokeId));
	}

	@PostMapping("/{usrid}/dislike/{jokeid}")
	public Joke dislikeJoke(@PathVariable("usrid") String userId, @PathVariable("jokeid") String jokeId) {
		return service.dislikeJoke(service.convertStringToLong(userId), service.convertStringToLong(jokeId));
	}
	
	@GetMapping("/{id}/favorites")
	public Set<Joke> getUserFavorites(@PathVariable("id") String idString) {
		return service.getUserFavorites(service.convertStringToLong(idString));
	}
	
	@GetMapping("/{usrid}/isfavorite/{jokeid}")
	public boolean isFavorite(@PathVariable("usrid") String userId, @PathVariable("jokeid") String jokeId) {
		return service.isFavorite(service.convertStringToLong(userId), service.convertStringToLong(jokeId));
	}
	
	@GetMapping("/{id}/favoritedjokecount")
	public int getCountOfFavoritedJokes(@PathVariable("id") String userId) {
		return service.getCountOfFavoritedJokes(service.convertStringToLong(userId));
	}
	
	@GetMapping("/{id}/liked")
	public Set<Joke> getLikedJokes(@PathVariable("id") String userId) {
		return service.getLikedJokes(service.convertStringToLong(userId));
	}
	
	@GetMapping("/{id}/disliked")
	public Set<Joke> getDislikedJokes(@PathVariable("id") String userId) {
		return service.getDislikedJokes(service.convertStringToLong(userId));
	}
}
