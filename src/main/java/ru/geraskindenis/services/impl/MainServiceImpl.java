package ru.geraskindenis.services.impl;

import liquibase.exception.LiquibaseException;
import ru.geraskindenis.domain.PersonalAccount;
import ru.geraskindenis.domain.Transaction;
import ru.geraskindenis.domain.TransactionTypes;
import ru.geraskindenis.domain.User;
import ru.geraskindenis.exceptions.MainServiceException;
import ru.geraskindenis.services.*;
import ru.geraskindenis.utils.DataSource;
import ru.geraskindenis.utils.LiquibaseInitialization;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;
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
        try {
            LiquibaseInitialization.initialization();
            SystemUser.INSTANCE.setId(userService.findByLogin(SystemUser.INSTANCE.getLogin()).getId());
        } catch (SQLException | LiquibaseException e) {
            throw new MainServiceException(e);
        }
    }

    @Override
    public synchronized boolean acceptTransaction(UUID uuid, TransactionTypes transactionTypes, BigDecimal amount) {
        User loggedUser = userService.getLoggedUser();
        PersonalAccount personalAccount = personalAccountService.getPersonalAccount(loggedUser);
        Transaction transaction = new Transaction();
        transaction.setUuid(uuid);
        transaction.setPersonalAccountId(personalAccount.getId());
        transaction.setTransactionType(transactionTypes);
        transaction.setAmount(amount);
        transaction.setCreatedAt(LocalDateTime.now());
        transactionService.acceptTransaction(transaction);
        userEventService.addLog(loggedUser, String.format("%s transaction %saccepted. Amount %s "
                , transactionTypes.toString(), transaction.isAccept() ? "" : "not ", amount));
        return transaction.isAccept();
    }

    @Override
    public BigDecimal getCurrentBalance() {
        User loggedUser = userService.getLoggedUser();
        userEventService.addLog(loggedUser, "Requested current balance");
        PersonalAccount personalAccount = personalAccountService.getPersonalAccount(loggedUser);
        return personalAccount.getBalance();
    }

    @Override
    public Map<UUID, Transaction> getTransactionHistory() {
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
    public User registration(String newLogin, String newPassword) {
        User newUser = userService.registration(newLogin, newPassword);
        personalAccountService.createPersonalAccount(newUser);
        return newUser;
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
