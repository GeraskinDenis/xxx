package ru.geraskindenis.services.impl;

import ru.geraskindenis.entity.PersonalAccount;
import ru.geraskindenis.entity.Transaction;
import ru.geraskindenis.entity.User;
import ru.geraskindenis.exceptions.MainServiceException;
import ru.geraskindenis.model.TransactionType;
import ru.geraskindenis.services.MainService;
import ru.geraskindenis.services.PersonalAccountService;
import ru.geraskindenis.services.TransactionService;
import ru.geraskindenis.services.UserService;
import ru.geraskindenis.utils.DataSource;
import ru.geraskindenis.utils.LiquibaseInitialization;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class MainServiceImpl implements MainService {
    public static final MainServiceImpl INSTANCE = new MainServiceImpl();
    private final UserService userService;
    private final UserEventServiceImpl userEventService;
    private final PersonalAccountService personalAccountService;
    private final TransactionService transactionService;

    public MainServiceImpl() {
        this.userService = UserServiceImpl.INSTANCE;
        this.userEventService = UserEventServiceImpl.INSTANCE;
        this.personalAccountService = PersonalAccountServiceImp.INSTANCE;
        this.transactionService = TransactionServiceImpl.INSTANCE;
        initialization();
    }

    private void initialization() {
        LiquibaseInitialization.initialization();
    }

    @Override
    public synchronized boolean acceptTransaction(UUID uuid, TransactionType transactionType, BigDecimal amount) {
        try (Connection connection = DataSource.INSTANCE.getConnection()) {
            connection.setAutoCommit(false);
            User loggedUser = userService.getLoggedUser();
            PersonalAccount personalAccount = personalAccountService.getPersonalAccount(loggedUser);
            Transaction transaction = new Transaction();
            transaction.setUuid(uuid);
            transaction.setPersonalAccountId(personalAccount.getId());
            transaction.setTransactionType(transactionType);
            transaction.setAmount(amount);
            transaction.setCreatedAt(LocalDateTime.now());
            transactionService.acceptTransaction(transaction);
            userEventService.addLog(loggedUser, String.format("%s transaction %saccepted. Amount %s "
                    , transactionType.toString(), transaction.isAccept() ? "" : "not ", amount));
            connection.commit();
            return transaction.isAccept();
        } catch (SQLException e) {
            throw new MainServiceException(e);
        }
    }

    @Override
    public BigDecimal getCurrentBalance() {
        User loggedUser = userService.getLoggedUser();
        userEventService.addLog(loggedUser, "Requested current balance");
        PersonalAccount personalAccount = personalAccountService.getPersonalAccount(loggedUser);
        return personalAccount.getBalance();
    }

    @Override
    public List<Transaction> getTransactionHistory() {
        User loggedUser = userService.getLoggedUser();
        PersonalAccount personalAccount = personalAccountService.getPersonalAccount(loggedUser);
        userEventService.addLog(loggedUser, "Transaction log requested.");
        return transactionService.getAllByPersonalAccount(personalAccount);
    }

    @Override
    public Boolean loggedIn() {
        return userService.loggedIn();
    }

    @Override
    public synchronized User registration(String newLogin, String newPassword) {
        try (Connection connection = DataSource.INSTANCE.getConnection()) {
            connection.setAutoCommit(false);
            User newUser = userService.registration(newLogin, newPassword);
            personalAccountService.createPersonalAccount(newUser);
            connection.commit();
            return newUser;
        } catch (SQLException e) {
            throw new MainServiceException(e);
        }
    }

    @Override
    public Boolean authorization(String login, String password) {
        return userService.authorization(login, password);
    }

    @Override
    public void logout() {
        userEventService.addLog(userService.getLoggedUser(), "logout");
        userService.logout();
    }

    @Override
    public void shutDown() {
        DataSource.INSTANCE.close();
    }

}
