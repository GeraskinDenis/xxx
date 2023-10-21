package ru.geraskindenis.in.commands;

import ru.geraskindenis.services.impl.MainServiceImpl;

import java.math.BigDecimal;

public class ShowCurrentBalance implements Command<BigDecimal> {
    public static final ShowCurrentBalance INSTANCE = new ShowCurrentBalance();
    private final String name;
    private final String clue;
    private final MainServiceImpl walletService;

    private ShowCurrentBalance() {
        this.name = "Show current balance";
        this.clue = "Your current balance: ";
        this.walletService = MainServiceImpl.INSTANCE;
    }

    @Override
    public BigDecimal execute() {
        BigDecimal bigDecimal = walletService.getCurrentBalance();
        System.out.println(clue + bigDecimal);
        return bigDecimal;
    }

    @Override
    public String getName() {
        return name;
    }
}
