package com.services;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.pojo.Lockers;

public interface LockersService {
	
	public ResponseEntity<String> applyForLocker(Map<String, String>map);
	
	public ResponseEntity<List<Lockers>> getAllPendingLocker();
	
	public ResponseEntity<String> addLocker(Map<String, String>map);
	
	public ResponseEntity<String> approvePendingLocker(String id);
	
	public ResponseEntity<List<Lockers>> getAllLockers();
	
	public ResponseEntity<List<Lockers>> getAllLockerOfUser();

}
