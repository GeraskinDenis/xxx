package ru.geraskindenis.repository.impl;

import ru.geraskindenis.entity.EventRecord;
import ru.geraskindenis.repository.UserEventLogRepository;
import ru.geraskindenis.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserEventLogRepositoryImpl implements UserEventLogRepository {
    public static final UserEventLogRepositoryImpl INSTANCE = new UserEventLogRepositoryImpl();

    @Override
    public void save(EventRecord eventRecord) throws SQLException {
        String query = "INSERT INTO user_event_log (time, user_id, message) VALUES (?, ?, ?)";
        Connection connection = DataSource.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setTimestamp(1, Timestamp.valueOf(eventRecord.getTime()));
        preparedStatement.setLong(2, eventRecord.getUserId());
        preparedStatement.setString(3, eventRecord.getMessage());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            eventRecord.setId(resultSet.getObject("id", Long.class));
        }
        resultSet.close();
        preparedStatement.close();
        DataSource.INSTANCE.closeConnection();
    }

    @Override
    public List<EventRecord> findAllByUserId(Long userId) throws SQLException {
        String query = "SELECT * FROM user_event_log WHERE user_id = ?";
        Connection connection = DataSource.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<EventRecord> records = new ArrayList<>();
        while (resultSet.next()) {
            EventRecord record = buildEventRecord(resultSet);
            records.add(record);
        }
        resultSet.close();
        preparedStatement.close();
        DataSource.INSTANCE.closeConnection();
        return records;
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
