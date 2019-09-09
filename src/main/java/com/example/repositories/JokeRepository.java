package com.example.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.entities.Joke;
import com.example.entities.User;

@Repository
@CrossOrigin(origins = "http://localhost:4200")
public interface JokeRepository extends JpaRepository<Joke, Long> {
	
	public List<Joke> findByTimestamp(LocalDate dateString);

	public List<Joke> findByAuthor(User user);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update Joke j set j.joke = ?1 where j.id = ?2")
	public void editJoke(String newJoke, long jokeId);
}