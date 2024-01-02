package com.adeies.adeies.enterprise.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponse {
    private String message = "Operation Successful.";
    private Object data = "";
}
