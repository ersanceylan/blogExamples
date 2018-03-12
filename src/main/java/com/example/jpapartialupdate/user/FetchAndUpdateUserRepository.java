package com.example.jpapartialupdate.user;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.beans.FeatureDescriptor;
import java.util.stream.Stream;

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
	public User updatePartial(User user, Long id) {
		User persistedUser = entityManager.find(User.class, id);

		String[] ignoredProperties = getNullPropertyNames(user);
		BeanUtils.copyProperties(user, persistedUser, ignoredProperties);

		entityManager.merge(persistedUser);

		return persistedUser;
	}

//	https://stackoverflow.com/a/35579690/3388447
	private String[] getNullPropertyNames(User user) {
		final BeanWrapper wrappedSource = new BeanWrapperImpl(user);
		return Stream.of(wrappedSource.getPropertyDescriptors())
				.map(FeatureDescriptor::getName)
				.filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
				.toArray(String[]::new);
	}

}
