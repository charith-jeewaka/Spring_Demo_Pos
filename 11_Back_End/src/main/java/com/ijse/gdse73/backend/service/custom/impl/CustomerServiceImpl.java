package com.ijse.gdse73.backend.service.custom.impl;

import com.ijse.gdse73.backend.dto.CustomerDTO;
import com.ijse.gdse73.backend.entity.Customer;
import com.ijse.gdse73.backend.exception.CustomException;
import com.ijse.gdse73.backend.repository.CustomerRepository;
import com.ijse.gdse73.backend.service.custom.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// have @Component
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void saveCustomer(CustomerDTO customerDTO){
        if (customerDTO==null) {
            throw new CustomException("CustomerDTO is null");
        }
        customerRepository.save(modelMapper.map(customerDTO, Customer.class));
    }

    @Override
    public void updateCustomer(CustomerDTO customerDTO) {
        if (customerDTO==null) {
            throw new CustomException("CustomerDTO is null");
        }
        customerRepository.save(modelMapper.map(customerDTO, Customer.class));
    }

    @Override
    public void deleteCustomer(int id) {
        customerRepository.deleteById(id);
    }

    @Override
    public CustomerDTO getCustomerById(int id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found: " + id));

        return modelMapper.map(customer, CustomerDTO.class);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> modelMapper.map(customer, CustomerDTO.class))
                .toList();
    }

//    @Override
//    public List<CustomerDTO> getAllCustomers() {
//        return customerRepository.findAll()
//                .stream()
//                .map(customer -> new CustomerDTO(
//                        customer.getId(),
//                        customer.getName(),
//                        customer.getAddress()
//                ))
//                .toList();
//    }


}
