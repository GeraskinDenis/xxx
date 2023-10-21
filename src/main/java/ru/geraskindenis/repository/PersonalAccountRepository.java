package ru.geraskindenis.repository;

import ru.geraskindenis.entity.PersonalAccount;

import java.sql.SQLException;
import java.util.Optional;

public interface PersonalAccountRepository {
    void save(PersonalAccount personalAccount) throws SQLException;

    void update(PersonalAccount personalAccount) throws SQLException;

    Optional<PersonalAccount> findById(Long aLong) throws SQLException;
}
