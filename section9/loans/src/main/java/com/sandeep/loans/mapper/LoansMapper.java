package com.sandeep.loans.mapper;

import com.sandeep.loans.dto.LoansDto;
import com.sandeep.loans.entity.Loans;

public class LoansMapper {

    public static LoansDto mapToLoansDto(Loans loans, LoansDto loansDto) {
        loansDto.setLoanNumber(loans.getLoanNumber());
        loansDto.setLoanType(loans.getLoanType());
        loansDto.setTotalLoan(loans.getTotalLoan());
        loansDto.setAmountPaid(loans.getAmountPaid());
        loansDto.setMobileNumber(loans.getMobileNumber());
        loansDto.setOutstandingAmount(loans.getOutstandingAmount());
        return loansDto;
    }

    public static Loans mapToLoans(Loans loans, LoansDto loansDto) {
        loans.setLoanNumber(loansDto.getLoanNumber());
        loans.setLoanType(loansDto.getLoanType());
        loans.setTotalLoan(loansDto.getTotalLoan());
        loans.setAmountPaid(loansDto.getAmountPaid());
        loans.setMobileNumber(loansDto.getMobileNumber());
        loans.setOutstandingAmount(loansDto.getOutstandingAmount());
        return loans;
    }
}
