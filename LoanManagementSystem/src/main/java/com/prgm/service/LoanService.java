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
import com.prgm.repository.UserRepository;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private BankInfoRepository bankInfoRepo;
    @Autowired
    private EMIService emiService;

    
    
    public Loan calculateEMI(Loan loan) throws Exception {
        LoanCalc lc = new LoanCalc(loan);
        double emi = lc.getEmi();
    	loan.setEmi(emi);
    	loan.setTotalInstallments(lc.getInstallments());
		return loan;
        // Implement EMI calculation logic
    }
    public BankInfo saveBankInfo(BankInfo bankInfo) {
    	return bankInfoRepo.save(bankInfo);
    }

    public Loan raiseLoanRequest(Loan loan) throws Exception {
    	Loan loan2 = calculateEMI(loan);
		return loanRepository.save(loan2);
        // Implement loan request handling
    }

	public Loan approveLoanRequest(Long loanid) {
		// TODO Auto-generated method stub
		Optional<Loan> dbloan= loanRepository.findById(loanid);
		Optional<Loan> dbts= loanRepository.findByUserId("user1");
		System.out.println(dbts.get().getLoanAmount());
		Loan loan = dbloan.get();
		loan.setApproved(true);
		return loanRepository.save(loan);
	}

	public Loan sanctionLoanRequest(Long loanid,BankInfo bankInfo) throws Exception {
		// TODO Auto-generated method stub
		Optional<Loan> dbloan= loanRepository.findById(loanid);
		Loan loan = dbloan.get();
		BankInfo dbBankInfo=null;
		if(!loan.getApproved()) {throw new Exception("Loan is not yet approved");}
		else if(loan.getBid() ==  null) {
			if(bankInfo.getAccNumber()!=null ) {
				dbBankInfo = saveBankInfo(bankInfo);
			}else if (bankInfo.getBid()!=null) {
				Optional<BankInfo> obi = bankInfoRepo.findById(bankInfo.getBid());
				dbBankInfo = obi.get();
			}else {throw new Exception("Bank Details not provided");}
			loan.setBid(dbBankInfo.getBid());
		}
		loan.setSanctioned(true);
		String loanTxnId = commons.paymentHandler(dbBankInfo,"credit",loan.getLoanAmount()); //payment dummy
		System.out.println("here after payhandler");
		if(loanTxnId==null) {throw new Exception("Payment Failed");}
		loan.setLoanTxnId(loanTxnId);
		
		loan.setCurrPrincipal(loan.getLoanAmount());
		loan.setPaidInstallment(0);
		EMI emi = emiService.newEMI(loan,LocalDate.now().plusMonths(1) );
		loan.setEmiId(emi.getEmiId());
		return loanRepository.save(loan);
	}
	public List<Loan> getAllLoans() {
		// TODO Auto-generated method stub
		return loanRepository.findAll();
	}
	public Optional<Loan> getLoanById(long loanId) {
		// TODO Auto-generated method stub
		return loanRepository.findById(loanId);
	}
	
}
