package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pojo.SavingsAccount;

public interface SavingsAccountDao extends JpaRepository<SavingsAccount, Integer> {
	
	@Query(nativeQuery = true,value = "select * from savings_account where user_id =:user_id")
	public SavingsAccount getUserSavingsAccount(int user_id);
	
}
