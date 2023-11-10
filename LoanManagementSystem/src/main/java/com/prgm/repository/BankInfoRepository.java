package com.prgm.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prgm.entity.BankInfo;

public interface BankInfoRepository extends JpaRepository<BankInfo, UUID> {

}
