package com.prgm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prgm.entity.EMI;

public interface EMIRepository extends JpaRepository<EMI, Long> {
	List<EMI> findAllByLoanId(Long loanId);
}
