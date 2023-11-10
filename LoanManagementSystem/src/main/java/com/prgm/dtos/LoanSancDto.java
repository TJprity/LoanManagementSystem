package com.prgm.dtos;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import lombok.Data;
@Data
public class LoanSancDto {

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	String username;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;
	@JsonProperty("bankInfoId")
	UUID bid;
	//or
	String accNumber;
	String micr;
	String ifscCode;
	
	
}
