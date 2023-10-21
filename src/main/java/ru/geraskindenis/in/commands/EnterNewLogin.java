package ru.geraskindenis.in.commands;

import ru.geraskindenis.utils.ScannerUtils;

public class EnterNewLogin implements Command<String> {
    public static final EnterNewLogin INSTANCE = new EnterNewLogin();
    private final String name;
    private final String clue;

    private EnterNewLogin() {
        this.name = "Enter new login";
        this.clue = "Enter new login:";
    }

    @Override
    public String execute() {
        return validation(ScannerUtils.INSTANCE.getString(clue));
    }

    private String validation(String s) {
        // TODO
        return s;
    }

    @Override
    public String getName() {
        return name;
    }
}
