package ru.geraskindenis.in.commands;

public class RegistrationCommand implements Command<String[]> {
    public static final RegistrationCommand INSTANCE = new RegistrationCommand();
    private final String name;
    private final String heading;

    private RegistrationCommand() {
        this.name = "Registration";
        this.heading = String.format(">>> %s <<<", name.toUpperCase());
    }

    @Override
    public String[] execute() {
        System.out.println(heading);
        String newLogin = EnterNewLogin.INSTANCE.execute();
        String newPassword = EnterNewPassword.INSTANCE.execute();
        return new String[]{newLogin, newPassword};
    }

    @Override
    public String getName() {
        return name;
    }
}
