package com.example.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entities.Joke;
import com.example.entities.User;

@Repository
public interface JokeRepository extends JpaRepository<Joke, Long> {
	
	public List<Joke> findByTimestamp(LocalDate dateString);

	public List<Joke> findByAuthor(User user);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update Joke j set j.likes = j.likes + 1 where j.id = ?1")
	public void updateLikesFor(long jokeId);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update Joke j set j.dislikes = j.dislikes + 1 where j.id = ?1")
	public void updateDislikesFor(long jokeId);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update Joke j set j.joke = ?1 where j.id = ?2")
	public void editJoke(String newJoke, long jokeId);
}