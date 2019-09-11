package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.entities.Joke;
import com.example.entities.User;
import com.example.repositories.JokeRepository;
import com.example.repositories.UserRepository;

@SpringBootApplication
@EnableJpaRepositories
public class Main{

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
	
	@Bean
	CommandLineRunner initDatabase(JokeRepository jokeRepository, UserRepository userRepository) {
		return args->{
			userRepository.save(new User(1, "user1", encoder1().encode("pass")));
			userRepository.save(new User(2, "Second user", encoder1().encode("pass")));
			userRepository.save(new User(3, "Third user", encoder1().encode("pass")));
			userRepository.save(new User(4, "Fourth user", encoder1().encode("pass")));
			userRepository.save(new User(5, "Fifth user", encoder1().encode("pass")));
			jokeRepository.save(new Joke(1, "Joke"));
			jokeRepository.save(new Joke(2, "Joke"));
			jokeRepository.save(new Joke(2, "Joke"));
			jokeRepository.save(new Joke(3, "Joke"));
			jokeRepository.save(new Joke(3, "Joke"));
		};
	}
	
	@Bean
	public PasswordEncoder  encoder1() {
	    return new BCryptPasswordEncoder();
	}
}
