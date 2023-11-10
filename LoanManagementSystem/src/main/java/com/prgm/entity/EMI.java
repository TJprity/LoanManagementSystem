package com.prgm.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class EMI {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emiId;
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private long loanId;
    private double pAmount;
    private double iAmount;
    private LocalDate dueDate;
    private Boolean paid = false;
    private String emiTxnId;
    
    public double getAmount() {return this.pAmount+this.iAmount;}
    // Add other EMI attributes, getters, and setters
}
