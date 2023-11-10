package com.prgm.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgm.dtos.CalcEMIdto;
import com.prgm.dtos.LoanReqDto;
import com.prgm.entity.EMI;
import com.prgm.entity.Loan;
import com.prgm.entity.User;
import com.prgm.service.EMIService;
import com.prgm.service.LoanService;
import com.prgm.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private ModelMapper modelMapper;
    @Autowired
    private LoanService loanService;
    @Autowired
    private EMIService emiService;
    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) throws Exception {
    	user.setIsAdmin(false);
		return ResponseEntity.ok(userService.register(user));
    }

    @PostMapping("/calculate-emi")
    public ResponseEntity<CalcEMIdto> calculateEMI(@RequestBody CalcEMIdto emidto) throws Exception {
    	Loan loan = loanService.calculateEMI(modelMapper.map(emidto, Loan.class));
    
        return ResponseEntity.ok(modelMapper.map(loan, CalcEMIdto.class));
    }

    @PostMapping("/loan/new-request")
    public ResponseEntity<Loan> raiseLoanRequest(@RequestBody LoanReqDto loanDto) throws Exception {
    	
    	Loan ml = modelMapper.map(loanDto, Loan.class);
    	User mu = modelMapper.map(loanDto, User.class);
    	
//    	System.out.println(mapper.writeValueAsString(mu));
    	
    	User user = userService.autenticate(mu, false);
    	if(ml.getUserId()==null) {
    		ml.setUserId(user.getUsername());
    	}
    	Loan loan = loanService.raiseLoanRequest(ml);
        return ResponseEntity.ok(loan);
    }

    
    @PostMapping("/loan/{loanId}/emi/pay")
    public ResponseEntity<Object> payEMI(@PathVariable Long loanId) {
    	Loan resp = null;
		try {
			resp = emiService.payEMI(loanId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
        return ResponseEntity.ok(resp);
    }
}