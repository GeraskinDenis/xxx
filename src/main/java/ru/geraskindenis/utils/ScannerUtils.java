package ru.geraskindenis.utils;

import ru.geraskindenis.in.commands.exceptions.CommandException;

import java.math.BigDecimal;
import java.util.Scanner;

public class ScannerUtils {
    public static final ScannerUtils INSTANCE = new ScannerUtils();
    private final Scanner scanner;

    public ScannerUtils() {
        scanner = new Scanner(System.in);
    }

    public String getString(String clue) {
        System.out.print("\n" + clue);
        try {
            return scanner.next();
        } catch (RuntimeException e) {
            throw new CommandException(e);
        }
    }

    public int getInteger(String clue) {
        System.out.print("\n" + clue);
        try {
            return scanner.nextInt();
        } catch (RuntimeException e) {
            throw new CommandException(e);
        }
    }

    public BigDecimal getBigDecimal(String clue) {
        System.out.print("\n" + clue);
        try {
            return scanner.nextBigDecimal();
        } catch (RuntimeException e) {
            throw new CommandException(e);
        }
    }
}
