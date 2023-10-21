package ru.geraskindenis.services;

import ru.geraskindenis.entity.PersonalAccount;
import ru.geraskindenis.entity.Transaction;

import java.util.List;

public interface TransactionService {
    boolean acceptTransaction(Transaction transaction);

    List<Transaction> getAllByPersonalAccount(PersonalAccount personalAccount);
}
