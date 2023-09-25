package com.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pojo.Lockers;

@RequestMapping("/locker")
public interface LockersRest {
	
	@PostMapping("/add-locker")
	public ResponseEntity<String> addLocker(@RequestBody(required = true)Map<String, String>map);
	
	@GetMapping("/users/get-all")
	public ResponseEntity<List<Lockers>> getAllLockers();
	
	@PatchMapping("/apply-for-locker")
	public ResponseEntity<String> applyForLocker(@RequestBody(required = true)Map<String, String>map);
	
	@GetMapping("/get-all-pending-locker")
	public ResponseEntity<List<Lockers>> getAllPendingLocker();
	
	@PatchMapping("/approve-pending-locker/{id}")
	public ResponseEntity<String> approvePendingLocker(@PathVariable(required = true) String id);
	
}
