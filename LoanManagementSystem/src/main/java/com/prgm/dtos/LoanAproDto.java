package com.prgm.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
public class LoanAproDto {

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	String username;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;
	
	
	
}
