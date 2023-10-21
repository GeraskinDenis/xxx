package ru.geraskindenis.repository;

import ru.geraskindenis.entity.EventRecord;

import java.sql.SQLException;
import java.util.List;

public interface UserEventLogRepository {
    void save(EventRecord eventRecord) throws SQLException;

    List<EventRecord> findAllByUserId(Long aLong) throws SQLException;
}
