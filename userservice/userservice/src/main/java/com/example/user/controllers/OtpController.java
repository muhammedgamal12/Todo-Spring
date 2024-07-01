package com.example.user.controllers;

import com.example.user.exception.otp.OtpNotFoundException;
import com.example.user.exception.user.UserNotFoundException;
import com.example.user.request.ChangePassword;
import com.example.user.response.UserResponse;
import com.example.user.services.OtpService;
import com.example.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class OtpController {
    @Autowired
    OtpService otpService;

    @Autowired
    UserService userService;

    @PostMapping("/forgetPassword")
    public  void forgetPassword(@RequestParam("email") String email)throws UserNotFoundException {
        otpService.sendOtp(email);

    }

    @PostMapping("/forgetPassword/verifyOtp")
    public ResponseEntity<String> verifyOtp(@RequestParam("otp") int otp, @RequestParam("email") String email) throws  OtpNotFoundException {

        return otpService.checkOtp(otp,email);
    }

    @PostMapping("/sendOtp")
    public ResponseEntity<String> sendOtp(@RequestParam("email") String email) throws UserNotFoundException {
            return otpService.sendOtp(email);
    }


}
