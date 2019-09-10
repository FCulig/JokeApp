package com.example.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entities.Joke;
import com.example.entities.User;
import com.example.exceptions.BadUserBodyException;
import com.example.exceptions.FavoriteAlreadyExistsException;
import com.example.exceptions.FavoriteNotFoundException;
import com.example.exceptions.UserAlreadyReacted;
import com.example.exceptions.UserNotFoundException;
import com.example.exceptions.WrongPathVariableTypeArgumentException;
import com.example.repositories.JokeRepository;
import com.example.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private static UserRepository userRepository;

	@Autowired
	private static JokeRepository jokeRepository;

	@Autowired
	public UserService(JokeRepository jokeRepository, UserRepository userRepository) {
		super();
		UserService.userRepository = userRepository;
		UserService.jokeRepository = jokeRepository;
	}

	public User addUser(User newUser) {
		checkUsername(newUser);
		userRepository.save(newUser);
		return newUser;
	}

	public User editUser(User newUser, long userId) {
		isUserPresent(userId);
		checkUsername(newUser);
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

	/*public List<User> getUserByUsername(String username) {
		List<User> usr = userRepository.findByUsername(username);

		if (!usr.isEmpty()) {
			return usr;
		} else {
			throw new UserNotFoundException(username);
		}
	}*/

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

		User usr = getUser(userId);
		Joke jk = JokeService.getJoke(jokeId);

		if (usr.getFavoritedJokes().contains(jk)) {
			throw new FavoriteAlreadyExistsException(getUser(userId));
		} else {
			jk.getUsers().add(usr);
			usr.getFavoritedJokes().add(JokeService.getJoke(jokeId));
			userRepository.save(usr);
			jokeRepository.save(jk);

			return JokeService.getJoke(jokeId);
		}
	}

	public Joke unfavoriteJoke(long userId, long jokeId) {
		JokeService.isJokePresent(jokeId);
		isUserPresent(userId);

		User usr = getUser(userId);
		Joke jk = JokeService.getJoke(jokeId);

		if (usr.getFavoritedJokes().contains(jk)) {
			jk.getUsers().remove(usr);
			usr.getFavoritedJokes().remove(JokeService.getJoke(jokeId));
			userRepository.save(usr);
			jokeRepository.save(jk);

			return JokeService.getJoke(jokeId);
		} else {
			throw new FavoriteNotFoundException("User did not favorite this joke.");
		}
	}

	public Joke likeJoke(long userId, long jokeId) {
		JokeService.isJokePresent(jokeId);
		isUserPresent(userId);

		User usr = getUser(userId);
		Joke jk = JokeService.getJoke(jokeId);

		if (usr.getLikedJokes().contains(jk)) {
			throw new UserAlreadyReacted("User already liked this joke");
		} else {
			if (usr.getDislikedJokes().contains(jk)) {
				usr.getDislikedJokes().remove(jk);
				jk.getUsersDisliked().remove(usr);
			}

			jk.getUsersLiked().add(usr);
			usr.getLikedJokes().add(JokeService.getJoke(jokeId));

			userRepository.save(usr);
			jokeRepository.save(jk);

			return JokeService.getJoke(jokeId);
		}
	}

	public Joke dislikeJoke(long userId, long jokeId) {
		JokeService.isJokePresent(jokeId);
		isUserPresent(userId);

		User usr = getUser(userId);
		Joke jk = JokeService.getJoke(jokeId);

		if (usr.getDislikedJokes().contains(jk)) {
			throw new UserAlreadyReacted("User already disliked this joke");
		} else {
			if (usr.getLikedJokes().contains(jk)) {
				usr.getLikedJokes().remove(jk);
				jk.getUsersLiked().remove(usr);
			}

			jk.getUsersDisliked().add(usr);
			usr.getDislikedJokes().add(JokeService.getJoke(jokeId));

			userRepository.save(usr);
			jokeRepository.save(jk);

			return JokeService.getJoke(jokeId);
		}
	}

	public Set<Joke> getUserFavorites(long userId) {
		isUserPresent(userId);
		return getUser(userId).getFavoritedJokes();
	}

	public long convertStringToLong(String idString) {
		try {
			long id = Long.parseLong(idString);
			return id;
		} catch (NumberFormatException e) {
			throw new WrongPathVariableTypeArgumentException("Path variable argument must be int/long");
		}
	}

	public void checkUsername(User usr) {
		if (usr.getUsername() == null) {
			throw new BadUserBodyException("Username isnt set");
		}
	}

	public boolean isFavorite(long userId, long jokeId) {
		isUserPresent(userId);
		JokeService.isJokePresent(jokeId);

		for (Joke jk : getUserFavorites(userId)) {
			if (jk.getId() == jokeId) {
				return true;
			}
		}

		return false;
	}

	public int getCountOfFavoritedJokes(long userId) {
		isUserPresent(userId);
		List<Joke> usersJokes = getUserJokes(userId);
		int cnt = 0;

		for (Joke jk : usersJokes) {
			try {
				cnt = cnt + JokeService.getUsersWhoFavorited(jk.getId()).size();
			} catch (Exception e) {
			}
		}

		return cnt;
	}

	public Set<Joke> getLikedJokes(long userId) {
		isUserPresent(userId);
		return getUser(userId).getLikedJokes();
	}

	public Set<Joke> getDislikedJokes(long userId) {
		isUserPresent(userId);
		return getUser(userId).getDislikedJokes();
	}
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}