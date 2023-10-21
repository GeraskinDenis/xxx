package ru.geraskindenis.services;

import ru.geraskindenis.entity.Transaction;
import ru.geraskindenis.entity.User;
import ru.geraskindenis.model.TransactionType;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Main class
 */
public interface MainService {
    boolean acceptTransaction(UUID uuid, TransactionType transactionType, BigDecimal amount);

    /**
     * This method returns current balance.
     *
     * @return returns {@code BigDecimal}
     */
    BigDecimal getCurrentBalance();

    List<Transaction> getTransactionHistory();

    /**
     * This method returns {@code true} if the user is a authorized.
     *
     * @return {@code boolean}
     */
    Boolean loggedIn();

    /**
     * This method performs new user registration.
     *
     * @param newLogin    new login
     * @param newPassword new password
     * @return Returns {@code true}
     */
    User registration(String newLogin, String newPassword);

    /**
     * This method authenticates the incoming user.
     *
     * @param login    login {@code String}
     * @param password password {@see String}
     * @return Returns {@code true} if authorization was successful
     */
    Boolean authorization(String login, String password);

    void logout();

    void shutDown();
}
