package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pojo.Loan;

public interface LoanDao extends JpaRepository<Loan, Integer> {

}
