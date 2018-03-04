package com.example.jpapartialupdate.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

/**
 * Created on March, 2018
 *
 * @author ersan
 */
@Repository
public class FetchAndUpdateUserRepository {

	@Autowired
	private EntityManager entityManager;

	@Transactional
	public User findById(Long id) {
		return entityManager.find(User.class, id);
	}

	@Transactional
	public User save(User user) {
		entityManager.persist(user);
		return user;
	}

	@Transactional
	public User updateContactInformation(User user, Long id) {
		User persistedUser = entityManager.find(User.class, id);
		persistedUser.setPhone(user.getPhone());
		persistedUser.setEmail(user.getEmail());
		return persistedUser;
	}

}
