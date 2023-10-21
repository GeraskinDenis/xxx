package ru.geraskindenis.in.commands;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCommandPackage implements Command<Command<?>> {
    protected final String name;
    protected final String heading;
    protected final String clue;
    protected final List<Command<?>> commands;

    protected AbstractCommandPackage(String name) {
        this.name = name;
        this.heading = String.format("\n>>> %s <<<", name.toUpperCase());
        this.clue = "Enter the number of menu:";
        commands = new ArrayList<>();
        initialization();
    }

    @Override
    public String getName() {
        return name;
    }

    protected abstract void initialization();
}
