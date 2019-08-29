package com.example.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entities.Joke;
import com.example.exceptions.JokeNotFoundException;
import com.example.repositories.JokeRepository;
import com.example.utils.JokeLikeDislikeComparator;

@Service
public class JokeService {
	private AtomicLong nextId = new AtomicLong();

	@Autowired
	private static JokeRepository jokeRepository;

	@Autowired
	public JokeService(JokeRepository jokeRepository) {
		super();
		JokeService.jokeRepository = jokeRepository;
	}

	public Joke addJoke(Joke newJoke) {
		newJoke.setId(nextId.getAndIncrement());
		jokeRepository.save(newJoke);
		return newJoke;
	}

	public static List<Joke> getAll() {
		return jokeRepository.findAll();
	}

	public Joke getJoke(long jokeId) {
		Optional<Joke> jk = jokeRepository.findById(jokeId);

		if (jk.isPresent()) {
			return jk.get();
		} else {
			throw new JokeNotFoundException(jokeId);
		}
	}

	public Joke editJoke(long jokeId, Joke newJoke) {
		/*jokeRepository.editJoke(newJoke.getJoke(), getJoke(jokeId).getId());
		return getJoke(jokeId);*/
		Joke jk = getJoke(jokeId); 
		jk.setJoke(newJoke.getJoke());;
		jokeRepository.save(jk);
		return jk;
	}

	public Joke likeJoke(long jokeId) {
		jokeRepository.updateLikesFor(getJoke(jokeId).getId());
		return getJoke(jokeId);
//		Joke jk = getJoke(jokeId); 
//		jk.setLikes(jk.getLikes()+1);
//		jokeRepository.save(jk);
//		return jk;
	}

	public Joke dislikeJoke(long jokeId) {
		/*jokeRepository.updateDislikesFor(getJoke(jokeId).getId());
		return getJoke(jokeId);*/
		Joke jk = getJoke(jokeId); 
		jk.setDislikes(jk.getDislikes()+1);
		jokeRepository.save(jk);
		return jk;
	}

	public Joke randomJoke() {
		return getAll().get(new Random().nextInt((int) jokeRepository.count()));
	}

	public Joke bestJoke() {
		List<Joke> jokes = getAll();
		Collections.sort(jokes, new JokeLikeDislikeComparator());
		return jokes.get(0);
	}

	public Joke worstJoke() {
		List<Joke> jokes = getAll();
		Collections.sort(jokes, new JokeLikeDislikeComparator());
		Collections.reverse(jokes);
		return jokes.get(0);
	}

	public List<Joke> topJokes(int n) {
		List<Joke> jokes = getAll();
		Collections.sort(jokes, new JokeLikeDislikeComparator());

		List<Joke> bestJokes = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			bestJokes.add(jokes.get(i));
		}

		return bestJokes;
	}

	public List<Joke> worstJokes(int n) {
		List<Joke> jokes = getAll();
		Collections.sort(jokes, new JokeLikeDislikeComparator());
		Collections.reverse(jokes);

		List<Joke> worstJokes = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			worstJokes.add(jokes.get(i));
		}

		return worstJokes;
	}

	public Joke deleteJoke(long jokeId) {
		Joke deletedJoke = getJoke(jokeId);
		jokeRepository.delete(deletedJoke);
		return deletedJoke;
	}

	public List<Joke> getTodaysJokes() {
		List<Joke> todays = jokeRepository.findByTimestamp(LocalDate.now());
		if(todays.isEmpty()) {
			throw new JokeNotFoundException("There are no jokes from today!");
		}else {
			return todays;
		}
	}

	public Joke getTodaysBest() {
		List<Joke> todays = getTodaysJokes();
		Collections.sort(todays, new JokeLikeDislikeComparator());
		return todays.get(0);
	}

	public Joke getTodaysWorst() {
		List<Joke> todays = getTodaysJokes();
		Collections.sort(todays, new JokeLikeDislikeComparator());
		Collections.reverse(todays);
		return todays.get(0);
	}

	public List<Joke> getJokesAtDate(String dateString) {
		List<Joke> jokesAtDay = jokeRepository.findByTimestamp(LocalDate.parse(dateString));
		if(!jokesAtDay.isEmpty()) {
			return jokesAtDay;
		}else {
			throw new JokeNotFoundException("Could not find jokes from day "+dateString);
		}
	}

}
