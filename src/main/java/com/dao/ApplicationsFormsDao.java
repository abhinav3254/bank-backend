package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pojo.ApplicationsForms;

public interface ApplicationsFormsDao extends JpaRepository<ApplicationsForms, Long> {
	
	
	@Query(nativeQuery = true,value = "select * from applications_forms where category like '%savings-account%' and user_id is not null")
	public List<ApplicationsForms> getAllSavingAccountRequest();
	
	
	@Query(nativeQuery = true,value = "select * from applications_forms where category like '%current-account%' and user_id is not null")
	public List<ApplicationsForms> getAllCurrentAccountRequest();
	
	
}
