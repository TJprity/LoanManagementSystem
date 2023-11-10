package com.prgm.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prgm.LoanCalc;
import com.prgm.commons;
import com.prgm.entity.BankInfo;
import com.prgm.entity.EMI;
import com.prgm.entity.Loan;
import com.prgm.repository.BankInfoRepository;
import com.prgm.repository.EMIRepository;
import com.prgm.repository.LoanRepository;

@Service
public class EMIService {
    @Autowired
    private EMIRepository emiRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private BankInfoRepository bankInfoRepo;

    public EMI newEMI(Loan loan,LocalDate dueDate) throws Exception {
    	EMI emi = new EMI();
    	System.out.println("here newEMI");
    	LoanCalc lc = new LoanCalc(loan);
    	emi.setLoanId(loan.getLoanId());
    	emi.setUserId(loan.getUserId());
    	emi.setPAmount(lc.getEmiPrincipalAmount());
    	emi.setIAmount(lc.getEmiInterestAmount());
//    	if(!dueDate.isAfter(LocalDate.now())) {throw new Exception("DueDate cannot be before today");}
    	emi.setDueDate(dueDate);
    	System.out.println("here newEMI");
    	return emiRepository.save(emi);
    }
    public Loan payEMI(Long loanId) throws Exception {
    	Optional<Loan> dbloan= loanRepository.findById(loanId);
		Loan loan = dbloan.get();
		if(loan.getPaid()) {throw new Exception("Loan is already paid");}
		
		Optional<EMI> dbemi= emiRepository.findById(loan.getEmiId());
		EMI emi = dbemi.get();
		if(!emi.getPaid() && emi.getEmiTxnId()==null) {
			Optional<BankInfo> obi = bankInfoRepo.findById(loan.getBid());
			BankInfo dbBankInfo = obi.get();
			String emiTxnId = commons.paymentHandler(dbBankInfo,"debit",emi.getAmount()); //payment dummy
			if(emiTxnId==null) {throw new Exception("Payment Failed");}
			emi.setEmiTxnId(emiTxnId);
			emi.setPaid(true);
			emiRepository.save(emi);
			
		} else {
			EMI newemi = newEMI(loan, emi.getDueDate().plusMonths(1));
			loan.setEmiId(newemi.getEmiId());
			loanRepository.save(loan);
			throw new Exception("current EMI already paid");
		}
		loan.setPaidInstallment(loan.getPaidInstallment()+1);
		loan.setCurrPrincipal(loan.getCurrPrincipal()-emi.getPAmount());
		if(loan.getPaidInstallment()>=loan.getTotalInstallments() || loan.getCurrPrincipal()<=0.0) {
			loan.setPaid(true);
		} else {
			EMI newemi = newEMI(loan, emi.getDueDate().plusMonths(1));
			loan.setEmiId(newemi.getEmiId());
		}
		return loanRepository.save(loan);
        // Implement EMI payment logic
    }
	public List<EMI> getAllEmiByLoan(Long loanId) {
		// TODO Auto-generated method stub
		return emiRepository.findAllByLoanId(loanId);
	}
}