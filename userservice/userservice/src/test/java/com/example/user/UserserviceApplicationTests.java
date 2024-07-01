package com.example.user;

import com.example.user.model.UserModel;
import com.example.user.request.LoginRequest;
import com.example.user.response.LoginRes;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.log.Log;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserserviceApplicationTests {


     /*  @Test
  public void testLogin(){

        String url="http://localhost:8080/api/auth/login";
        LoginRequest login =new LoginRequest("m.sherbiny2288@gmail.com","11");

        String body = (new ObjectMapper()).valueToTree(login).toString();
        RestTemplate restTemplate =new RestTemplate();
        ResponseEntity<LoginRes> response =restTemplate.postForEntity(url,body, LoginRes.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);


    }
*/
}
