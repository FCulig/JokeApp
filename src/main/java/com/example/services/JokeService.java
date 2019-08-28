package com.example.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entities.Joke;
import com.example.exceptions.JokeNotFoundException;
import com.example.repositories.JokeRepository;
import com.example.utils.JokeComparator;

@Service
public class JokeService{
	
	private AtomicLong nextId = new AtomicLong();
	
	//dodan static -> provjeri
	@Autowired
	private static JokeRepository jokeRepository;
	
	@Autowired
	public JokeService(JokeRepository jokeRepository) {
		super();
		this.jokeRepository = jokeRepository;
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
		for (Joke jk : getAll()) {
			if (jk.getId() == jokeId) {
				return jk;
			}
		}

		throw new JokeNotFoundException(jokeId);
	}

	public Joke editJoke(long jokeId, Joke newJoke) {
		for (Joke jk : getAll()) {
			if (jk.getId() == jokeId) {
				newJoke.setId(jokeId);
				newJoke.setLikes(jk.getLikes());
				newJoke.setDislikes(jk.getDislikes());
				newJoke.setTimestamp(LocalDate.now());
				jokeRepository.delete(jk);
				jokeRepository.save(newJoke);
				return newJoke;
			}
		}

		throw new JokeNotFoundException(jokeId);
	}

	public Joke likeJoke(long jokeId) {
		for (Joke jk : getAll()) {
			if (jk.getId() == jokeId) {
				jokeRepository.like(jokeId);
				return jokeRepository.getOne(jokeId);
			}
		}

		throw new JokeNotFoundException(jokeId);
	}

	public Joke dislikeJoke(long jokeId) {
		for (Joke jk : getAll()) {
			if (jk.getId() == jokeId) {
				jokeRepository.dislike(jokeId);
				return jokeRepository.getOne(jokeId);
			}
		}

		throw new JokeNotFoundException(jokeId);
	}

	public Joke randomJoke() {
		return getAll().get(new Random().nextInt((int)jokeRepository.count()));
	}

	public Joke bestJoke() {
		List<Joke> jokes = getAll();
		Collections.sort(jokes, new JokeComparator());
		return jokes.get(0);
	}

	public Joke worstJoke() {
		List<Joke> jokes = getAll();
		Collections.sort(jokes, new JokeComparator());
		return jokes.get(jokes.size() - 1);
	}

	public List<Joke> topJokes(int n) {
		List<Joke> jokes = getAll();
		Collections.sort(jokes, new JokeComparator());

		List<Joke> bestJokes = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			bestJokes.add(jokes.get(i));
		}

		return bestJokes;
	}

	public List<Joke> worstJokes(int n) {
		List<Joke> jokes = getAll();
		Collections.sort(jokes, new JokeComparator());

		List<Joke> worstJokes = new ArrayList<>();
		for (int i = jokes.size() - 1; i > jokes.size() - n - 1; i--) {
			worstJokes.add(jokes.get(i));
		}

		return worstJokes;
	}

	public Joke deleteJoke(long jokeId) {
		for (Joke jk : getAll()) {
			if (jk.getId() == jokeId) {
				Joke deletedJoke = jk;
				jokeRepository.delete(jk);
				return deletedJoke;
			}
		}

		throw new JokeNotFoundException(jokeId);
	}

	public List<Joke> getTodaysJokes(){
		List<Joke> todays = new ArrayList<>();
		LocalDateTime now = LocalDateTime.now();
		for(Joke jk : getAll()) {
			if(jk.getTimestamp().getYear()==now.getYear() &&
					jk.getTimestamp().getMonth()==now.getMonth() &&
					jk.getTimestamp().getDayOfMonth()==now.getDayOfMonth()) {
				todays.add(jk);
			}
		}
		
		return todays;
	}
	
	public Joke getTodaysBest() {
		List<Joke> todays = getTodaysJokes();
		Collections.sort(todays, new JokeComparator());
		return todays.get(0);
	}
	
	public Joke getTodaysWorst() {
		List<Joke> todays = getTodaysJokes();
		Collections.sort(todays, new JokeComparator());
		return todays.get(todays.size()-1);
	}
	
	public List<Joke> getJokesAtDate(String dateString){
		List<Joke> jokesAtDay = new ArrayList<>();
		LocalDate date = LocalDate.parse(dateString);
		for(Joke jk : getAll()) {
			if(jk.getTimestamp().getYear()==date.getYear() &&
					jk.getTimestamp().getMonth()==date.getMonth() &&
					jk.getTimestamp().getDayOfMonth()==date.getDayOfMonth()) {
				jokesAtDay.add(jk);
			}
		}
		return jokesAtDay;
	}
	
}
