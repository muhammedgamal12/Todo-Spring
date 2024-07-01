package com.example.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "token")
public class TokenModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String token;

    @CreatedDate
    private java.util.Date craeted_date;

    private Date expiration_date;

    private String token_type = "BEARER";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public UserModel user;




}
