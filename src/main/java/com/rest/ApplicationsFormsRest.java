package com.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

	
	@GetMapping("/applications")
	public ResponseEntity<List<ApplicationsForms>> getAllForms();

}
