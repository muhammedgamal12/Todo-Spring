package com.example.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "otp")
public class OtpModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    private int otp;

    private Date expiration_date;

    @OneToOne
    @JoinColumn(name = "user_id")
    public UserModel user;
}
