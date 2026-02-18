package com.ijse.gdse73.backend.service.custom;

import com.ijse.gdse73.backend.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    void saveCustomer(CustomerDTO customerDTO);
    void updateCustomer(CustomerDTO customerDTO);
    void deleteCustomer(int id);
    CustomerDTO getCustomerById(int id);
    List<CustomerDTO> getAllCustomers();
}
