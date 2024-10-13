package com.chat.application.repository;

import com.chat.application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findByUsernameContainingIgnoreCaseOrFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCase(String username, String firstname, String lastname);

    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByEmail(String email);

}
