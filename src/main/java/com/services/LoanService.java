package com.services;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface LoanService {
	
	public ResponseEntity<String> applyLoan(Map<String, String>map);

}
