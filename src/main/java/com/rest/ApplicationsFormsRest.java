package com.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pojo.ApplicationsForms;

@RequestMapping("/applications-form")
public interface ApplicationsFormsRest {
	
	@PostMapping("/apply/savings-account")
	public ResponseEntity<String> applyForSavingsAccount(@RequestBody(required = true)Map<String, String>map);
	
	@PostMapping("/apply/current-account")
	public ResponseEntity<String> applyForCurrentAccount(@RequestBody(required = true)Map<String, String>map);

	@GetMapping("/applications/saving-accounts")
	public ResponseEntity<List<ApplicationsForms>> getAllFormsOfSavingAccounts();
	
	@GetMapping("/applications/current-accounts")
	public ResponseEntity<List<ApplicationsForms>> getAllFormsOfCurrentAccounts();

	// if application approves then we need to change account table also
	
	@GetMapping("/approve-savings-accounts/{id}")
	public ResponseEntity<String> approveSavingsAccount(@PathVariable String id);
	
	
	@GetMapping("/approve-current-accounts/{id}")
	public ResponseEntity<String> approveCurrentAccount(@PathVariable String id);
	
	
	
}
