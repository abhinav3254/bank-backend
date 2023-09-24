package com.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
public class Loan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String firstName;
	private String lastName;
	private String fullName;
	private String mobile;
	private String email;
	private String pan;
	private String aadhar;
	
	private String mother;
	private Double income;
	
	private String employement;
	private String relationship;
	private String propertyOwnership;
	private String gender;
	private Double amount;
	
	
	private Boolean status;
	private String category;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfApply;
	
	@ManyToOne
	private User user;
	
}
