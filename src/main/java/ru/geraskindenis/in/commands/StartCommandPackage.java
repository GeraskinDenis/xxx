package ru.geraskindenis.in.commands;

import ru.geraskindenis.in.commands.exceptions.CommandException;

public class StartCommandPackage extends AbstractCommandPackage {
    public static final StartCommandPackage INSTANCE = new StartCommandPackage();

    private StartCommandPackage() {
        super("Start menu");
    }

    @Override
    protected void initialization() {
        commands.add(RegistrationCommand.INSTANCE);
        commands.add(AuthorizationCommand.INSTANCE);
        commands.add(Exit.INSTANCE);
    }

    @Override
    public Command<?> execute() {
        System.out.println(heading);
        for (int i = 0; i < commands.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, commands.get(i).getName());
        }
        int i = validator(Utils.INSTANCE.getInteger(clue));
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
