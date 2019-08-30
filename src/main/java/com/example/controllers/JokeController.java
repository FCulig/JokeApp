package com.example.controllers;

import java.util.List;

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
	public Joke getJoke(@PathVariable("id") long jokeId) {
		return JokeService.getJoke(jokeId);
	}

	@PostMapping("/{id}")
	public Joke editJoke(@PathVariable("id") long jokeId, @RequestBody Joke newJoke) {
		return service.editJoke(jokeId, newJoke);
	}

	@PostMapping("/{id}/like")
	public Joke likeJoke(@PathVariable("id") long jokeId) {
		return service.likeJoke(jokeId);
	}

	@PostMapping("/{id}/dislike")
	public Joke dislikeJoke(@PathVariable("id") long jokeId) {
		return service.dislikeJoke(jokeId);
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
	public List<Joke> bestJokes(@RequestParam int n) {
		return service.topJokes(n);
	}

	@GetMapping("/worst")
	public Joke worstJoke() {
		return service.worstJoke();
	}

	@GetMapping(value = "/worst", params = "n")
	public List<Joke> worstJokes(@RequestParam String n) {
		return service.worstJokes(Integer.parseInt(n));
	}

	@DeleteMapping("/{id}")
	public Joke deleteJoke(@PathVariable("id") long jokeId) {
		return service.deleteJoke(jokeId);
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
	
	@GetMapping("/allfavoritedjokes")
	public List<Joke> getAllFavoritedJokes(){
		return service.getAllFavoritedJokes();
	}
	
	
}