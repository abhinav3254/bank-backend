package com.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
public class User {

	// The @Id annotation indicates that 'id' is the primary key for this entity.
	// The @GeneratedValue annotation specifies the strategy for generating unique
	// IDs.
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "username")
	private String username;
	private String name;
	private String email;
	private String password;
	private String phone;
	private String address;
	private String status;
	private String role;
	private String gender;

	// The @Temporal annotation specifies that 'registerDate' is temporal field, meaning they store date and time information.
	@Temporal(TemporalType.TIMESTAMP)
	private Date registerDate;

	// The @Temporal annotation specifies that 'lastLogin' is temporal field, meaning they store date and time information.
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogin;
}