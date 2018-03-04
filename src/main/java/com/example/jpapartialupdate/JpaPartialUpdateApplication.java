package com.example.jpapartialupdate;

import com.example.jpapartialupdate.user.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
	private ShortVersionRepository shortVersionRepository;

	@Autowired
	private CriteriaUpdateRepository criteriaUpdateRepository;

	@Autowired
	private JPQLRepository jpqlRepository;

	@GetMapping("/users/{id}")
	public User getUser(@PathVariable("id") Long id) {
		return userRepository.findById(id);
	}

	@PostMapping("/users")
	public User createUser(User user) {
		return userRepository.save(user);
	}

//	FETCH AND UPDATE
	@PostMapping("/users/{id}/update-contact-info/")
	public User fetchAndUpdate(@PathVariable Long id, User user) {
		log.info(user.toString());
		User persistedUser = userRepository.updateContactInformation(user, id);
		log.info(persistedUser.toString());
		return persistedUser;
	}

// SHORT VERSION OF ENTITY
	@PostMapping("/users/{id}/short-version-update/")
	public UserContactInfo shortVersionUpdate(@PathVariable Long id, UserContactInfo userContactInfo) {
		return shortVersionRepository.updateContactInformation(userContactInfo, id);
	}

//	CRITERIA UPDATE
	@PostMapping("/users/{id}/criteria-update/")
	public User criteriaUpdate(@PathVariable Long id, User user) {
		return criteriaUpdateRepository.updateContactInformation(user, id);
	}

	@PostMapping("/users/{id}/jpql-update/")
	public User jpqlUpdate(@PathVariable Long id, User user) {
		return jpqlRepository.updateContactInformation(user, id);
	}



	@Bean
	public CommandLineRunner initialDataForDevelopment(FetchAndUpdateUserRepository userRepository) {

		return (args) -> {
			User user = new User("johndoe", "securePassword1", "john@jd.com", "05001112233");
			userRepository.save(user);
			log.info(user.toString());
		};
	}

}
