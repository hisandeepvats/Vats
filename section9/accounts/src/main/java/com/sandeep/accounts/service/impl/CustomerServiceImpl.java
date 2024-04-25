package com.sandeep.accounts.service.impl;

import com.sandeep.accounts.dto.AccountsDto;
import com.sandeep.accounts.dto.CardsDto;
import com.sandeep.accounts.dto.CustomerDetailsDto;
import com.sandeep.accounts.dto.LoansDto;
import com.sandeep.accounts.entity.Accounts;
import com.sandeep.accounts.entity.Customer;
import com.sandeep.accounts.exception.ResourceNotFoundException;
import com.sandeep.accounts.mapper.AccountsMapper;
import com.sandeep.accounts.mapper.CustomerMapper;
import com.sandeep.accounts.repository.AccountsRepository;
import com.sandeep.accounts.repository.CustomerRepository;
import com.sandeep.accounts.service.ICustomerService;
import com.sandeep.accounts.service.client.CardsFeignClient;
import com.sandeep.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    /**
     *
     * @param mobileNumber
     * @return
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity =  loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity =  cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;
    }
}
