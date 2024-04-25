package com.sandeep.accounts.service.impl;

import com.sandeep.accounts.constants.AccountsConstants;
import com.sandeep.accounts.dto.AccountsDto;
import com.sandeep.accounts.entity.Accounts;
import com.sandeep.accounts.entity.Customer;
import com.sandeep.accounts.exception.CustomerAlreadyExistException;
import com.sandeep.accounts.exception.ResourceNotFoundException;
import com.sandeep.accounts.mapper.AccountsMapper;
import com.sandeep.accounts.mapper.CustomerMapper;
import org.springframework.stereotype.Service;

import com.sandeep.accounts.dto.CustomerDto;
import com.sandeep.accounts.repository.AccountsRepository;
import com.sandeep.accounts.repository.CustomerRepository;
import com.sandeep.accounts.service.IAccountService;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountService {
	
	private AccountsRepository accountsRepository;
	private CustomerRepository customerRepository;

	/**
	 * 
	 * @param customerDto - CustomerDto Object
	 */
	@Override
	public void createAccount(CustomerDto customerDto) {
		Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
		Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
		if(optionalCustomer.isPresent()){
			throw new CustomerAlreadyExistException("Customer already registered with given mobile number"
					+ customerDto.getMobileNumber());
		}
		//customer.setCreatedAt(LocalDateTime.now());
		//customer.setCreatedBy("Anonymous");
		Customer savedCustomer = customerRepository.save(customer);
		accountsRepository.save(createNewAccount(savedCustomer));
	}

	private Accounts createNewAccount(Customer customer){

		Accounts newAccount = new Accounts();
		newAccount.setCustomerId(customer.getCustomerId());
		long randomAccNumber = 10000000000L + new Random().nextInt(900000000);

		newAccount.setAccountNumber(randomAccNumber);
		newAccount.setAccountType(AccountsConstants.SAVINGS);
		newAccount.setBranchAddress(AccountsConstants.ADDRESS);
		//newAccount.setCreatedAt(LocalDateTime.now());
		//newAccount.setCreatedBy("Anonymous");
		return newAccount;
	}

	@Override
	public CustomerDto fetchAccount(String mobileNumber) {

		Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
				() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
		);
		Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
				() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
		);

		CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
		customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(account, new AccountsDto()));
		return customerDto;
	}

	/**
	 *
	 * @param customerDto
	 * @return
	 */
	@Override
	public boolean updateAccount(CustomerDto customerDto) {

		boolean isUpdated = false;
		AccountsDto accountsDto = customerDto.getAccountsDto();
		if(accountsDto != null) {
			Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
					() -> new ResourceNotFoundException("Account", "accountNumber", accountsDto.getAccountNumber().toString())
			);
			AccountsMapper.mapToAccounts(accountsDto,accounts);

			accounts = accountsRepository.save(accounts);

			Long customerId = accounts.getCustomerId();

			Customer customer = customerRepository.findById(customerId).orElseThrow(
					() -> new ResourceNotFoundException("Customer", "CustomerId", customerId.toString())
			);
			CustomerMapper.mapToCustomer(customerDto,customer);
			customerRepository.save(customer);
			isUpdated = true;
		}
		return isUpdated;
	}

	@Override
	public boolean deleteAccount(String mobileNumber) {

		Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
				() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
		);
		accountsRepository.deleteByCustomerId(customer.getCustomerId());
		customerRepository.deleteById(customer.getCustomerId());
		return true;
	}


}
