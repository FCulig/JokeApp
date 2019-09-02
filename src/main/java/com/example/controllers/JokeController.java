package com.example.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.exceptions.WrongPathVariableTypeArgumentException;
import com.example.services.JokeService;

@RestController
@RequestMapping("jokes")
public class JokeController {

	private JokeService service;

	@Autowired
	public JokeController(JokeService service) {
		this.service = service;
	}

	@PostMapping("")
	public Joke addJoke(@RequestBody Joke newJoke) {
		return service.addJoke(newJoke);
	}

	@GetMapping("")
	public List<Joke> all() {
		return JokeService.getAll();
	}

	@GetMapping("/{id}")
	public Joke getJoke(@PathVariable("id") String jokeIdString) {
		return JokeService.getJoke(service.convertStringToLong(jokeIdString));
	}

	@PostMapping("/{id}")
	public Joke editJoke(@PathVariable("id") String jokeIdString, @RequestBody Joke newJoke) {
		return service.editJoke(service.convertStringToLong(jokeIdString), newJoke);
	}

	@PostMapping("/{id}/like")
	public Joke likeJoke(@PathVariable("id") String jokeIdString) {
		return service.likeJoke(service.convertStringToLong(jokeIdString));
	}

	@PostMapping("/{id}/dislike")
	public Joke dislikeJoke(@PathVariable("id") String jokeIdString) {
		return service.dislikeJoke(service.convertStringToLong(jokeIdString));
	}

	@GetMapping("/random")
	public Joke getRandomJoke() {
		return service.randomJoke();
	}

	@GetMapping("/best")
	public Joke bestJoke() {
		return service.bestJoke();
	}

	@GetMapping(value = "/best", params = "n")
	public List<Joke> bestJokes(@RequestParam String n) {
		return service.topJokes(service.convertStringToInt(n));
	}

	@GetMapping("/worst")
	public Joke worstJoke() {
		return service.worstJoke();
	}

	@GetMapping(value = "/worst", params = "n")
	public List<Joke> worstJokes(@RequestParam String n) {
		return service.worstJokes(service.convertStringToInt(n));
	}

	@DeleteMapping("/{id}")
	public Joke deleteJoke(@PathVariable("id") String jokeIdString) {
		return service.deleteJoke(service.convertStringToLong(jokeIdString));
	}

	@GetMapping("/todayjokes")
	public List<Joke> getTodaysJokes() {
		return service.getTodaysJokes();
	}

	@GetMapping(value = "", params = "date")
	public List<Joke> jokesOfDay(@RequestParam String date) {
		return service.getJokesAtDate(date);
	}

	@GetMapping("/todaybest")
	public Joke getTodaysBest() {
		return service.getTodaysBest();
	}

	@GetMapping("/todayworst")
	public Joke getTodaysWorst() {
		return service.getTodaysWorst();
	}

	@GetMapping("/mostfavorable")
	public Joke getMostFavorableJoke() {
		return service.getMostFavorableJoke();
	}

	@GetMapping("/{id}/whofavorited")
	public Set<User> getUsersWhoFavorited(@PathVariable("id") String jokeIdString) {
		return service.getUsersWhoFavorited(service.convertStringToLong(jokeIdString));
	}

}