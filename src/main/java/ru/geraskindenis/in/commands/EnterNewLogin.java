package ru.geraskindenis.in.commands;

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
        return validation(Utils.INSTANCE.getString(clue));
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
