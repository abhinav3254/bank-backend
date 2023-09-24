package com.restimpl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.pojo.Loan;
import com.rest.LoanRest;
import com.services.LoanService;


@RestController
public class LoanRestImpl implements LoanRest {
	
	@Autowired
	private LoanService loanService;

	@Override
	public ResponseEntity<String> applyForHomeLoan(Map<String, String> map) {
		try {
			return loanService.applyForLoan(map,"home");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> applyForPersonalLoan(Map<String, String> map) {
		try {
			return loanService.applyForLoan(map,"personal");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> applyForVehicleLoan(Map<String, String> map) {
		try {
			return loanService.applyForLoan(map,"vehicle");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> applyForStudentLoan(Map<String, String> map) {
		try {
			return loanService.applyForLoan(map,"student");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Loan>> getAllHomeLoanRequest() {
		try {
			return loanService.getAllLoan("home");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<Loan>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Loan>> getAllPersonalLoanRequest() {
		try {
			return loanService.getAllLoan("personal");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<Loan>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Loan>> getAllVehicleLoanRequest() {
		try {
			return loanService.getAllLoan("vehicle");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<Loan>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Loan>> getAllStudentLoanRequest() {
		try {
			return loanService.getAllLoan("student");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<Loan>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	

}
