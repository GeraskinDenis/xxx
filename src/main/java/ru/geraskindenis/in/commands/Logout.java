package ru.geraskindenis.in.commands;

public class Logout implements Command<Boolean> {
    public static final Logout INSTANCE = new Logout();
    private final String name;
    private final String clue;

    private Logout() {
        this.name = "Logout";
        this.clue = "Logout Y/N?:";
    }

    @Override
    public Boolean execute() {
        String s = validation(Utils.INSTANCE.getString(clue));
        return s.equals("Y");
    }

    private String validation(String s) {
        // TODO
        return s.toUpperCase();
    }

    @Override
    public String getName() {
        return name;
    }
}
