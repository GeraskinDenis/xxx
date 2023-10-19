package ru.geraskindenis.services.impl;

import ru.geraskindenis.domain.PersonalAccount;
import ru.geraskindenis.domain.User;
import ru.geraskindenis.exceptions.PersonalAccountServiceException;
import ru.geraskindenis.repository.PersonalAccountRepository;
import ru.geraskindenis.repository.impl.PersonalAccountRepositoryImpl;
import ru.geraskindenis.services.PersonalAccountService;

import java.sql.SQLException;

public class PersonalAccountServiceImp implements PersonalAccountService {
    public static final PersonalAccountServiceImp INSTANCE = new PersonalAccountServiceImp();
    private final PersonalAccountRepository<Long, PersonalAccount> personalAccountRepository;

    private PersonalAccountServiceImp() {
        this.personalAccountRepository = PersonalAccountRepositoryImpl.INSTANCE;
    }

    @Override
    public PersonalAccount getPersonalAccount(User user) {
        try {
            return personalAccountRepository.findById(user.getId()).orElseThrow();
        } catch (SQLException e) {
            throw new PersonalAccountServiceException(e);
        }
    }

    @Override
    public void createPersonalAccount(User user) {
        PersonalAccount personalAccount = new PersonalAccount(user);
        try {
            personalAccountRepository.save(personalAccount);
        } catch (SQLException e) {
            throw new PersonalAccountServiceException(e);
        }
    }

    @Override
    public void update(PersonalAccount personalAccount) {
        try {
            personalAccountRepository.update(personalAccount);
        } catch (SQLException e) {
            throw new PersonalAccountServiceException(e);
        }
    }
}
