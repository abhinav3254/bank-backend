package com.pojo;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Double amount;
	
//	@Temporal(TemporalType.TIMESTAMP)
//	private Date withdrawDate;
	
	@OneToOne
	private User user;
	
}
