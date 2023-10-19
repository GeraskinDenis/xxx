package ru.geraskindenis.in.commands;

public class Exit implements Command<Boolean> {
    public static final Exit INSTANCE = new Exit();

    private final String name;
    private final String clue;

    private Exit() {
        this.name = "Exit";
        this.clue = "Shut down the program Y/N?:";
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
