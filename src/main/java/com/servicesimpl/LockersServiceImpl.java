package com.servicesimpl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dao.LockersDao;
import com.jwt.JwtFilter;
import com.pojo.Lockers;
import com.pojo.User;
import com.services.LockersService;

@Service
public class LockersServiceImpl implements LockersService {

	@Autowired
	private JwtFilter jwtFilter;

	@Autowired
	private LockersDao lockersDao;
	
	@Override
	public ResponseEntity<String> addLocker(Map<String, String> map) {
		try {
			if (jwtFilter.isManager()) {
				
				String size = map.get("size");
				boolean status = false;
				boolean available = true;
				double money = Double.parseDouble(map.get("money"));
				
				Lockers lockers = new Lockers();
				
				lockers.setSize(size);
				lockers.setStatus(status);
				lockers.setAvailable(available);
				lockers.setMoney(money);
				
				lockersDao.save(lockers);
				
				return new ResponseEntity<String>("Locker Added",HttpStatus.OK);
				
			} else {
				return new ResponseEntity<String>("UNAUTHORIZED",HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	public ResponseEntity<List<Lockers>> getAllLockers() {
		try {
			List<Lockers> listLockers = lockersDao.getAllAvailableLockers();
			return new ResponseEntity<List<Lockers>>(listLockers,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<Lockers>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> applyForLocker(Map<String, String> map) {
		try {
			Integer lockerId = Integer.parseInt(map.get("lockerId"));
			String nomineeName = map.get("nomineeName");
			boolean available = false;
			String tenure = map.get("tenure");
			Date applyDate = new Date();
			User user = jwtFilter.getUserDetails();
			Optional<Lockers> lockersOptional = lockersDao.findById(lockerId);
			Lockers lockers = lockersOptional.get();
			
			lockers.setNomineeName(nomineeName);
			lockers.setAvailable(available);
			lockers.setTenure(tenure);
			lockers.setApplyDate(applyDate);
			lockers.setUser(user);
			lockers.setAllocated(false);
			
			lockersDao.save(lockers);
			
			return new ResponseEntity<String>("Application Submitted",HttpStatus.OK);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Lockers>> getAllPendingLocker() {
		try {
			if (jwtFilter.isManager()) {
				List<Lockers> list = lockersDao.getAllPendingLockersRequest();
				
				return new ResponseEntity<List<Lockers>>(list,HttpStatus.OK);
			} else {
				return new ResponseEntity<List<Lockers>>(HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<Lockers>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}


	@Override
	public ResponseEntity<String> approvePendingLocker(String id) {
		try {
			
			if (jwtFilter.isManager()) {
				
				
				Optional<Lockers> lockerOptional = lockersDao.findById(Integer.parseInt(id));
				Lockers lockers = lockerOptional.get();
				
				lockers.setAllocatedDate(new Date());
				lockers.setAllocated(true);
				
				lockersDao.save(lockers);
				
				return new ResponseEntity<String>("Approved",HttpStatus.OK);
				
			} else {
				return new ResponseEntity<String>("UNAUTHORIZED",HttpStatus.UNAUTHORIZED);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Lockers>> getAllLockerOfUser() {
		try {
			List<Lockers> listLockers = lockersDao.getAllLockerOfUser(jwtFilter.getUserDetails().getId());
			return new ResponseEntity<List<Lockers>>(listLockers,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<Lockers>>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
