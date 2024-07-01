package com.example.user.exception.user;

import com.example.user.exception.token.TokenAlreadyExistException;
import com.example.user.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException userNotFoundException){
        ErrorResponse userErrorResponse= ErrorResponse.
                builder().
                code(HttpStatus.NOT_FOUND.value()).
                message(userNotFoundException.getMessage()).
                timestamp(System.currentTimeMillis()).
                build();
        return new ResponseEntity<>(userErrorResponse,HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleTokenAlreadyExist(EmailAlreadyExistException emailAlreadyExistException){
        ErrorResponse userErrorResponse= ErrorResponse.
                builder().
                code(HttpStatus.ALREADY_REPORTED.value()).
                message(emailAlreadyExistException.getMessage()).
                timestamp(System.currentTimeMillis()).
                build();
        return new ResponseEntity<>(userErrorResponse,HttpStatus.ALREADY_REPORTED);

    }
}
