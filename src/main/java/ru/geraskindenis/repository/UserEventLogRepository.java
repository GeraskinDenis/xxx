package ru.geraskindenis.repository;

import java.sql.SQLException;

public interface UserEventLogRepository<Key, Entity, Return> {
    void save(Entity entity) throws SQLException;

    Return findAllByUserId(Key key) throws SQLException;
}
