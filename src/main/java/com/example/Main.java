package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.example.entities.Joke;
import com.example.entities.User;
import com.example.repositories.JokeRepository;
import com.example.repositories.UserRepository;

@EnableJpaAuditing
@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
	
	@Bean
	CommandLineRunner initDatabase(JokeRepository jokeRepository, UserRepository userRepository) {
		return args->{
			userRepository.save(new User(1, "First user", "pass"));
			userRepository.save(new User(2, "Second user", "pass"));
			userRepository.save(new User(3, "Third user", "pass"));
			userRepository.save(new User(4, "Fourth user", "pass"));
			userRepository.save(new User(5, "Fifth user", "pass"));
			jokeRepository.save(new Joke(1, "Joke"));
			jokeRepository.save(new Joke(2, "Joke"));
			jokeRepository.save(new Joke(2, "Joke"));
			jokeRepository.save(new Joke(3, "Joke"));
			jokeRepository.save(new Joke(3, "Joke"));
		};
	}
}
