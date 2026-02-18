package com.ijse.gdse73.backend.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class APIResponse<T> {
    private int status;
    private String message;
    private T data;
}
