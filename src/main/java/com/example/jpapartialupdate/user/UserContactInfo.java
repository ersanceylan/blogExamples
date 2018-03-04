package com.example.jpapartialupdate.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created on March, 2018
 *
 * @author ersan
 */
@Data
@NoArgsConstructor
@Entity(name = "APP_USER")
public class UserContactInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email;

	private String phone;

	@Override
	public String toString() {
		return "UserContactInfo{" + "id=" + id + ", email='" + email + '\'' + ", phone='" + phone + '\'' + '}';
	}
}
