package ru.geraskindenis.in.commands;

import ru.geraskindenis.model.TransactionType;
import ru.geraskindenis.services.impl.MainServiceImpl;
import ru.geraskindenis.utils.ScannerUtils;

import java.math.BigDecimal;
import java.util.UUID;

public class PerformCreditTransaction implements Command<Boolean> {
    public static final Command<Boolean> INSTANCE = new PerformCreditTransaction();
    private final String name;
    private final String heading;
    private final String clue;
    private final MainServiceImpl walletService;

    private PerformCreditTransaction() {
        this.name = "Perform a credit transaction";
        this.heading = String.format(">>> %s <<<", name.toUpperCase());
        this.clue = "Enter amount: ";
        walletService = MainServiceImpl.INSTANCE;
    }

    @Override
    public Boolean execute() {
        System.out.println(heading);
        BigDecimal amount = ScannerUtils.INSTANCE.getBigDecimal(clue);
        UUID uuidTransaction = UUID.randomUUID();
        boolean result = walletService.acceptTransaction(uuidTransaction, TransactionType.CREDIT, amount);
        System.out.format("Transaction UUID: %s Amount: %s %s"
                , uuidTransaction
                , amount.toString()
                , result ? "Accept" : "Not accept");
        return true;
    }

    @Override
    public String getName() {
        return name;
    }
}
