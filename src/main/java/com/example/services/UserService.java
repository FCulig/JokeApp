package com.example.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entities.Joke;
import com.example.entities.User;
import com.example.exceptions.UserNotFoundException;
import com.example.repositories.UserRepository;

@Service
public class UserService {
	private static AtomicLong nextId = new AtomicLong();
	
	@Autowired
	private static UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	
	public User addUser(User newUser) {
		newUser.setId(nextId.getAndIncrement());
		userRepository.save(newUser);
		return newUser;
	}

	/*public static User addUser(User newUser) {
		long id;
		boolean taken;
		do {
			taken = false;
			id = nextId.getAndIncrement();
			for(User usr : users) {
				if(usr.getId() == id) {
					taken = true;
					break;
				}
			}
			
		}while(taken);
		newUser.setId(id);
		users.add(newUser);
		return newUser;
	}

	public static User addUserIfDoesntExist(User newUser) {
		boolean exists = false;
		for(User usr : users) {
			if(usr.getId() == newUser.getId()) {
				exists = true;
			}
		}
		
		if(!exists) {
			users.add(newUser);
		}
		
		return newUser;
	}*/

	public static List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public static User getUser(long userId) {
		for (User us : getAllUsers()) {
			if (us.getId() == userId) {
				return us;
			}
		}

		throw new UserNotFoundException(userId);
	}

	public User getMostActiveUser() {
		List<Joke> jokes = JokeService.getAll();
		Map<Long, Integer> counter = new HashMap<>();

		for (User usr : getAllUsers()) {
			counter.put(usr.getId(), 0);
		}

		for (Joke jk : jokes) {
			counter.put(jk.getUserId(), counter.get(jk.getUserId()) + 1);
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
		for(User usr : getAllUsers()) {
			if(usr.getId() == userId) {
				userRepository.delete(usr);
				return usr;
			}
		}
		
		throw new UserNotFoundException(userId);
	}
	
	public List<Joke> getUserJokes(long userId){
		User usr = getUser(userId);
		List<Joke> usersJokes = new ArrayList<>();
		for(Joke jk : JokeService.getAll()) {
			if(jk.getUserId() == usr.getId()) {
				usersJokes.add(jk);
			}
		}
		
		return usersJokes;
	}
}
