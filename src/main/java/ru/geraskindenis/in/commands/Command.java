package ru.geraskindenis.in.commands;

public interface Command<T> {
    T execute();

    String getName();
}
