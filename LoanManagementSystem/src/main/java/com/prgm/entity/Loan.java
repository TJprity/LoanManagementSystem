package com.prgm.entity;

import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long loanId;//loan id
    double loanAmount;
    int tenure;
    double roi;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    double emi;
    
    String userId;//user id
    @Lob
    byte[] aadhaarFile;
    @Lob
    byte[] panFile;
    @Lob
    byte[] poaFile;
    
    Boolean approved = false;
    @JdbcTypeCode(org.hibernate.type.SqlTypes.VARCHAR)
	@Column(columnDefinition = "char(36)")
    UUID bid;
    Boolean sanctioned = false;
    String loanTxnId;
    double currPrincipal;
    int totalInstallments;
    private Long emiId;
    int paidInstallment;
    Boolean paid = false;
    // Add other loan attributes, getters, and setters
}

