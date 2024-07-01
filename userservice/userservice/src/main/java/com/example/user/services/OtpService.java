package com.example.user.services;

import com.example.user.exception.otp.OtpNotFoundException;
import com.example.user.exception.user.UserNotFoundException;
import com.example.user.model.OtpModel;
import com.example.user.model.UserModel;
import com.example.user.repository.OtpRepository;
import com.example.user.repository.UserRepository;
import com.example.user.request.ChangePassword;
import com.example.user.request.MailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    OtpRepository otpRepository;

    @Autowired
    MailService mailService;


    public ResponseEntity<String> sendOtp(String email) throws UserNotFoundException {

        OtpModel check_existing_otp =otpRepository.findOtpByUser(userRepository.findUserByEmail(email));
        if(check_existing_otp!=null)
            otpRepository.deleteById(check_existing_otp.getId());
        if (userRepository.findUserByEmail(email) == null)
            throw new UserNotFoundException("Please enter a valid email");

            UserModel user = userRepository.findUserByEmail(email);

            String otp = generateOtp().toString();
            MailRequest mailRequest = MailRequest.builder().
                    to(email).
                    subject("your Otp is")
                    .body(otp).
                    build();

            OtpModel otpModel = OtpModel.builder().
                    otp(Integer.parseInt(otp)).
                    expiration_date(new Date(System.currentTimeMillis() + 70 * 70 * 1000))
                    .user(user).
                    build();

            otpRepository.save(otpModel);
            mailService.sendEmail(mailRequest);

            return ResponseEntity.ok("Otp sent Successfully");

    }
   public ResponseEntity<String> checkOtp(int otp,String email) throws OtpNotFoundException {


            UserModel user=userRepository.findUserByEmail(email);
            OtpModel otpmodel=  otpRepository.findOtp(otp,user);

       if (otpmodel==null)
           throw new OtpNotFoundException("Please enter the Otp that you have received in your email");

                  if (otpmodel.getExpiration_date().before(Date.from(Instant.now())));
                  otpRepository.deleteById(otpmodel.getId());

                  userRepository.enableUser(email,true);
        return ResponseEntity.ok("Otp Checked Successfully");
    }


    private Integer generateOtp(){
        Random random =new Random();
        return random.nextInt(100000,999999);
    }
}
