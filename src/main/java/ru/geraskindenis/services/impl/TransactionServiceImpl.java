package ru.geraskindenis.services.impl;

import ru.geraskindenis.entity.PersonalAccount;
import ru.geraskindenis.entity.Transaction;
import ru.geraskindenis.entity.User;
import ru.geraskindenis.exceptions.TransactionServiceException;
import ru.geraskindenis.model.TransactionType;
import ru.geraskindenis.repository.TransactionRepository;
import ru.geraskindenis.repository.impl.TransactionRepositoryImpl;
import ru.geraskindenis.services.PersonalAccountService;
import ru.geraskindenis.services.TransactionService;
import ru.geraskindenis.services.UserService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {
    public static final TransactionServiceImpl INSTANCE = new TransactionServiceImpl();
    private final TransactionRepository transactionRepository;
    private final PersonalAccountService personalAccountService;
    private final UserService userService;

    private TransactionServiceImpl() {
        this.transactionRepository = TransactionRepositoryImpl.INSTANCE;
        this.personalAccountService = PersonalAccountServiceImp.INSTANCE;
        this.userService = UserServiceImpl.INSTANCE;
    }

    @Override
    public boolean acceptTransaction(Transaction transaction) {
        if (transaction.getTransactionType() == TransactionType.DEBIT) {
            acceptDebitTransaction(transaction);
        } else {
            acceptCreditTransaction(transaction);
        }
        return transaction.isAccept();
    }

    @Override
    public List<Transaction> getAllByPersonalAccount(PersonalAccount personalAccount) {
        try {
            return transactionRepository.findAllByPersonalAccountId(personalAccount.getId());
        } catch (SQLException e) {
            throw new TransactionServiceException(e);
        }
    }

    private void acceptDebitTransaction(Transaction transaction) {
        try {
            PersonalAccount personalAccount = getPersonalAccount(userService.getLoggedUser());
            BigDecimal newBalance = personalAccount.getBalance().subtract(transaction.getAmount());
            if (newBalance.signum() == -1) {
                transactionRepository.save(transaction);
                throw new TransactionServiceException("ERROR: There are not enough funds in the account.");
            }
            transaction.setAccept(true);
            transactionRepository.save(transaction);
            personalAccount.setBalance(newBalance);
            personalAccountService.update(personalAccount);
        } catch (SQLException e) {
            throw new TransactionServiceException(e);
        }
    }

    private void acceptCreditTransaction(Transaction transaction) {
        try {
            PersonalAccount personalAccount = getPersonalAccount(userService.getLoggedUser());
            BigDecimal newBalance = personalAccount.getBalance().add(transaction.getAmount());
            transaction.setAccept(true);
            transactionRepository.save(transaction);
            personalAccount.setBalance(newBalance);
            personalAccountService.update(personalAccount);
        } catch (SQLException e) {
            throw new TransactionServiceException(e);
        }
    }

    private PersonalAccount getPersonalAccount(User user) {
        return personalAccountService.getPersonalAccount(user);
    }
}
