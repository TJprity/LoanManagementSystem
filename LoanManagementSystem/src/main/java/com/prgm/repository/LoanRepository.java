package com.prgm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prgm.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> {
	Optional<Loan> findByUserId(String userId);
}

