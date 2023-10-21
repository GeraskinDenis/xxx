package ru.geraskindenis.model;

import ru.geraskindenis.entity.User;

public class SystemUser extends User {
    public static final SystemUser INSTANCE = new SystemUser();

    private SystemUser() {
        setLogin("SYSTEM");
        setPassword("NoPassword");
        setRole(Role.SYSTEM);
        setId(1L);
    }
}
