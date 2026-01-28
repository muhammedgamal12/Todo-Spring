package com.example.user.controllers;

import com.example.user.exception.token.TokenExpiredException;
import com.example.user.exception.user.UserNotFoundException;
import com.example.user.model.UserModel;
import com.example.user.request.ChangePassword;
import com.example.user.request.MailRequest;
import com.example.user.response.UserResponse;
import com.example.user.services.JwtService;
import com.example.user.services.MailService;
import com.example.user.services.OtpService;
import com.example.user.services.UserService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController
{
    @Autowired
    MailService mailService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    UserService userService;




    @PostMapping("/sendMail")
    public void sendMail(@Valid @RequestBody MailRequest mailRequest)throws UserNotFoundException {

        mailService.sendEmail(mailRequest);
    }

    @PutMapping("/changePassword/{email}")
    public  ResponseEntity<String> changePassword(@PathVariable String email, @RequestBody ChangePassword changePassword) throws UserNotFoundException {

        return userService.changePassword(email,changePassword);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam("email")String email) throws UserNotFoundException {
        return userService.deleteUser(email);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) throws TokenExpiredException {
        return userService.logout(request);
    }

    @GetMapping("/searchUser")
    public UserModel searchByEmail(@RequestParam String email) throws UserNotFoundException {

        return  userService.searchUser(email);
    }
    @GetMapping("/findAllUser")
    public List<UserModel> searchAll(HttpServletRequest request, @RequestParam String email) throws UserNotFoundException, TokenExpiredException {

        return  userService.searchAllUser(request,email);
    }
    @GetMapping("/checkToken")
    public UserResponse checkToken(HttpServletRequest request) throws UserNotFoundException, TokenExpiredException {

        return userService.checkToken(request);
    }


}
