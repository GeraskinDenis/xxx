package ru.geraskindenis.services;

public interface UserEventService<Key, Entity, Return> {
    void addLog(Key key, Entity entity);

    Return getLog(Key key);
}
