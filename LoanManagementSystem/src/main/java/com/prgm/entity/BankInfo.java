package com.prgm.entity;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class BankInfo {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@JdbcTypeCode(org.hibernate.type.SqlTypes.VARCHAR)
	@Column(columnDefinition = "char(36)")
	@JsonAlias({ "bankInfoID" })
	UUID bid;
	
	@Column(nullable = false)
	String accNumber;
	
	@Column(nullable = false)
	String micr;
	
	@Column(nullable = false)
	String ifscCode;
	
	
}
