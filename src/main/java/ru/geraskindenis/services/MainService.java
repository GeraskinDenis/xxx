package ru.geraskindenis.services;

import ru.geraskindenis.domain.Transaction;
import ru.geraskindenis.domain.TransactionTypes;
import ru.geraskindenis.domain.User;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

/**
 * Main class
 */
public interface MainService {
    boolean acceptTransaction(UUID uuid, TransactionTypes transactionTypes, BigDecimal amount);

    /**
     * This method returns current balance.
     *
     * @return returns {@code BigDecimal}
     */
    BigDecimal getCurrentBalance();

    Map<UUID, Transaction> getTransactionHistory();

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
