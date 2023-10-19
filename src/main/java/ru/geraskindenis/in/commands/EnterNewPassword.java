package ru.geraskindenis.in.commands;

import ru.geraskindenis.in.commands.exceptions.CommandException;

public class EnterNewPassword implements Command<String> {
    public static final EnterNewPassword INSTANCE = new EnterNewPassword();
    private final String name;
    private final String clue;

    private EnterNewPassword() {
        this.name = "Enter new password";
        this.clue = "Enter new password:";
    }

    @Override
    public String execute() {
        String newPassword1 = validator(Utils.INSTANCE.getString(clue));
        String newPassword2 = validator(Utils.INSTANCE.getString("Repeat new password:"));
        if (!newPassword1.equals(newPassword2)) {
            throw new CommandException("ERROR: New passwords don't match.");
        }
        return newPassword1;
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
