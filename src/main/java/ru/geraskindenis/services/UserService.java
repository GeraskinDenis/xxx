package ru.geraskindenis.services;

import ru.geraskindenis.domain.User;

import java.sql.SQLException;

public interface UserService {
    User registration(String newLogin, String newPassword);

    boolean authorization(String login, String password);

    boolean loggedIn();

    User getLoggedUser();

    void logout();

    User findByLogin(String login) throws SQLException;
}
