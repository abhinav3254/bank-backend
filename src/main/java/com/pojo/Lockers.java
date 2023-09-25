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

@Entity
@Data
public class Lockers {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	/**
	 * Locker will have three size
	 * 1. Large
	 * 2. Medium
	 * 3. Small
	 * Use this from drop down options
	 */
	private String size;
	
	// when bank person approves then change to true
	private boolean status;
	
	private boolean available;
	
	private double money;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date allocatedDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date applyDate;
	
	private String nomineeName;
	
	private boolean allocated;
	
	/**
	 * tenure must be in years
	 */
	private String tenure;
	
	@ManyToOne
	private User user;
	
	
}
