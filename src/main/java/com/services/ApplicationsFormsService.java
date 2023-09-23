package com.services;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.pojo.ApplicationsForms;

public interface ApplicationsFormsService {
	
	
	public ResponseEntity<String> applyForAccount(Map<String, String> map,String category);
	
	public ResponseEntity<List<ApplicationsForms>> getAllForms();

}
