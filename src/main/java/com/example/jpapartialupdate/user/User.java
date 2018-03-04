package com.example.jpapartialupdate.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created on March, 2018
 *
 * @author ersan
 */
@Data
@NoArgsConstructor
@Entity(name = "APP_USER")
@DynamicUpdate
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	@Column(nullable = false, updatable = false)
	private String password;

	private String email;

	private String phone;

	public User(String username, String password, String email, String phone) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "User{" + "id=" + id + ", username='" + username + '\'' + ", password='" + password + '\'' + ", email='" + email + '\'' + ", phone='"
				+ phone + '\'' + '}';
	}
}
