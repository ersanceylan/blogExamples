package com.example.jpapartialupdate;

import com.example.jpapartialupdate.user.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@SpringBootApplication
public class JpaPartialUpdateApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaPartialUpdateApplication.class, args);
	}

	@Autowired
	private FetchAndUpdateUserRepository userRepository;

	@Autowired
	private CriteriaUpdateRepository criteriaUpdateRepository;

	@GetMapping("/users/{id}")
	public User getUser(@PathVariable("id") Long id) {
		return userRepository.findById(id);
	}

	@PostMapping("/users")
	public User createUser(User user) {
		return userRepository.save(user);
	}

//	FETCH AND UPDATE
	@PatchMapping(value = "/users/{id}/update-contact-info/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public User fetchAndUpdate(@PathVariable("id") Long id, @RequestBody User user) {
		return userRepository.updatePartial(user, id);
	}

//	CRITERIA UPDATE
	@PatchMapping(value = "/users/{id}/criteria-update/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public User criteriaUpdate(@RequestBody User user, @PathVariable Long id) {
		return criteriaUpdateRepository.updatePartial(user, id);
	}


	@Bean
	public CommandLineRunner initialDataForDevelopment(FetchAndUpdateUserRepository userRepository) {

		return (args) -> {
			User user = new User("john", "doe", "john@jd.com", "05001112233");
			userRepository.save(user);
			log.info(user.toString());
		};
	}

}
