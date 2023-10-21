package ru.geraskindenis.in.commands;

import ru.geraskindenis.in.commands.exceptions.CommandException;
import ru.geraskindenis.utils.ScannerUtils;

public class AccountOperationsCommandPackage extends AbstractCommandPackage {
    public static final AccountOperationsCommandPackage INSTANCE = new AccountOperationsCommandPackage();

    private AccountOperationsCommandPackage() {
        super("Account operations");
    }

    @Override
    protected void initialization() {
        commands.add(ShowCurrentBalance.INSTANCE);
        commands.add(PerformDebitTransaction.INSTANCE);
        commands.add(PerformCreditTransaction.INSTANCE);
        commands.add(ShowTransactionHistory.INSTANCE);
        commands.add(Logout.INSTANCE);
    }

    @Override
    public Command<?> execute() {
        System.out.println(heading);
        for (int i = 0; i < commands.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, commands.get(i).getName());
        }
        int i = validator(ScannerUtils.INSTANCE.getInteger(clue));
        return commands.get(i);
    }

    private int validator(int i) {
        i -= 1;
        if (i >= commands.size()) {
            throw new CommandException("ERROR: There is no such number on the menu.");
        }
        return i;
    }
}
