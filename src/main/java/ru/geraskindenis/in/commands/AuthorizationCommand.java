package ru.geraskindenis.in.commands;

public class AuthorizationCommand implements Command<String[]> {
    public static final AuthorizationCommand INSTANCE = new AuthorizationCommand();
    private final String name;
    private final String heading;

    private AuthorizationCommand() {
        this.name = "Authorization";
        this.heading = String.format(">>> %s <<<", name.toUpperCase());
    }

    @Override
    public String[] execute() {
        System.out.println(heading);
        String login = EnterLogin.INSTANCE.execute();
        String password = EnterPassword.INSTANCE.execute();
        return new String[]{login, password};
    }

    @Override
    public String getName() {
        return name;
    }
}
