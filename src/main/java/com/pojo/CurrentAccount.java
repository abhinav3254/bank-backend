package com.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
public class CurrentAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Double draft;
	
	@OneToOne
	private User user;
	
	@OneToOne
	private ApplicationsForms applicationsForms;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date accountCreattionDate;
	
	private String accountNumber;
	
	
}
