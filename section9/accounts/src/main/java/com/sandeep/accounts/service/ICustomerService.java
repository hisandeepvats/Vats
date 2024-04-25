package com.sandeep.accounts.service;

import com.sandeep.accounts.dto.CustomerDetailsDto;

public interface ICustomerService {

    /**
     *
     * @param mobileNumber
     * @return
     */
    CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId);
}
