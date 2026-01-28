package com.example.todoservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.PublicKey;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    @Size(min = 3,message = "You need to enter at least 3 characters")
    @NotNull(message = "is required")
    @NotEmpty(message = "title is mandatory")
    private String title;

    private int user_id;
    @OneToOne(cascade = CascadeType.ALL)
    public ItemDetails itemDetails;


}
