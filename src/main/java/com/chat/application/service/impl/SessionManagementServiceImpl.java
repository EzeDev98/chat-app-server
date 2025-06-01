package com.chat.application.service.impl;

import com.chat.application.model.ActiveSession;
import com.chat.application.model.User;
import com.chat.application.repository.ActiveSessionRepository;
import com.chat.application.repository.UserRepository;
import com.chat.application.service.SessionManagementService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SessionManagementServiceImpl implements SessionManagementService {

    private final ActiveSessionRepository sessionRepository;
    private final UserRepository userRepository;

    public SessionManagementServiceImpl(ActiveSessionRepository sessionRepository, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ActiveSession createSession(User user, String token) {
        ActiveSession session = createActiveSession(user, token);
        return sessionRepository.save(session);
    }

    @Override
    public void invalidateSessionByUserToken(User user) {
        invalidateSession(user);
    }

    private ActiveSession createActiveSession(User user, String token) {
        ActiveSession session = new ActiveSession();
        session.setUser(user);
        session.setToken(token);
        session.setCreatedAt(LocalDateTime.now());
        return session;
    }

    private void invalidateSession(User user) {

        if (user.getActiveSession() != null && user.getToken() != null) {
            findAndDeleteSessionByToken(user.getToken());
            user.setActiveSession(null);
            user.setToken(null);
            user.setLoggedIn(false);
            userRepository.save(user);
        }
    }


    private void findAndDeleteSessionByToken(String token) {
        sessionRepository.findByToken(token)
                .ifPresent(sessionRepository::delete);
    }

}
