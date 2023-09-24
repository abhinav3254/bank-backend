package com.services;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.pojo.Loan;

public interface LoanService {
	
	public ResponseEntity<String> applyForLoan(Map<String, String>map,String category);
	
	public ResponseEntity<List<Loan>> getAllLoan(String category);
}
