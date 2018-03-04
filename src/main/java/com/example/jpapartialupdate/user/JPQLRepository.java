package com.example.jpapartialupdate.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created on March, 2018
 *
 * @author ersan
 */
@Repository
public class JPQLRepository {

	@Autowired
	private EntityManager entityManager;

	@Transactional
	public User updateContactInformation(User user, Long id) {
		Query query = entityManager
				.createQuery("UPDATE APP_USER AS u SET u.password = '1', u.email = :email, u.phone = :phone WHERE u.id = :id");
		query.setParameter("email", user.getEmail())
				.setParameter("phone", user.getPhone())
				.setParameter("id", id);
		query.executeUpdate();

		// fetch user
		TypedQuery<User> query1 = entityManager.createQuery("SELECT u FROM APP_USER u WHERE u.id = :id", User.class)
				.setParameter("id", id);
		return query1.getSingleResult();
	}

}
