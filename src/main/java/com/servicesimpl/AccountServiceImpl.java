package com.servicesimpl;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dao.AccountDao;
import com.dao.UserDao;
import com.jwt.JwtFilter;
import com.jwt.JwtUtils;
import com.pojo.Account;
import com.pojo.User;
import com.services.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private JwtFilter jwtFilter;

	@Autowired
	private AccountDao accountDao;

	
	/**
	 * Opens a new bank account for the authenticated user if one doesn't already exist.
	 *
	 * @return A ResponseEntity with a message indicating whether the account was created or already exists.
	 */
	@Override
	public ResponseEntity<String> openAccount() {
		try {
			
			User userOne = jwtFilter.getUserDetails();
			
			Account bankItem2 = accountDao.getUserBankItem(userOne.getId());
			
			
			if (Objects.isNull(bankItem2)) {
				// No account found so we can create a new account
				Account bankItem = new Account();
				
				User user = userDao.getUserByUsername(userOne.getUsername());
				
				bankItem.setAmount(0.00);
				bankItem.setUser(user);
				
				
				accountDao.save(bankItem);
				
				return new ResponseEntity<String>("ACCOUNT CREATED",HttpStatus.OK);
				
			} else {
				// account already exists
				return new ResponseEntity<String>("ACCOUNT ALREADY EXISTS",HttpStatus.MULTIPLE_CHOICES);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	/**
	 * Retrieves the current balance of the authenticated user's bank account.
	 *
	 * @return A ResponseEntity with the user's balance or an error message if the user is not found.
	 */
	@Override
	public ResponseEntity<String> getBalanceInfo() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String userToken = authentication.getName();
			String username = jwtUtils.extractUsername(userToken);

			// Retrieve the user's bank information based on the extracted username
			User user = userDao.getUserByUsername(username);
			
			Account bankItem = accountDao.getUserBankItem(user.getId());
			
			if (Objects.isNull(bankItem)) {
				return new ResponseEntity<String>("USER NOT FOUND",HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<String>(bankItem.getAmount().toString(),HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	/**
	 * Adds the specified amount to the user's bank balance.
	 *
	 * @param amount1 The amount to be added.
	 * @return A ResponseEntity with a message indicating the updated balance or an error message.
	 */
	@Override
	public ResponseEntity<String> addBalance(String amount1) {
		try {
			Double amount = Double.parseDouble(amount1);
			

			if (amount > 0) {
				// Obtain the currently authenticated user from the security context
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				String userToken = authentication.getName();
				String username = jwtUtils.extractUsername(userToken);

				// Retrieve the user's bank information based on the extracted username
				User user = userDao.getUserByUsername(username);

				// Retrieve the user's bank item
				Account bankItem = accountDao.getUserBankItem(user.getId());

				// Get the previous balance
				Double previousAmount = bankItem.getAmount();

				// Calculate the new balance after deposit
				// Update the bank item's balance
				bankItem.setAmount(amount + previousAmount);
				
				// Save the updated bank item
				accountDao.save(bankItem);

				return new ResponseEntity<String>(
						"DEPOSITED AMOUNT\nUPDATED BALANCE IS :- " + (amount + previousAmount), HttpStatus.OK);

			} else {
				// Return a bad request response if the amount is less than or equal to 0
				return new ResponseEntity<String>("AMOUNT IS LESS THAN 0", HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			// Handle any exceptions that may occur during the operation
			e.printStackTrace();
		}
		// Return an internal server error response if an exception is caught
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}


	@Override
	public ResponseEntity<String> withdrawBalance(String amount1) {
		try {
			Double amount = Double.parseDouble(amount1);
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String userToken = authentication.getName();
			String username = jwtUtils.extractUsername(userToken);

			// Retrieve the user's bank information based on the extracted username
			User user = userDao.getUserByUsername(username);
			
			Account account = accountDao.getUserBankItem(user.getId());
			
			if (Objects.isNull(account)) {
				
			} else {
				Double userBalance = account.getAmount();
				
				if (userBalance<amount) {
					// insufficient Balance
					return new ResponseEntity<String>("insufficient Balance",HttpStatus.BAD_REQUEST);
				} else {
					userBalance = userBalance - amount;
					
					account.setAmount(userBalance);
					
					accountDao.save(account);
					
					return new ResponseEntity<String>("amount withdraw :- "+amount,HttpStatus.OK);
				}
				
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}


	@Override
	public ResponseEntity<String> fundsTransfer(Map<String, String> map) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String userToken = authentication.getName();
			String username = jwtUtils.extractUsername(userToken);

			// Retrieve the user's bank information based on the extracted username
			User user = userDao.getUserByUsername(username);
			
			Account account = accountDao.getUserBankItem(user.getId());
			
			if (Objects.isNull(account)) {
				return new ResponseEntity<String>("YOU DON'T HAVE ACCOUNT",HttpStatus.BAD_REQUEST);
			} else {
				
				String reciever = map.get("reciever");
				Double amount = Double.parseDouble(map.get("amount"));
				
				if (reciever.isEmpty()) {
					return new ResponseEntity<String>("reciever name can't be empty",HttpStatus.NO_CONTENT);
				} else if (amount<0) {
					return new ResponseEntity<String>("amount can't be empty",HttpStatus.NO_CONTENT);
				} else {
					Double userBalance = account.getAmount();
					
					if (userBalance<amount) {
						return new ResponseEntity<String>("INSUFFICENT FUNDS",HttpStatus.CONFLICT);
					} else {
						User recieverUser = userDao.getUserByUsername(reciever);
						
						
						if (Objects.isNull(recieverUser)) {
							return new ResponseEntity<String>("NO USER FOUND BY THIS USERNAME",HttpStatus.NOT_FOUND);
						} else {
							Account recieverAccount = accountDao.getUserBankItem(recieverUser.getId());
							
							if (Objects.isNull(recieverAccount)) {
								return new ResponseEntity<String>("NO ACCOUNT FOUND BY THIS USERNAME",HttpStatus.NOT_FOUND);
							} else {
								
								Double recieverAmount =  recieverAccount.getAmount()+amount;
								Double senderAmount = account.getAmount()-amount;
								
								recieverAccount.setAmount(recieverAmount);
								account.setAmount(senderAmount);
								
								accountDao.save(recieverAccount);
								accountDao.save(account);
								
								return new ResponseEntity<String>("FUNDS TRANSFERED",HttpStatus.OK);
							}
						}
						
					}
					
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}


}
