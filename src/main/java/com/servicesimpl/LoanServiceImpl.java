package com.servicesimpl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dao.LoanDao;
import com.jwt.JwtFilter;
import com.pojo.Loan;
import com.pojo.User;
import com.services.LoanService;


@Service
public class LoanServiceImpl implements LoanService {

	@Autowired
	private JwtFilter jwtFilter;
	
	@Autowired
	private LoanDao loanDao;
	
	@Override
	public ResponseEntity<String> applyForLoan(Map<String, String> map, String category) {
		try {
			Loan loan = configApplyForLoan(map, category);
			
			if (Objects.isNull(loan)) {
				return new ResponseEntity<String>("Try after sometime",HttpStatus.BAD_REQUEST);
			} else {
				loanDao.save(loan);
				return new ResponseEntity<String>("successfully applied....",HttpStatus.OK);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private Loan configApplyForLoan(Map<String, String> map,String category) {
		try {
			
			
			String firstName = map.get("firstName");
			String lastName = map.get("lastName");
			String fullName = firstName+lastName;
			String mobile = map.get("mobile");
			String email = map.get("email");
			String pan = map.get("pan");
			String aadhar = map.get("aadhar");
			
			String mother = map.get("mother");
			Double income = Double.parseDouble(map.get("annualIncome"));
			
			String employement = map.get("employmentType");
			String relationship = map.get("relationshipStatus");
			String propertyOwnership = map.get("propertyOwnership");
			String gender = map.get("gender");
			Double amount = Double.parseDouble(map.get("amount"));
			
			
			Boolean status = false;
			
			Date dateOfApply = new Date();
			

			User user = jwtFilter.getUserDetails();
			
			Loan loan = new Loan();
			
			loan.setAadhar(aadhar);
			loan.setAmount(amount);
			loan.setCategory(category);
			loan.setDateOfApply(dateOfApply);
			loan.setEmail(email);
			loan.setEmployement(employement);
			loan.setFirstName(firstName);
			loan.setFullName(fullName);
			loan.setGender(gender);
			loan.setIncome(income);
			loan.setLastName(lastName);
			loan.setMobile(mobile);
			loan.setPan(pan);
			loan.setRelationship(relationship);
			loan.setMother(mother);
			loan.setPropertyOwnership(propertyOwnership);
			loan.setUser(user);
			loan.setStatus(status);
			loan.setDateOfApply(new Date());
			
			return loan;

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ResponseEntity<List<Loan>> getAllLoan(String category) {
		try {
			
			if (jwtFilter.isManager()) {
				if (category.equalsIgnoreCase("home")) {
					// home loan
					List<Loan> listLoan = loanDao.getLoanByCategory(category);
					return new ResponseEntity<List<Loan>>(listLoan,HttpStatus.OK);
				} else if (category.equalsIgnoreCase("vehicle")) {
					// vehicle loan
					List<Loan> listLoan = loanDao.getLoanByCategory(category);
					return new ResponseEntity<List<Loan>>(listLoan,HttpStatus.OK);
				} else if (category.equalsIgnoreCase("personal")) {
					// personal loan
					List<Loan> listLoan = loanDao.getLoanByCategory(category);
					return new ResponseEntity<List<Loan>>(listLoan,HttpStatus.OK);
				} else if (category.equalsIgnoreCase("student")) {
					// student loan
					List<Loan> listLoan = loanDao.getLoanByCategory(category);
					return new ResponseEntity<List<Loan>>(listLoan,HttpStatus.OK);
				} else {
					return new ResponseEntity<List<Loan>>(HttpStatus.BAD_REQUEST);
				}
			} else {
				return new ResponseEntity<List<Loan>>(HttpStatus.UNAUTHORIZED);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<Loan>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
