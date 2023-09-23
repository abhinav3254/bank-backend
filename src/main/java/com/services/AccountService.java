package com.services;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface AccountService {

	public ResponseEntity<String> openAccount();

	public ResponseEntity<String> getBalanceInfo();

	public ResponseEntity<String> addBalance(String amount);
	
	public ResponseEntity<String> withdrawBalance(String amount);
	
	public ResponseEntity<String> fundsTransfer(Map<String, String> map);

}
