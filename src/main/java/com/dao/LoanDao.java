package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pojo.Loan;

public interface LoanDao extends JpaRepository<Loan, Integer> {
	
	@Query(nativeQuery = true,value = "select * from loan where category like %:category%")
	public List<Loan> getLoanByCategory(String category);
	
}
