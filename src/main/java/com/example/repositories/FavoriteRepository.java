package com.example.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entities.Favorite;
import com.example.entities.Joke;
import com.example.entities.User;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

	@Modifying
	@Transactional
	@Query("delete from Favorite f where f.user = ?1 and f.joke= ?2")
	public void unfavorite(User user, Joke joke);
	
	@Modifying
	@Transactional
	@Query("select f.joke from Favorite f where f.user= ?1")
	public List<Joke> findFavs(User user);
	
	@Transactional
	@Query("select f from Favorite f where f.user = ?1 and f.joke = ?2")
	public Optional<Favorite> getFavorite(User user,Joke joke);
	
	@Transactional
	@Query("select distinct f.joke from Favorite f")
	public List<Joke> getAllFavoritedJokes();
}
