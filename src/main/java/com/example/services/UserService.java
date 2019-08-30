package com.example.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entities.Favorite;
import com.example.entities.Joke;
import com.example.entities.User;
import com.example.exceptions.FavoriteAlreadyExistsException;
import com.example.exceptions.FavoriteNotFoundException;
import com.example.exceptions.UserNotFoundException;
import com.example.repositories.FavoriteRepository;
import com.example.repositories.UserRepository;

@Service
public class UserService {
	private static AtomicLong nextId = new AtomicLong();
	private static AtomicLong nextIdFav = new AtomicLong();

	@Autowired
	private static UserRepository userRepository;

	@Autowired
	private static FavoriteRepository favoriteRepository;

	@Autowired
	public UserService(UserRepository userRepository, FavoriteRepository favoriteRepository) {
		super();
		UserService.userRepository = userRepository;
		UserService.favoriteRepository = favoriteRepository;
	}

	public User addUser(User newUser) {
		newUser.setId(nextId.getAndIncrement());
		userRepository.save(newUser);
		return newUser;
	}
	
	public User editUser(User newUser, long userId) {
		isUserPresent(userId);
		userRepository.editUser(userId, newUser.getUsername());
		return getUser(userId);
	}

	public static List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public static User getUser(long userId) {
		Optional<User> usr = userRepository.findById(userId);

		if (usr.isPresent()) {
			return usr.get();
		} else {
			throw new UserNotFoundException(userId);
		}
	}
	
	public List<User> getUserByUsername(String username) {
		List<User> usr = userRepository.findByUsername(username);
		
		if(!usr.isEmpty()) {
			return usr;
		}else {
			throw new UserNotFoundException(username);
		}
	}

	public User getMostActiveUser() {
		List<Joke> jokes = JokeService.getAll();
		Map<Long, Integer> counter = new HashMap<>();

		for (User usr : getAllUsers()) {
			counter.put(usr.getId(), 0);
		}

		for (Joke jk : jokes) {
			counter.put(jk.getAuthor().getId(), counter.get(jk.getAuthor().getId()) + 1);
		}

		Map.Entry<Long, Integer> first = counter.entrySet().iterator().next();
		long maxId = first.getKey();
		int maxCnt = first.getValue();
		for (Map.Entry<Long, Integer> item : counter.entrySet()) {
			if (item.getValue() > maxCnt) {
				maxCnt = item.getValue();
				maxId = item.getKey();
			}
		}

		return getUser(maxId);
	}

	public User deleteUser(long userId) {
		User deletedUser = getUser(userId);
		userRepository.delete(deletedUser);
		return deletedUser;
	}

	public List<Joke> getUserJokes(long userId) {
		isUserPresent(userId);
		return JokeService.getJokesFromUser(getUser(userId));
	}

	public void isUserPresent(long userId) {
		Optional<User> usr = userRepository.findById(userId);
		if (!usr.isPresent()) {
			throw new UserNotFoundException(userId);
		}
	}

	public Joke favoriteJoke(long userId, long jokeId) {
		JokeService.isJokePresent(jokeId);
		isUserPresent(userId);
		isFavoritePresent(userId, jokeId);
		favoriteRepository.save(new Favorite(nextIdFav.incrementAndGet(), userId, jokeId));
		return JokeService.getJoke(jokeId);
	}

	public Joke unfavoriteJoke(long userId, long jokeId) {
		JokeService.isJokePresent(jokeId);
		isUserPresent(userId);
		isNotFavoritePresent(userId, jokeId);
		favoriteRepository.unfavorite(getUser(userId), JokeService.getJoke(jokeId));
		return JokeService.getJoke(jokeId);
	}

	public List<Joke> getUserFavorites(long userId) {
		isUserPresent(userId);
		return favoriteRepository.findFavs(getUser(userId));
	}

	public void isFavoritePresent(long userId, long jokeId) {
		Optional<Favorite> fv = favoriteRepository.getFavorite(getUser(userId), JokeService.getJoke(jokeId));

		if (fv.isPresent()) {
			throw new FavoriteAlreadyExistsException(getUser(userId));
		}
	}

	public void isNotFavoritePresent(long userId, long jokeId) {
		Optional<Favorite> fv = favoriteRepository.getFavorite(getUser(userId), JokeService.getJoke(jokeId));

		if (!fv.isPresent()) {
			throw new FavoriteNotFoundException("This joke is not favorited");
		}
	}
}