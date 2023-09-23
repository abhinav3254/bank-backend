package com.rest;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/account")
public interface AccountRest {
	
	@GetMapping("/openAccount")
	public ResponseEntity<String> openAccount();
	
	@GetMapping("/balance")
	public ResponseEntity<String> getBalanceInfo();
	
	@PostMapping("/addBalance")
	public ResponseEntity<String> addBalance(@RequestParam String amount);
	
	@PostMapping("/withdraw")
	public ResponseEntity<String> withdrawBalance(@RequestParam String amount);
	
	
	@PostMapping("/fundTransfer")
	public ResponseEntity<String> fundsTransfer(@RequestBody(required = true)Map<String, String> map);
}
