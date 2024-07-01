package com.example.user.exception.token;

import com.example.user.exception.user.UserNotFoundException;
import com.example.user.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TokenExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException userNotFoundException){
        ErrorResponse userErrorResponse= ErrorResponse.
                builder().
                code(HttpStatus.ALREADY_REPORTED.value()).
                message(userNotFoundException.getMessage()).
                timestamp(System.currentTimeMillis()).
                build();
        return new ResponseEntity<>(userErrorResponse,HttpStatus.ALREADY_REPORTED);

    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExist(TokenAlreadyExistException emailAlreadyExistException){
        ErrorResponse userErrorResponse= ErrorResponse.
                builder().
                code(HttpStatus.NOT_ACCEPTABLE.value()).
                message(emailAlreadyExistException.getMessage()).
                timestamp(System.currentTimeMillis()).
                build();
        return new ResponseEntity<>(userErrorResponse,HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> tokenExpired(TokenExpiredException tokenExpiredException){
        ErrorResponse userErrorResponse= ErrorResponse.
                builder().
                code(HttpStatus.EXPECTATION_FAILED.value()).
                message(tokenExpiredException.getMessage()).
                timestamp(System.currentTimeMillis()).
                build();
        return new ResponseEntity<>(userErrorResponse,HttpStatus.EXPECTATION_FAILED);

    }
}
