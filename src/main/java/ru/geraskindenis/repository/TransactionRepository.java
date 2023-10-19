package ru.geraskindenis.repository;

import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

public interface TransactionRepository<Key, Entity> {
    void save(Entity entity) throws SQLException;

    Optional<Entity> findByUuid(Key key) throws SQLException;

    Map<Key, Entity> findAllByPersonalAccountId(Long personalAccountId) throws SQLException;
}
