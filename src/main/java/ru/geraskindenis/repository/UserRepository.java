package ru.geraskindenis.repository;

import ru.geraskindenis.entity.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserRepository {
    void save(User user) throws SQLException;

    void update(User user) throws SQLException;

    Optional<User> findById(Long aLong) throws SQLException;

    Optional<ru.geraskindenis.entity.User> findByLogin(String login) throws SQLException;

    void deleteByLogin(String login) throws SQLException;
}
