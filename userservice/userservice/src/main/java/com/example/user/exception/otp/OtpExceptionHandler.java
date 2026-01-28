package com.example.user.exception.otp;

import com.example.user.exception.user.UserNotFoundException;
import com.example.user.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OtpExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleOtpNotFound(OtpNotFoundException otpNotFoundException){
        ErrorResponse userErrorResponse= ErrorResponse.
                builder().
                code(HttpStatus.NOT_FOUND.value()).
                message(otpNotFoundException.getMessage()).
                timestamp(System.currentTimeMillis()).
                build();
        return new ResponseEntity<>(userErrorResponse,HttpStatus.NOT_FOUND);

    }
}
