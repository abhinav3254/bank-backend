package com.servicesimpl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dao.LoanDao;
import com.dao.UserDao;
import com.jwt.JwtUtils;
import com.pojo.Loan;
import com.pojo.User;
import com.services.LoanService;


@Service
public class LoanServiceImpl implements LoanService {
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private LoanDao loanDao;

	@Override
	public ResponseEntity<String> applyLoan(Map<String, String> map) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String userToken = authentication.getName();
			String username = jwtUtils.extractUsername(userToken);
			User user = userDao.getUserByUsername(username);
			
			Double amount = Double.parseDouble(map.get("amount"));
			
			if (amount<0) {
				return new ResponseEntity<String>("AMOUNT CAN'T BE NEGATIVE",HttpStatus.BAD_REQUEST);
			} else {
				
				Loan loan = new Loan();
				
				loan.setAmount(amount);
				loan.setUser(user);
				loan.setStatus(false);
				loan.setApplyDate(new Date());
				
				loanDao.save(loan);
				
				return new ResponseEntity<String>("APPLICATION FOR LOAN SUBMITTED",HttpStatus.OK);
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
