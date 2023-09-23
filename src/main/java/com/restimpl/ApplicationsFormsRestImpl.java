package com.restimpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.pojo.ApplicationsForms;
import com.rest.ApplicationsFormsRest;
import com.services.ApplicationsFormsService;

@RestController
public class ApplicationsFormsRestImpl implements ApplicationsFormsRest {
	
	@Autowired
	private ApplicationsFormsService applicationsFormsService;

	@Override
	public ResponseEntity<String> applyForSavingsAccount(Map<String, String> map) {
		try {
			return applicationsFormsService.applyForAccount(map,"savings-account");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> applyForCurrentAccount(Map<String, String> map) {
		try {
			return applicationsFormsService.applyForAccount(map,"current-account");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<ApplicationsForms>> getAllForms() {
		try {
			return applicationsFormsService.getAllForms();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<ApplicationsForms>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
