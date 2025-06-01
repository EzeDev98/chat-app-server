package com.chat.application.repository;

import com.chat.application.model.ActiveSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActiveSessionRepository extends JpaRepository<ActiveSession, Long> {
    Optional<ActiveSession> findByToken(String token);
}
