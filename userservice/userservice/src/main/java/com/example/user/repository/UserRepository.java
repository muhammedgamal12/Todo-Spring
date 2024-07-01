package com.example.user.repository;

import com.example.user.model.UserModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Integer> {


    @Query("SELECT u from UserModel u WHERE u.email = ?1")
    UserModel findUserByEmail(String email);

    @Transactional
    @Modifying
    @Query("update UserModel u set u.password=?2 where u.email = ?1")
    void changePassword(String email,String password);

    @Transactional
    @Modifying
    @Query("update UserModel u set u.enabled=?2 where u.email = ?1")
    void enableUser(String email,boolean enabled);
}
