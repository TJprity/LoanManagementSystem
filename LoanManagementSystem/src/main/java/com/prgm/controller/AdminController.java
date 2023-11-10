package com.prgm.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prgm.dtos.LoanAproDto;
import com.prgm.dtos.LoanSancDto;
import com.prgm.entity.BankInfo;
import com.prgm.entity.EMI;
import com.prgm.entity.Loan;
import com.prgm.entity.User;
import com.prgm.service.EMIService;
import com.prgm.service.LoanService;
import com.prgm.service.UserService;
import com.prgm.commons;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private ModelMapper modelMapper;
    @Autowired
    private LoanService loanService;
    @Autowired
    private EMIService emiService;
    @Autowired
    private UserService userService;
    
    @PostMapping("/loan")
    public ResponseEntity<Object> getAllLoans(@RequestBody LoanAproDto loanDto){
    	User mu = modelMapper.map(loanDto, User.class);
		List<Loan>resp = null;
		try {
			User user = userService.autenticate(mu, true);
			resp = loanService.getAllLoans();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
        return ResponseEntity.ok(resp);
    }
    @PostMapping("/loan/{loanId}")
    public ResponseEntity<Object> getLoanById(@PathVariable Long loanId, @RequestBody LoanAproDto loanDto){
    	User mu = modelMapper.map(loanDto, User.class);
		Loan resp = null;
		try {
			User user = userService.autenticate(mu, true);
			resp = loanService.getLoanById(loanId).get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
        return ResponseEntity.ok(resp);
    }
    @PostMapping("/loan/{loanId}/emi")
    public ResponseEntity<Object> getAllEmiByLoan(@PathVariable Long loanId, @RequestBody LoanAproDto loanDto){
    	User mu = modelMapper.map(loanDto, User.class);
		List<EMI> resp = null;
		try {
			User user = userService.autenticate(mu, true);
			resp = emiService.getAllEmiByLoan(loanId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
        return ResponseEntity.ok(resp);
    }
    
	@PostMapping("/loan/approve/{loanId}")
    public ResponseEntity<Object> approveLoanRequest(@PathVariable Long loanId, @RequestBody LoanAproDto loanDto) {
		User mu = modelMapper.map(loanDto, User.class);
		Loan resp = null;
		try {
			User user = userService.autenticate(mu, true);
			resp = loanService.approveLoanRequest(loanId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
        return ResponseEntity.ok(resp);
    }
    
    @PostMapping("/loan/sanction/{loanId}")
    public ResponseEntity<Object> sanctionLoanRequest(@PathVariable Long loanId, @RequestBody LoanSancDto loanDto) {
    	
    	Loan resp = null;
		try {
			User mu = modelMapper.map(loanDto, User.class);
			BankInfo mbi = modelMapper.map(loanDto, BankInfo.class);
//			mbi.setBid(loanDto.getBid());
			commons.printObj(mbi);
			User user = userService.autenticate(mu, true);
			resp = loanService.sanctionLoanRequest(loanId, mbi);
			commons.printObj(resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
        return ResponseEntity.ok(resp);
    }
}
