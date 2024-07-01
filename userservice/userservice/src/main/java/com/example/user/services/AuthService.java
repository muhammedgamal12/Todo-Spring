package com.example.user.services;

import com.example.user.exception.token.TokenAlreadyExistException;
import com.example.user.exception.user.EmailAlreadyExistException;
import com.example.user.exception.user.UserNotFoundException;
import com.example.user.model.Role;
import com.example.user.model.TokenModel;
import com.example.user.model.UserModel;
import com.example.user.repository.TokenRepository;
import com.example.user.repository.UserRepository;
import com.example.user.request.AuthenticationResponse;
import com.example.user.request.LoginRequest;
import com.example.user.request.RegisterRequest;
import com.example.user.response.LoginRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthService
{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userservice;


    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private  AuthenticationManager authenticationManager;

    public LoginRes login(LoginRequest request) throws UserNotFoundException, TokenAlreadyExistException {
        UserModel user = userRepository.findUserByEmail(request.getEmail());
        
        if ((request.getEmail())==null||user==null)
            throw new UserNotFoundException("Please Register first");
        if ( !passwordEncoder.matches(request.getPassword(),user.getPassword()))
            throw new UserNotFoundException("Password is Wrong");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        Map<String , Object> extraClaims = new HashMap<>();
        String jwtToken = jwtService.createToken(user , extraClaims);
        saveUserToken(user, jwtToken);


        return new LoginRes(request.getEmail(),jwtToken);
    }

    public AuthenticationResponse register(RegisterRequest request) throws UserNotFoundException, EmailAlreadyExistException {
        if ((request.getEmail())==null|| request.getPassword()==null)
            throw new UserNotFoundException("Please enter your email and password");

        UserModel userModel=userRepository.findUserByEmail(request.getEmail());
        if(userModel!=null)
            throw new EmailAlreadyExistException("This Email Alraedy Exist");
        UserModel user = UserModel.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .enabled(false)
                .build();

        UserModel savedUser = userRepository.save(user);
        Map<String , Object> extraClaims = new HashMap<>();

        return new AuthenticationResponse( request.getEmail());
    }

    private void saveUserToken(UserModel user, String jwtToken) throws TokenAlreadyExistException {

        TokenModel tokenModel=tokenRepository.findToken(user);
        if(tokenModel!=null)
            throw new TokenAlreadyExistException("You Already Logged in logout first");
        Date date=new Date();
        TokenModel token = TokenModel.builder()
                .user(user)
                .token(jwtToken)
                .craeted_date(date)
                .expiration_date(new Date(System.currentTimeMillis()+jwtService.accessTokenValidity))
                .token_type("BEARER")
                .build();
        tokenRepository.save(token);}

    }

