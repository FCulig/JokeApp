package com.example.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entities.Joke;

@Repository
public interface JokeRepository extends JpaRepository<Joke, Long> {
	
	@Modifying
	@Query("update joke set LIKES = LIKES + 1 where ID = ?1")
	public void like(long jokeId);
	
	@Modifying
	@Query("update joke set DISLIKES = DISLIKES + 1 where ID = ?1")
	public void dislike(long jokeId);
}