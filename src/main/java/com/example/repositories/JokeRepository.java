package com.example.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entities.Joke;

@Repository
public interface JokeRepository extends JpaRepository<Joke, Long> {
	
	public List<Joke> findByTimestamp(LocalDate dateString);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update Joke j set j.likes = j.likes + 1 where j.id = ?1")
	public void updateLikesFor(long jokeId);

	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update joke set joke.DISLIKES = joke.DISLIKES + 1 where joke.ID = ?1", nativeQuery = true)
	public void updateDislikesFor(long jokeId);

	@Modifying//(clearAutomatically = true)
	@Transactional
	@Query(value = "update joke set joke.JOKE = ?1 where joke.ID = ?2", nativeQuery = true)
	public void editJoke(String newJoke, long jokeId);
}