package com.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pojo.Loan;

@RequestMapping("/loan")
public interface LoanRest {
	
	
	@PostMapping("/apply-for-home-loan")
	public ResponseEntity<String> applyForHomeLoan(@RequestBody(required = true)Map<String, String>map);
	
	@PostMapping("/apply-for-personal-loan")
	public ResponseEntity<String> applyForPersonalLoan(@RequestBody(required = true)Map<String, String>map);
	
	@PostMapping("/apply-for-vehicle-loan")
	public ResponseEntity<String> applyForVehicleLoan(@RequestBody(required = true)Map<String, String>map);
	
	@PostMapping("/apply-for-student-loan")
	public ResponseEntity<String> applyForStudentLoan(@RequestBody(required = true)Map<String, String>map);
	
	
	@GetMapping("/get-all-home-loan-applications")
	public ResponseEntity<List<Loan>> getAllHomeLoanRequest();
	
	@GetMapping("/get-all-personal-loan-applications")
	public ResponseEntity<List<Loan>> getAllPersonalLoanRequest();

	@GetMapping("/get-all-vehicle-loan-applications")
	public ResponseEntity<List<Loan>> getAllVehicleLoanRequest();
	
	@GetMapping("/get-all-student-loan-applications")
	public ResponseEntity<List<Loan>> getAllStudentLoanRequest();
	

}
