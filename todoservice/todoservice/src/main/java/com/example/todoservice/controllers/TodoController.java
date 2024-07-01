package com.example.todoservice.controllers;

import com.example.todoservice.dto.UserResponse;
import com.example.todoservice.exception.TokenException;
import com.example.todoservice.models.Todo;
import com.example.todoservice.services.TodoServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TodoController {

    @Autowired
    TodoServices todoServices;

    @PostMapping("/addTodo")
    public String addTodo(@Valid HttpServletRequest request, @RequestBody Todo todo) throws TokenException, IOException {

        return todoServices.addTodo(request,todo);
    }

    @DeleteMapping("/deleteTodo/{id}")
    public String deleteTodo(HttpServletRequest request, @PathVariable int id) throws IOException, TokenException {

        return todoServices.deleteTodo(request,id);
    }

    @PutMapping("/updateTodo/{id}")
    public String updateTodo(HttpServletRequest request, @PathVariable int id,@RequestBody Todo todo) throws TokenException, IOException {

        return todoServices.updateTodo(request,id,todo);
    }

    @GetMapping("/searchTodo/{id}")
    public Optional<Todo> searchTodo(HttpServletRequest request, @PathVariable int id)  {
        return todoServices.searchTodo(request,id);
  }

//    @GetMapping("/donetodo")
//    public Optional<Todo> findDone(HttpServletRequest request)  {
//        return todoServices;
//    }
//
//    @GetMapping("/notdonetodo")
//    public Optional<Todo> findNotDone(HttpServletRequest request)  {
//        return todoServices.;
//    }


    @GetMapping("/token")
    public ResponseEntity<UserResponse> checkToken(HttpServletRequest request) throws IOException {

        return todoServices.checkToken(request);
    }

}
