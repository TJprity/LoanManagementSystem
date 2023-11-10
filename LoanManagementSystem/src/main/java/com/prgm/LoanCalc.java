package com.prgm;

import com.prgm.entity.Loan;

public class LoanCalc {
	final public int tenure;
	final public double loanAmount;
	final public double roi;
	
	public int I;
	public double epa;
	public double eia;
	public double emi;
	
	public LoanCalc(double loanAmount,int tenure,double roi) throws Exception {
		System.out.println("here Loancalc");
		if (loanAmount == 0 || tenure == 0 || roi == 0) {
        	throw new Exception("Invalid values, recheck");
        }
		this.tenure=tenure;
		this.loanAmount=loanAmount;
		this.roi=roi;
	}
	public LoanCalc(Loan loan) throws Exception {
		this(loan.getLoanAmount(),loan.getTenure(),loan.getRoi());
		System.out.println("here Loancalc(loan)");
	}
	public int getInstallments() {
		this.I=(this.I==0)?(this.tenure * 12):this.I;
		return this.I;
	}
	public double getEmiPrincipalAmount() {
		this.epa=(this.epa==0.0)?(loanAmount / this.getInstallments()):this.epa;
		return this.epa;
	}
	public double getEmiInterestAmount() {
		this.eia=(this.eia==0.0)?(this.getEmiPrincipalAmount() * this.roi / 100):this.eia;
		return this.eia;
	}
	public double getEmi() {
		this.emi = (this.emi==0.0)?(this.getEmiPrincipalAmount() + getEmiInterestAmount()):this.emi;
		return this.emi;
	}
	

}
