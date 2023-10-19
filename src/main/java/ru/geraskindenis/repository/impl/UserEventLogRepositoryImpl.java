package ru.geraskindenis.repository.impl;

import ru.geraskindenis.domain.EventRecord;
import ru.geraskindenis.repository.UserEventLogRepository;
import ru.geraskindenis.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserEventLogRepositoryImpl implements UserEventLogRepository<Long, EventRecord, List<EventRecord>> {
    public static final UserEventLogRepositoryImpl INSTANCE = new UserEventLogRepositoryImpl();

    @Override
    public void save(EventRecord eventRecord) throws SQLException {
        String query = "INSERT INTO user_event_log (time, user_id, message) VALUES (?, ?, ?)";
        try (Connection connection = DataSource.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(eventRecord.getTime()));
            preparedStatement.setLong(2, eventRecord.getUserId());
            preparedStatement.setString(3, eventRecord.getMessage());
            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();
            if (keys.next()) {
                eventRecord.setId(keys.getObject("id", Long.class));
            }
        }
    }

    @Override
    public List<EventRecord> findAllByUserId(Long userId) throws SQLException {
        String query = "SELECT * FROM user_event_log WHERE user_id = ?";
        try (Connection connection = DataSource.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<EventRecord> records = new ArrayList<>();
            while (resultSet.next()) {
                EventRecord record = buildEventRecord(resultSet);
                records.add(record);
            }
            return records;
        }
    }

    private EventRecord buildEventRecord(ResultSet resultSet) throws SQLException {
        EventRecord result = new EventRecord();
        result.setId(resultSet.getLong("id"));
        result.setTime(resultSet.getTimestamp("time").toLocalDateTime());
        result.setUserId(resultSet.getLong("user_id"));
        result.setMessage(resultSet.getString("message"));
        return result;
    }
}
