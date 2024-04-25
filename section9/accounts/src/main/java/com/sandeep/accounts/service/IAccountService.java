package com.sandeep.accounts.service;

import com.sandeep.accounts.dto.CustomerDto;

public interface IAccountService {
	
	/**
	 * 
	 * @param customerDto - CustomerDto Object
	 */
	void createAccount(CustomerDto customerDto);

	CustomerDto fetchAccount(String mobileNumber);

	boolean updateAccount(CustomerDto customerDto);

	boolean deleteAccount(String mobileNumber);

}
