package com.example.user.controllers;


import com.example.user.exception.token.TokenAlreadyExistException;
import com.example.user.exception.user.EmailAlreadyExistException;
import com.example.user.exception.user.UserNotFoundException;
import com.example.user.model.UserModel;
import com.example.user.request.AuthenticationResponse;
import com.example.user.request.LoginRequest;
import com.example.user.request.RegisterRequest;
import com.example.user.response.LoginRes;
import com.example.user.services.AuthService;
import com.example.user.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<LoginRes> login(@Valid @RequestBody LoginRequest loginRequest ) throws UserNotFoundException, TokenAlreadyExistException {
       return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest registerRequest) throws UserNotFoundException, EmailAlreadyExistException {
        return ResponseEntity.ok(authService.register(registerRequest));
    }


}