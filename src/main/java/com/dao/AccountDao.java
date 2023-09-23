package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pojo.Account;

public interface AccountDao extends JpaRepository<Account, Integer> {
	
	@Query(nativeQuery = true,value = "select * from account where user_id=:userId")
	public Account getUserBankItem(Integer userId);
	
}
