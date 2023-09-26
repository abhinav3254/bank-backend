package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pojo.Lockers;

public interface LockersDao extends JpaRepository<Lockers, Integer> {
	
	
	@Query(nativeQuery = true,value = "select * from lockers where available = false and allocated = false")
	public List<Lockers> getAllPendingLockersRequest();
	
	@Query(nativeQuery = true,value = "select * from lockers where available = true")
	public List<Lockers> getAllAvailableLockers();
	
	@Query(nativeQuery = true, value = "select * from lockers where user_id =:uid" )
	public List<Lockers> getAllLockerOfUser(Integer uid);

}
