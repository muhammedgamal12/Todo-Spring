package com.example.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
@Table(name = "user")
public class UserModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    @NotEmpty(message = "title is mandatory")
    @NotNull
    private String email;
    @NotNull
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$")
    @NotEmpty(message = "title is mandatory")
    private String password;
    private boolean enabled;
    @Enumerated(EnumType.STRING)
    private Role role;


    @OneToOne(mappedBy = "user")
    OtpModel otpModel;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
