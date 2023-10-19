package ru.geraskindenis.services.impl;

import ru.geraskindenis.domain.EventRecord;
import ru.geraskindenis.domain.User;
import ru.geraskindenis.exceptions.UserEventServiceException;
import ru.geraskindenis.repository.UserEventLogRepository;
import ru.geraskindenis.repository.impl.UserEventLogRepositoryImpl;
import ru.geraskindenis.services.UserEventService;

import java.sql.SQLException;
import java.util.List;

public class UserEventServiceImpl implements UserEventService<User, String, List<EventRecord>> {
    public static final UserEventServiceImpl INSTANCE = new UserEventServiceImpl();
    private final UserEventLogRepository<Long, EventRecord, List<EventRecord>> repository;

    private UserEventServiceImpl() {
        repository = UserEventLogRepositoryImpl.INSTANCE;
    }

    @Override
    public void addLog(User user, String message) {
        EventRecord eventRecord = EventRecord.of(user, message);
        try {
            repository.save(eventRecord);
        } catch (SQLException e) {
            throw new UserEventServiceException(e);
        }
    }

    @Override
    public List<EventRecord> getLog(User user) {
        try {
            return repository.findAllByUserId(user.getId());
        } catch (SQLException e) {
            throw new UserEventServiceException(e);
        }
    }
}
