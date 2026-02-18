package com.ijse.gdse73.backend.controller;

import com.ijse.gdse73.backend.dto.CustomerDTO;
import com.ijse.gdse73.backend.service.custom.CustomerService;
import com.ijse.gdse73.backend.util.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor // from Lombok (for auto constructor injection)
@RequestMapping("api/v1/customer")
@CrossOrigin
public class CustomerController {
    private final CustomerService customerServiceImpl;

//    @Autowired // not required for constructor injection
//    public CustomerController(CustomerService customerService) {
//        this.customerService = customerService;
//    }

    @PostMapping
    public ResponseEntity<APIResponse<String>> saveCustomer(@RequestBody @Valid CustomerDTO customerDTO){
        customerServiceImpl.saveCustomer(customerDTO);

        return new ResponseEntity<>(new APIResponse<>(
                201,"Customer Saved",null
        ), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<APIResponse<String>> updateCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        customerServiceImpl.updateCustomer(customerDTO);

        return new ResponseEntity<>(new APIResponse<>(
                200,"Customer Updated",null
        ), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteCustomer(@PathVariable int id) {
        customerServiceImpl.deleteCustomer(id);

        return new ResponseEntity<>(new APIResponse<>(
                200,"Customer Deleted",null
        ), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<CustomerDTO>>> getAllCustomers(){
        return new ResponseEntity<>(new APIResponse<>(
                200,"Customers Uploaded",customerServiceImpl.getAllCustomers()
        ), HttpStatus.OK);
    }

}
