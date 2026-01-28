package com.example.user.repository;

import com.example.user.model.OtpModel;
import com.example.user.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OtpModel,Integer> {

      @Query("SELECT o from OtpModel o WHERE o.otp = ?1 and o.user= ?2")
       OtpModel findOtp(int otp, UserModel userModel);

    @Query("SELECT o from OtpModel o WHERE o.user= ?1")
    OtpModel findOtpByUser( UserModel userModel);


}
