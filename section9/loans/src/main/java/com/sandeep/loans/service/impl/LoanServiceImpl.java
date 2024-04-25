package com.sandeep.loans.service.impl;

import com.sandeep.loans.constants.LoansConstant;
import com.sandeep.loans.dto.LoansDto;
import com.sandeep.loans.entity.Loans;
import com.sandeep.loans.exception.LoanAlreadyExistsException;
import com.sandeep.loans.exception.ResourceNotFoundException;
import com.sandeep.loans.mapper.LoansMapper;
import com.sandeep.loans.repository.LoansRepository;
import com.sandeep.loans.service.ILoanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoanServiceImpl  implements ILoanService {
    
    LoansRepository loansRepository;

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> optionalLoans = loansRepository.findByMobileNumber(mobileNumber);
        if(optionalLoans.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already registered with given mobile number " + mobileNumber);
        }
        loansRepository.save(createNewLoan(mobileNumber));
    }

    private Loans createNewLoan(String mobileNumber) {

        Loans newLoan = new Loans();

        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstant.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstant.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstant.NEW_LOAN_LIMIT);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));

        return newLoan;
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loans", "mobileNumber", mobileNumber)
        );

        return LoansMapper.mapToLoansDto(loans, new LoansDto());
    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {
        boolean isUpdated = false;
        if (loansDto != null) {
            Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Loans", "loansNumber", loansDto.getLoanNumber())
            );
            LoansMapper.mapToLoans(loans, loansDto);
            loansRepository.save(loans);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {

        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loans", "mobileNumber", mobileNumber)
        );

        loansRepository.deleteById(loans.getLoanId());

        return true;
    }
}
