package ru.geraskindenis.in.commands;

public class EnterLogin implements Command<String> {
    public static final EnterLogin INSTANCE = new EnterLogin();
    private final String name;
    private final String clue;

    private EnterLogin() {
        this.name = "Enter your login";
        this.clue = "Enter your login:";
    }

    @Override
    public String execute() {
        return validator(Utils.INSTANCE.getString(clue));
    }

    private String validator(String s) {
        // TODO
        return s;
    }

    @Override
    public String getName() {
        return name;
    }
}
