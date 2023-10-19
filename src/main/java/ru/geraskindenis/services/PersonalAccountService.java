package ru.geraskindenis.services;

import ru.geraskindenis.domain.PersonalAccount;
import ru.geraskindenis.domain.User;

public interface PersonalAccountService {
    PersonalAccount getPersonalAccount(User user);

    void createPersonalAccount(User newUser);

    void update(PersonalAccount personalAccount);
}
