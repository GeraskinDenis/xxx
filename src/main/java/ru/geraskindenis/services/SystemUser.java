package ru.geraskindenis.services;

import ru.geraskindenis.domain.User;
import ru.geraskindenis.in.commands.Role;

public class SystemUser extends User {
    public static final SystemUser INSTANCE = new SystemUser();

    private SystemUser() {
        setLogin("SYSTEM");
        setPassword("NoPassword");
        setRole(Role.SYSTEM);
    }
}
