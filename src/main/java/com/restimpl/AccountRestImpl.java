package com.restimpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.rest.AccountRest;
import com.services.AccountService;

@RestController
public class AccountRestImpl implements AccountRest {

	@Autowired
	private AccountService accountService;

	@Override
	public ResponseEntity<String> openAccount() {
		try {
			return accountService.openAccount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> getBalanceInfo() {
		try {
			return accountService.getBalanceInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> addBalance(String amount) {
		try {
			return accountService.addBalance(amount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> withdrawBalance(String amount) {
		try {
			return accountService.withdrawBalance(amount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> fundsTransfer(Map<String, String> map) {
		try {
			return accountService.fundsTransfer(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
