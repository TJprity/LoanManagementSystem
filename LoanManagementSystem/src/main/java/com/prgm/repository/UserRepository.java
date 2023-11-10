package com.prgm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prgm.entity.Loan;
import com.prgm.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
}

