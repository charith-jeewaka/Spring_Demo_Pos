package com.ijse.gdse73.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CustomerDTO {
//    @Pattern(regexp = "^\\d+$", message = "numbers only")
    private Integer id;

    @NotBlank(message = "Customer name is mandatory")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Customer name can only contain letters and spaces")
    private String name;

    @NotBlank(message = "Customer address is mandatory")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Customer address can only contain letters and spaces")
//    @Size(min = 5, message = "Address must be at least 5 characters long")
    private String address;

}
