package com.chat.application.service;
import com.chat.application.model.ActiveSession;
import com.chat.application.model.User;

public interface SessionManagementService {
    ActiveSession createSession(User user, String token);
    void invalidateSessionByUserToken(User user);

}
