package ru.geraskindenis.services;

import ru.geraskindenis.entity.PersonalAccount;
import ru.geraskindenis.entity.User;

public interface PersonalAccountService {
    PersonalAccount getPersonalAccount(User user);

    void createPersonalAccount(User newUser);

    void update(PersonalAccount personalAccount);
}
