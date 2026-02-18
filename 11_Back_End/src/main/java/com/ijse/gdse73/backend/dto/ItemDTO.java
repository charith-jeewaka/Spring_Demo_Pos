package com.ijse.gdse73.backend.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ItemDTO {

    private Integer id;

    @NotBlank(message = "Item name is mandatory")
    private String name;

    @Min(value = 0, message = "Quantity cannot be negative")
    private int qty;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private double price;
}
