package com.budgetCalculator.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtErrorResponse {
    private String errorMessage;
    private HttpStatus httpStatus;
}
