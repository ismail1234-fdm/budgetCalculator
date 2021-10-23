package com.budgetCalculator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    //hier we catch only ApiRequestException, in case of multiple Exception, we can add more class
    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        // 1.create payload containing exception details
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException =  new ApiException(
                e.getMessage(),
                e,
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
       return new ResponseEntity<>(apiException, badRequest);
        // 2.return response entity
    }
}
