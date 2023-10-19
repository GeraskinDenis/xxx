package ru.geraskindenis.repository;

import ru.geraskindenis.domain.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserRepository<Key, Entity> {
    void save(Entity entity) throws SQLException;

    void update(Entity entity) throws SQLException;

    Optional<Entity> findById(Key key) throws SQLException;

    Optional<User> findByLogin(String login) throws SQLException;
}
