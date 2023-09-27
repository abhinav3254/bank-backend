package com.servicesimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dao.ApplicationsFormsDao;
import com.dao.CurrentAccountDao;
import com.dao.SavingsAccountDao;
import com.dao.UserDao;
import com.jwt.JwtFilter;
import com.jwt.JwtUtils;
import com.pojo.ApplicationsForms;
import com.pojo.CurrentAccount;
import com.pojo.SavingsAccount;
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
	private SavingsAccountDao savingsAccountDao;

	@Autowired
	private CurrentAccountDao currentAccountDao;

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

				return new ResponseEntity<String>("Applied Suucessfully", HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public ApplicationsForms ConfigApplicationsForms(Map<String, String> map, String category) {
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

			applicationsForms.setStatus(false);

			return applicationsForms;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ResponseEntity<List<ApplicationsForms>> getAllForms(String category) {
		try {

			if (jwtFilter.isManager()) {
				List<ApplicationsForms> listApplicationsForms = new ArrayList<ApplicationsForms>();
				if (category.contains("current")) {
					listApplicationsForms = applicationsFormsDao.getAllCurrentAccountRequest();

					return new ResponseEntity<List<ApplicationsForms>>(listApplicationsForms, HttpStatus.OK);
				} else if (category.contains("savings")) {
					listApplicationsForms = applicationsFormsDao.getAllSavingAccountRequest();

					return new ResponseEntity<List<ApplicationsForms>>(listApplicationsForms, HttpStatus.OK);
				}

			} else {
				return new ResponseEntity<List<ApplicationsForms>>(HttpStatus.UNAUTHORIZED);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<ApplicationsForms>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> approveSavingsAccount(String id) {
		try {

			if (jwtFilter.isManager()) {
				Optional<ApplicationsForms> optionalApplicationForms = applicationsFormsDao.findById(Long.parseLong(id));

				if (optionalApplicationForms.isPresent()) {

					ApplicationsForms applicationsForms = optionalApplicationForms.get();

					if (applicationsForms.getCategory().charAt(0) == 's') {

						if (!Objects.isNull(applicationsForms.getUser())) {
							SavingsAccount savingsAccount = savingsAccountDao
									.getUserSavingsAccount(applicationsForms.getUser().getId());

							if (Objects.isNull(savingsAccount)) {
								savingsAccount = new SavingsAccount();

								savingsAccount.setAccountCreatedDate(new Date());
								savingsAccount.setAmount(0d);
								savingsAccount.setApplicationsForms(applicationsForms);
								savingsAccount.setUser(applicationsForms.getUser());

								savingsAccountDao.save(savingsAccount);
								applicationsForms.setUser(null);
								applicationsFormsDao.save(applicationsForms);

								return new ResponseEntity<String>("created", HttpStatus.OK);

						}
							applicationsFormsDao.delete(applicationsForms);
							return new ResponseEntity<String>("Application already approved!!",HttpStatus.BAD_REQUEST);
						} else {
							
							
							return new ResponseEntity<String>("Already there is an account",HttpStatus.NOT_ACCEPTABLE);
							
						}

					} else {
						return new ResponseEntity<String>("This is not a savings account type", HttpStatus.BAD_REQUEST);
					}

				} else {
					return new ResponseEntity<String>("Not found",HttpStatus.NOT_FOUND);
				}
			}
			else {
				return new ResponseEntity<String>("UNAUTHORIZED",HttpStatus.UNAUTHORIZED);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> approveCurrentAccount(String id) {
		try {

			if (jwtFilter.isManager()) {
				
				Optional<ApplicationsForms> optionalApplicationForms = applicationsFormsDao.findById(Long.parseLong(id));

				if (optionalApplicationForms.isPresent()) {
					

					ApplicationsForms applicationsForms = optionalApplicationForms.get();
					
					if (applicationsForms.getCategory().charAt(0)=='c') {
						
						if (!Objects.isNull(applicationsForms.getUser())) {
							CurrentAccount currentAccount = currentAccountDao.getInfoOfTheUser(applicationsForms.getUser().getId());
							
							if (Objects.isNull(currentAccount)) {
								
								currentAccount = new CurrentAccount();
								
								currentAccount.setAccountCreattionDate(new Date());
								currentAccount.setApplicationsForms(applicationsForms);
								currentAccount.setDraft(0d);
								currentAccount.setUser(applicationsForms.getUser());
								
								currentAccountDao.save(currentAccount);
								applicationsForms.setUser(null);
								applicationsFormsDao.save(applicationsForms);

								return new ResponseEntity<String>("created", HttpStatus.OK);

						} else {
							applicationsFormsDao.delete(applicationsForms);
							return new ResponseEntity<String>("Application already approved!!",HttpStatus.BAD_REQUEST);
						}
						
						
							
						} else {
							return new ResponseEntity<String>("Already Have a current account",HttpStatus.NOT_ACCEPTABLE);
						}
						
					} else {
						return new ResponseEntity<String>("This is not a current account type", HttpStatus.BAD_REQUEST);
					}
					
				} else {
					return new ResponseEntity<String>("Not found",HttpStatus.NOT_FOUND);
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
