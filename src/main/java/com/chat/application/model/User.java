package com.chat.application.model;

import com.chat.application.enums.Roles;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "first_name")
    @JsonProperty("first_name")
    private String firstname;

    @Column(name = "last_name")
    @JsonProperty("last_name")
    private String lastname;

    @Column(name = "user_name")
    @JsonProperty("username")
    private String username;

    @Column(name = "email")
    @JsonProperty("email")
    private String email;

    @Column(name = "phone_number")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @Column(name = "password")
    @JsonProperty("password")
    private String password;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @Column(name = "status")
    private boolean isEnabled = true;

    @Column(name = "time")
    private LocalDateTime createdAt;

    public User() {
    }

    public User(String firstname, String lastname, String username, String email, String phoneNumber, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
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
        return isEnabled;
    }
}
