package ru.geraskindenis.repository;

import java.sql.SQLException;
import java.util.Optional;

public interface PersonalAccountRepository<Key, Entity> {
    void save(Entity entity) throws SQLException;

    void update(Entity entity) throws SQLException;

    Optional<Entity> findById(Key key) throws SQLException;
}
