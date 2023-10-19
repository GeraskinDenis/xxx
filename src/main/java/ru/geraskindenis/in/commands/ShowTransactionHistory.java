package ru.geraskindenis.in.commands;

import ru.geraskindenis.domain.Transaction;
import ru.geraskindenis.services.impl.MainServiceImpl;

import java.util.Map;
import java.util.UUID;

public class ShowTransactionHistory implements Command<Boolean> {
    public static final ShowTransactionHistory INSTANCE = new ShowTransactionHistory();
    private final String name;
    private final String heading;
    private final MainServiceImpl walletService;

    private ShowTransactionHistory() {
        this.name = "Show transaction history";
        this.heading = "\n>>> Your transaction history: <<<";
        this.walletService = MainServiceImpl.INSTANCE;
    }

    @Override
    public Boolean execute() {
        System.out.println(heading);
        Map<UUID, Transaction> transactionHistory = walletService.getTransactionHistory();
        transactionHistory.values().forEach(System.out::println);
        return true;
    }

    @Override
    public String getName() {
        return name;
    }
}
