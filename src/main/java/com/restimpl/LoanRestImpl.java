package com.restimpl;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.rest.LoanRest;
import com.services.LoanService;


@RestController
public class LoanRestImpl implements LoanRest {
	
	@Autowired
	private LoanService loanService;

	@Override
	public ResponseEntity<String> applyLoan(Map<String, String> map) {
		try {
			return loanService.applyLoan(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
