package com.example.jpapartialupdate.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.beans.FeatureDescriptor;
import java.util.stream.Stream;

/**
 * Created on March, 2018
 *
 * @author ersan
 */
@Slf4j
@Service
public class CriteriaUpdateRepository {

	private EntityManager entityManager;

	public CriteriaUpdateRepository(EntityManagerFactory entityManagerFactory) {
		this.entityManager = entityManagerFactory.createEntityManager();
	}

	public User updatePartial(User user, Long id) {
		entityManager.getTransaction().begin();

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaUpdate<User> updateCriteria = criteriaBuilder.createCriteriaUpdate(User.class);
		Root<User> root = updateCriteria.from(User.class);
		updateCriteria = buildCriteriaIgnoringNullValues(updateCriteria, user);
		updateCriteria.where(criteriaBuilder.equal(root.get("id"), id));
		entityManager.createQuery(updateCriteria).executeUpdate();

		User persistedUser = entityManager.find(User.class, id);
		log.info(persistedUser.toString());
		entityManager.refresh(persistedUser);
		entityManager.getTransaction().commit();

		return persistedUser;
	}

	private CriteriaUpdate<User> buildCriteriaIgnoringNullValues(CriteriaUpdate<User> criteriaUpdate, User user) {
		final BeanWrapper wrappedSource = new BeanWrapperImpl(user);
		Stream.of(wrappedSource.getPropertyDescriptors())
				.map(FeatureDescriptor::getName)
				.filter(propertyName ->  (!propertyName.equals("class") && wrappedSource.getPropertyValue(propertyName) != null))
				.forEach(s -> criteriaUpdate.set(s, wrappedSource.getPropertyValue(s)));
		return criteriaUpdate;
	}

}
