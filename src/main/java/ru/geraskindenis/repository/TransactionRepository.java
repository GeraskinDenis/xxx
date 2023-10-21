package ru.geraskindenis.repository;

import ru.geraskindenis.entity.Transaction;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository {
    void save(Transaction transaction) throws SQLException;

    Optional<Transaction> findByUuid(UUID UUID) throws SQLException;

    List<Transaction> findAllByPersonalAccountId(Long personalAccountId) throws SQLException;
}
