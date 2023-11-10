package com.prgm.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CalcEMIdto {

	//Long id;//loan id
    double loanAmount;
    int tenure;
    double roi;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    double emi;
}
