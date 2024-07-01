package com.example.todoservice.exception;

import com.example.todoservice.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TokenExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUserNotFound(TokenException tokenException){
        ErrorResponse userErrorResponse= ErrorResponse.
                builder().
                code(HttpStatus.NOT_FOUND.value()).
                message(tokenException.getMessage()).
                timestamp(System.currentTimeMillis()).
                build();
        return new ResponseEntity<>(userErrorResponse,HttpStatus.NOT_FOUND);

    }

}
