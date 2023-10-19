package ru.geraskindenis.services;

import ru.geraskindenis.domain.PersonalAccount;
import ru.geraskindenis.domain.Transaction;

import java.util.Map;
import java.util.UUID;

public interface TransactionService {
    boolean acceptTransaction(Transaction transaction);

    Map<UUID, Transaction> getAllByPersonalAccount(PersonalAccount personalAccount);
}
