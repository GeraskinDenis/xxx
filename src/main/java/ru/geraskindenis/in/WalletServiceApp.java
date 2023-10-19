package ru.geraskindenis.in;

import ru.geraskindenis.in.commands.*;
import ru.geraskindenis.services.MainService;
import ru.geraskindenis.services.impl.MainServiceImpl;

public class WalletServiceApp {
    public static final WalletServiceApp INSTANCE = new WalletServiceApp();
    private final MainService mainService;
    private boolean running;

    public WalletServiceApp() {
        this.mainService = MainServiceImpl.INSTANCE;
        this.running = true;
    }

    public void launch() {
        while (running) {
            try {
                if (mainService.loggedIn()) {
                    Command<?> command = AccountOperationsCommandPackage.INSTANCE.execute();
                    if (command == Logout.INSTANCE) {
                        if ((boolean) command.execute()) {
                            mainService.logout();
                        }
                    } else {
                        command.execute();
                    }
                } else {
                    Command<?> command = StartCommandPackage.INSTANCE.execute();
                    if (command == RegistrationCommand.INSTANCE) {
                        String[] loginPass = (String[]) command.execute();
                        mainService.registration(loginPass[0], loginPass[1]);
                    } else if (command == AuthorizationCommand.INSTANCE) {
                        String[] loginPass = (String[]) command.execute();
                        mainService.authorization(loginPass[0], loginPass[1]);
                    } else if (command == Exit.INSTANCE) {
                        running = !(boolean) command.execute();
                    }
                }
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
//                e.printStackTrace();
            }
        }
        mainService.shutDown();
    }
}
