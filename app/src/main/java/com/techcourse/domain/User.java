package com.techcourse.domain;

import com.techcourse.exception.UnauthorizedException;

public class User {

    private final Long id;
    private final String account;
    private final String password;
    private final String email;

    public User(String account, String password, String email) {
        this.id = null;
        this.account = account;
        this.password = password;
        this.email = email;
    }

    public User(Long id, String account, String password, String email) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.email = email;
    }

    public static User withId(Long id, User user) {
        return new User(id, user.account, user.password, user.email);
    }

    public void checkPassword(String password) {
        if (!this.password.equals(password)) {
            throw new UnauthorizedException();
        }
    }

    public String getAccount() {
        return account;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
