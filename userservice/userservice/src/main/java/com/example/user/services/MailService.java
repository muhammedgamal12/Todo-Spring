package com.example.user.services;


import com.example.user.exception.user.UserNotFoundException;
import com.example.user.repository.UserRepository;
import com.example.user.request.MailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    UserRepository userRepository;
    public void sendEmail(MailRequest mail) throws UserNotFoundException {
        if (userRepository.findUserByEmail(mail.getTo())==null){
            throw new UserNotFoundException("This User Not Exist Please Register First");
        }
        else{
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail.getTo());
        message.setFrom("m.gamal2288@gmail.com");
        message.setSubject(mail.getSubject());
        message.setText(mail.getBody());

        emailSender.send(message);


    }
    }
}
