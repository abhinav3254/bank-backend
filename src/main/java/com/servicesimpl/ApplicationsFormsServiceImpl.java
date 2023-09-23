package com.servicesimpl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dao.ApplicationsFormsDao;
import com.dao.UserDao;
import com.jwt.JwtFilter;
import com.jwt.JwtUtils;
import com.pojo.ApplicationsForms;
import com.pojo.User;
import com.services.ApplicationsFormsService;


@Service
public class ApplicationsFormsServiceImpl implements ApplicationsFormsService {

	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Autowired
	private ApplicationsFormsDao applicationsFormsDao;
	
	@Override
	public ResponseEntity<String> applyForAccount(Map<String, String> map, String category) {
		try {
			
			ApplicationsForms applicationsForms = ConfigApplicationsForms(map, category);
			if (Objects.isNull(applicationsForms)) {
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			} else {
				applicationsFormsDao.save(applicationsForms);
				
				return new ResponseEntity<String>("Applied Suucessfully",HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public ApplicationsForms ConfigApplicationsForms(Map<String, String>map,String category) {
		try {
			
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String userToken = authentication.getName();
			String username = jwtUtils.extractUsername(userToken);

			// Retrieve the user's profile information based on the extracted username
			User user = userDao.getUserByUsername(username);

			String mobile = map.get("mobile");
			
			String email = map.get("email");
			
			String pan = map.get("pan");
			
			String aadhar = map.get("aadhar");
			
			String mother = map.get("mother");
			
			Double annualIncome = Double.parseDouble(map.get("annualIncome"));
			
			String profession = map.get("profession");
			
			String sourceOfIncome = map.get("sourceOfIncome");
			
			Date dateOfApply = new Date();
			
			ApplicationsForms applicationsForms = new ApplicationsForms();
			
			
			applicationsForms.setAadhar(aadhar);
			applicationsForms.setAnnualIncome(annualIncome);
			applicationsForms.setCategory(category);
			applicationsForms.setDateOfApply(dateOfApply);
			applicationsForms.setEmail(email);
			applicationsForms.setMobile(mobile);
			applicationsForms.setMother(mother);
			applicationsForms.setPan(pan);
			applicationsForms.setProfession(profession);
			applicationsForms.setSourceOfIncome(sourceOfIncome);
			applicationsForms.setUser(user);
			
			
			return applicationsForms;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ResponseEntity<List<ApplicationsForms>> getAllForms() {
		try {
			
			
			if (jwtFilter.isManager()) {
				return new ResponseEntity<List<ApplicationsForms>>(HttpStatus.UNAUTHORIZED);
			} else {
				List<ApplicationsForms> listApplicationsForms = applicationsFormsDao.findAll();
				
				return new ResponseEntity<List<ApplicationsForms>>(listApplicationsForms,HttpStatus.OK);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<ApplicationsForms>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
