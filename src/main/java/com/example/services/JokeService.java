package com.example.services;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entities.Joke;
import com.example.entities.User;
import com.example.exceptions.BadJokeBodyException;
import com.example.exceptions.FavoriteNotFoundException;
import com.example.exceptions.JokeNotFoundException;
import com.example.exceptions.NotEnoughJokesException;
import com.example.exceptions.WrongDateFormatException;
import com.example.exceptions.WrongPathVariableTypeArgumentException;
import com.example.repositories.JokeRepository;
import com.example.utils.JokeLikeDislikeComparator;

@Service
public class JokeService {

	@Autowired
	private static JokeRepository jokeRepository;

	@Autowired
	public JokeService(JokeRepository jokeRepository) {
		super();
		JokeService.jokeRepository = jokeRepository;
	}

	public Joke addJoke(Joke newJoke) {
		checkJokeBody(newJoke);
		newJoke.setAuthor(UserService.getUser(newJoke.getAuthor().getId()));
		jokeRepository.save(newJoke);
		return newJoke;
	}

	public static List<Joke> getAll() {
		return jokeRepository.findAll();
	}

	public static Joke getJoke(long jokeId) {
		Optional<Joke> jk = jokeRepository.findById(jokeId);

		if (jk.isPresent()) {
			return jk.get();
		} else {
			throw new JokeNotFoundException(jokeId);
		}
	}

	public static void isJokePresent(long jokeId) {
		Optional<Joke> jk = jokeRepository.findById(jokeId);

		if (!jk.isPresent()) {
			throw new JokeNotFoundException(jokeId);
		}
	}

	public Joke editJoke(long jokeId, Joke newJoke) {
		checkOnlyJokeBody(newJoke);
		jokeRepository.editJoke(newJoke.getJoke(), getJoke(jokeId).getId());
		return getJoke(jokeId);
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
		checkIsEnoughJokes(n);
		List<Joke> jokes = getAll();
		Collections.sort(jokes, new JokeLikeDislikeComparator());

		List<Joke> bestJokes = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			bestJokes.add(jokes.get(i));
		}

		return bestJokes;
	}

	public List<Joke> worstJokes(int n) {
		checkIsEnoughJokes(n);
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
		if (todays.isEmpty()) {
			throw new JokeNotFoundException("There are no jokes from today!");
		} else {
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
		try {
			List<Joke> jokesAtDay = jokeRepository.findByTimestamp(LocalDate.parse(dateString));
			if (!jokesAtDay.isEmpty()) {
				return jokesAtDay;
			} else {
				throw new JokeNotFoundException("Could not find jokes from day " + dateString);
			}
		} catch (DateTimeParseException e) {
			throw new WrongDateFormatException("Wrong format of date entered");
		}
	}

	public static List<Joke> getJokesFromUser(User usr) {
		return jokeRepository.findByAuthor(usr);
	}

	public Joke getMostFavorableJoke() {
		List<Joke> allJokes = jokeRepository.findAll();

		long maxId = 0, maxCnt = 0;

		for (Joke jk : allJokes) {
			if (jk.getUsers().size() > maxCnt) {
				maxCnt = jk.getUsers().size();
				maxId = jk.getId();
			}
		}

		return getJoke(maxId);
	}

	public static Set<User> getUsersWhoFavorited(long jokeId) {
		Set<User> users = getJoke(jokeId).getUsers();

		if (users.isEmpty()) {
			throw new FavoriteNotFoundException("Nobody favorited this joke.");
		} else {
			return users;
		}

	}

	public void checkOnlyJokeBody(Joke jk) {
		if (jk.getJoke() == null) {
			throw new BadJokeBodyException("There is no joke sent");
		}
	}

	public void checkJokeBody(Joke jk) {
		checkOnlyJokeBody(jk);
		if (jk.getAuthor() == null) {
			throw new BadJokeBodyException("There is no author to this joke");
		}
	}

	public long convertStringToLong(String idString) {
		try {
			long id = Long.parseLong(idString);
			return id;
		} catch (NumberFormatException e) {
			throw new WrongPathVariableTypeArgumentException("Path variable argument must be int/long");
		}
	}

	public int convertStringToInt(String intString) {
		try {
			int n = Integer.parseInt(intString);
			return n;
		} catch (NumberFormatException e) {
			throw new WrongPathVariableTypeArgumentException("Path variable must be int");
		}
	}

	public void checkIsEnoughJokes(int n) {
		try {
			if (n > jokeRepository.findAll().size()) {
				throw new NotEnoughJokesException("You requested more jokes than there is");
			}
		} catch (IndexOutOfBoundsException e) {
			throw new NotEnoughJokesException("You requested more jokes than there is");
		}
	}

	public static Set<User> getUsersWhoLiked(long jokeId){
		/*if(getJoke(jokeId).getUsersLiked().size()>0) {
			return getJoke(jokeId).getUsersLiked();
		}else {
			throw new NoReactionsFoundException("Nobody liked this joke");
		}*/
		return getJoke(jokeId).getUsersLiked();
	}
	
	public static Set<User> getUsersWhoDisliked(long jokeId){
		/*if(getJoke(jokeId).getUsersDisliked().size()>0) {
			return getJoke(jokeId).getUsersDisliked();
		}else {
			throw new NoReactionsFoundException("Nobody disliked this joke");
		}*/
		return getJoke(jokeId).getUsersDisliked();
	}
}
