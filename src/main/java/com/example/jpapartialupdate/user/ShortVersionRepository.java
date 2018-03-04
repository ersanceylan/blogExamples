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
public class ShortVersionRepository {

	@Autowired
	private EntityManager entityManager;

	@Transactional
	public UserContactInfo updateContactInformation(UserContactInfo userContactInfo, Long id) {
		UserContactInfo persistedUser = entityManager.find(UserContactInfo.class, id);
		entityManager.merge(userContactInfo);
		return persistedUser;
	}

}
