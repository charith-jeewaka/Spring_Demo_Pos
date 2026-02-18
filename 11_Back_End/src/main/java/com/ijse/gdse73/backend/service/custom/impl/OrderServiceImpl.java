package com.ijse.gdse73.backend.service.custom.impl;

import com.ijse.gdse73.backend.dto.ItemDTO;
import com.ijse.gdse73.backend.dto.OrderDTO;
import com.ijse.gdse73.backend.dto.OrderItemDTO;
import com.ijse.gdse73.backend.entity.Customer;
import com.ijse.gdse73.backend.entity.Item;
import com.ijse.gdse73.backend.entity.Order;
import com.ijse.gdse73.backend.entity.OrderItem;
import com.ijse.gdse73.backend.repository.OrderRepository;
import com.ijse.gdse73.backend.service.custom.CustomerService;
import com.ijse.gdse73.backend.service.custom.ItemService;
import com.ijse.gdse73.backend.service.custom.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ItemService itemService;
    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public void placeOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setDate(orderDTO.getDate());

        double total = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemDTO orderItemDTO : orderDTO.getItems()) {
            // Fetch item
            ItemDTO itemDTO = itemService.getItemById(orderItemDTO.getItemId());
            if (orderItemDTO.getQty() > itemDTO.getQty()) {
                throw new RuntimeException("Insufficient stock for item: " + itemDTO.getName());
            }

            // Reduce stock
            itemService.reduceStock(itemDTO.getId(), orderItemDTO.getQty());

            // Create OrderItem entity
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(modelMapper.map(itemDTO, Item.class));
            orderItem.setQty(orderItemDTO.getQty());
            orderItem.setUnitPrice(itemDTO.getPrice());
            orderItem.setOrder(order); // link to parent order

            orderItems.add(orderItem);

            // Add to total
            total += itemDTO.getPrice() * orderItemDTO.getQty();
        }

        order.setOrderItems(orderItems);
        order.setTotal(total);

        // Link customer
        order.setCustomer(modelMapper.map(customerService.getCustomerById(orderDTO.getCustomerId()), Customer.class));

        // Save order (cascade saves orderItems)
        orderRepository.save(order);
    }

    @Override
    public List<OrderDTO> getOrderHistory() {
        return orderRepository.findAll()
                .stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .toList();
    }
}
