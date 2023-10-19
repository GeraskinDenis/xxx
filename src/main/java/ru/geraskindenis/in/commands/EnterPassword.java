package ru.geraskindenis.in.commands;

public class EnterPassword implements Command<String> {
    public static final EnterPassword INSTANCE = new EnterPassword();
    private final String name;
    private final String clue;

    private EnterPassword() {
        this.name = "Enter your password";
        this.clue = "Enter your password:";
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
