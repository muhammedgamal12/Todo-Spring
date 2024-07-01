package com.example.user.repository;

import com.example.user.model.TokenModel;
import com.example.user.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<TokenModel, Integer> {


    @Query("SELECT t from TokenModel t WHERE t.user= ?1")
    TokenModel findToken(UserModel user);

    @Query("SELECT t from TokenModel t WHERE t.token= ?1")
    TokenModel findTokenModel(String token);

}
