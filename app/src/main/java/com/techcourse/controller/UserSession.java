package com.techcourse.controller;

import com.techcourse.domain.User;
import com.techcourse.exception.UnauthorizedException;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class UserSession {

    public static final String SESSION_KEY = "user";

    public static boolean isLoggedIn(HttpSession session) {
        return getUserFrom(session).isPresent();
    }

    public static User getUser(HttpSession session) {
        return getUserFrom(session).orElseThrow(UnauthorizedException::new);
    }

    private static Optional<User> getUserFrom(HttpSession session) {
        final User user = (User) session.getAttribute(SESSION_KEY);
        return Optional.ofNullable(user);
    }

    private UserSession() {}
}
