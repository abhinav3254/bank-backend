package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pojo.CurrentAccount;

public interface CurrentAccountDao extends JpaRepository<CurrentAccount, Integer> {

	@Query(nativeQuery = true, value = "select * from current_account where user_id=:user_id")
	public CurrentAccount getInfoOfTheUser(int user_id);
	
}
