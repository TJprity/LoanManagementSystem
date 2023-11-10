package com.prgm;

import org.apache.commons.lang3.RandomStringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgm.entity.BankInfo;

public class commons {

	public static void printObj(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			System.out.println(mapper.writeValueAsString(obj));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String paymentHandler(BankInfo binfo,String TxnType,Double amount) {
		//dummy payment handler //todo: implement real world payment api
		System.out.println("here pay handler");
		return RandomStringUtils.random(10, true, true);
	}
	
}
