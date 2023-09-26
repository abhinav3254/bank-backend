package com.restimpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.pojo.Lockers;
import com.rest.LockersRest;
import com.services.LockersService;


@RestController
public class LockersRestImpl implements LockersRest {
	
	@Autowired
	private LockersService lockersService;

	@Override
	public ResponseEntity<String> applyForLocker(Map<String, String> map) {
		try {
			return lockersService.applyForLocker(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Lockers>> getAllPendingLocker() {
		try {
			return lockersService.getAllPendingLocker();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<Lockers>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> addLocker(Map<String, String> map) {
		try {
			return lockersService.addLocker(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> approvePendingLocker(String id) {
		try {
			return lockersService.approvePendingLocker(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Lockers>> getAllLockers() {
		try {
			return lockersService.getAllLockers();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<Lockers>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Lockers>> getAllLockerOfUser() {
		try {
			return lockersService.getAllLockerOfUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<Lockers>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
