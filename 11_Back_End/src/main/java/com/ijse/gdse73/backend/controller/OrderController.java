package com.ijse.gdse73.backend.controller;

import com.ijse.gdse73.backend.dto.CustomerDTO;
import com.ijse.gdse73.backend.dto.OrderDTO;
import com.ijse.gdse73.backend.service.custom.OrderService;
import com.ijse.gdse73.backend.util.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/order")
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<APIResponse<String>> placeOrder(@RequestBody @Valid OrderDTO orderDTO){
        orderService.placeOrder(orderDTO);

        return new ResponseEntity<>(new APIResponse<>(
                201,"Order Saved",null
        ), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<OrderDTO>>> getOrderHistory(){
        return new ResponseEntity<>(new APIResponse<>(
                200,"Orders Uploaded",orderService.getOrderHistory()
        ), HttpStatus.OK);
    }
}
