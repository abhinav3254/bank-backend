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
public class ApplicationsForms {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * Make seprate methods for credit card apply
	 * or insurance apply etc and in each method specify
	 * category
	 */
	private String category;
	
	private String mobile;
	
	private String email;
	
	private String pan;
	
	private String aadhar;
	
	private String mother;
	
	private Double annualIncome;
	
	private String profession;
	
	private String sourceOfIncome;
	
	
	@ManyToOne
	private User user;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfApply;
	
}
