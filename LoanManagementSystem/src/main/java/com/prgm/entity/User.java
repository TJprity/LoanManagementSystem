package com.prgm.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
@Entity
public class User {
    
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Long id;
	@Id
	@Column(nullable = false)
    String username;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;
    Boolean isAdmin = false;
    
    
    // Other user attributes, getters, and setters
}
