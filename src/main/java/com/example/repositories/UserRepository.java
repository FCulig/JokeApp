package com.example.repositories;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.entities.User;

@Repository
@CrossOrigin(origins = "http://localhost:4200")
public interface UserRepository extends JpaRepository<User, Long> {
	
	public List<User> findByUsername(String username);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update User u set u.username = ?2 where u.id = ?1")
	public void editUser(long userId, String newUsername);
}
