package com.example.todoservice.services;

import com.example.todoservice.dto.UserResponse;
import com.example.todoservice.exception.TokenException;
import com.example.todoservice.models.Done;
import com.example.todoservice.models.ItemDetails;
import com.example.todoservice.models.Priority;
import com.example.todoservice.models.Todo;
import com.example.todoservice.repositories.DetailsRepository;
import com.example.todoservice.repositories.TodoRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Service
public class TodoServices {

    @Autowired
    TodoRepository todoRepository;
    @Autowired
    DetailsRepository detailsRepository;
   public String addTodo(HttpServletRequest request,Todo todo) throws TokenException, IOException {

       if(checkToken(request)!=null){
       ItemDetails itemDetails = ItemDetails.builder().
               description(todo.itemDetails.getDescription()).
               created_at(new Date(System.currentTimeMillis())).
               priority(Priority.High).
               done(Done.NotDone)
               .build();

       Todo todoModel= Todo.builder().
               title(todo.getTitle()).
               user_id(checkToken(request).getBody().getUser_id()).
               itemDetails(itemDetails).
               build();

      todoRepository.save(todoModel);}
       else throw new TokenException("Token not Valid");
      return "Todo added Successfully";

   }

   public String deleteTodo(HttpServletRequest request,int id) throws IOException, TokenException {

       if(checkToken(request)!=null)
           todoRepository.deleteById(id);
       else throw new TokenException("Token not Valid");
       return  "deleted successfully";
   }

    public String updateTodo(HttpServletRequest request, int id, Todo todo) throws TokenException, IOException {

        if(checkToken(request)!=null) {
           Todo t =todoRepository.findById(id).get();
           t.setTitle(todo.getTitle());
           t.setItemDetails(todo.getItemDetails());
           todoRepository.save(t);
        }
        else throw new TokenException("Token not Valid");
        return "Updated Successfully";
    }



  public Optional<Todo> searchTodo(HttpServletRequest request, int id) {
       Optional<Todo> todo=todoRepository.findById(id);
       return todo;

  }




   public ResponseEntity<UserResponse> checkToken(HttpServletRequest request) throws IOException {

       HttpHeaders headers = new HttpHeaders();
       headers.setContentType(MediaType.APPLICATION_JSON);
       headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
       headers.add("Authorization" , request.getHeader("Authorization"));


       HttpEntity<Map<String, Object>> entity = new HttpEntity<>(headers);

       String url="http://localhost:8080/api/checkToken";
       RestTemplate restTemplate =new RestTemplate();
       ResponseEntity<UserResponse> tokenResponse = restTemplate.exchange(url , HttpMethod.GET,entity,UserResponse.class);

           return tokenResponse;
   }




}
