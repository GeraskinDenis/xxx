package ru.geraskindenis.services;

import ru.geraskindenis.entity.EventRecord;
import ru.geraskindenis.entity.User;

import java.util.List;

public interface UserEventService {
    void addLog(User user, String string);

    List<EventRecord> getLog(User user);
}
