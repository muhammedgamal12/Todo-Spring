package com.example.user.services;

import com.example.user.exception.token.TokenExpiredException;
import com.example.user.exception.user.UserNotFoundException;
import com.example.user.model.TokenModel;
import com.example.user.model.UserModel;
import com.example.user.repository.TokenRepository;
import com.example.user.repository.UserRepository;
import com.example.user.request.ChangePassword;
import com.example.user.response.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    JwtService jwtService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenRepository tokenRepository;
    public ResponseEntity<String> changePassword(String email, ChangePassword changePassword) throws UserNotFoundException {

        if (userRepository.findUserByEmail(email)==null){
            throw new UserNotFoundException("This email not right please enter a valid email");
        }
        else{
        if(!Objects.equals(changePassword.getPassword(),changePassword.getRepeatPassword())){
            return new ResponseEntity<>("Password don't match", HttpStatus.EXPECTATION_FAILED);
        }
        String new_password=passwordEncoder.encode(changePassword.getPassword());
        userRepository.changePassword(email,new_password);
        return ResponseEntity.ok("Password Changed");
    }
    }
    public ResponseEntity<String> deleteUser(String email) throws UserNotFoundException {
        if (userRepository.findUserByEmail(email)==null){
            throw new UserNotFoundException("This User already Not Exist");
        }
        UserModel user=userRepository.findUserByEmail(email);
        tokenRepository.deleteById(tokenRepository.findToken(user).getId());
        userRepository.deleteById(user.getId());
        return ResponseEntity.ok("User Deleted");
    }

    public UserModel searchUser(String email) throws UserNotFoundException {
        UserModel userModel=userRepository.findUserByEmail(email);
      if(userModel==null)
          throw new UserNotFoundException("This email not exist");
        return userModel;
    }

    public List<UserModel> searchAllUser(HttpServletRequest request,String email) throws UserNotFoundException, TokenExpiredException {
        if(jwtService.isTokenValid(request.getHeader("Authorization"),searchUser(email)))
          logout(request);

       List<UserModel> userModel=userRepository.findAll();
        if(userModel==null)
            throw new UserNotFoundException("no users");
        return userModel;
    }
    public ResponseEntity<String> logout(HttpServletRequest request) throws TokenExpiredException {

        String token=jwtService.getToken(request).substring(1);
        TokenModel tokenmodel=tokenRepository.findTokenModel(token);

        if(tokenmodel==null)
            throw new TokenExpiredException("You are not Active");

     int id =tokenRepository.findToken(userRepository.findUserByEmail(tokenmodel.getUser().
             getEmail())).getId();
     tokenRepository.deleteById(id);
     return ResponseEntity.ok("You logged out Successfully");
    }

    public UserResponse checkToken(HttpServletRequest request ) throws UserNotFoundException, TokenExpiredException {
        String token=request.getHeader("Authorization").substring("Bearer ".length());

        UserModel user =searchUser(jwtService.getClaims(request).getSubject());
        if(token==tokenRepository.findToken(user).getToken())
            throw  new TokenExpiredException("Your session finished please login again");

        if (user==null)
            throw new UserNotFoundException("this user not exist");

       if(jwtService.isTokenExpired(tokenRepository.findToken(user).getExpiration_date())){
           logout(request);
           throw new TokenExpiredException("Your session finished please login again");
       }
       UserResponse userResponse = UserResponse.builder()
                       .user_id(user.getId())
                               .email(user.getEmail()).build();

       return userResponse;

    }

}
