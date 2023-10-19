package ru.geraskindenis.services.impl;

import ru.geraskindenis.domain.EventRecord;
import ru.geraskindenis.domain.User;
import ru.geraskindenis.exceptions.UserServiceException;
import ru.geraskindenis.in.commands.Role;
import ru.geraskindenis.repository.UserRepository;
import ru.geraskindenis.repository.impl.UserRepositoryImpl;
import ru.geraskindenis.services.PersonalAccountService;
import ru.geraskindenis.services.SystemUser;
import ru.geraskindenis.services.UserEventService;
import ru.geraskindenis.services.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class UserServiceImpl implements UserService {
    public static final UserServiceImpl INSTANCE = new UserServiceImpl();

    private final UserRepository<Long, User> userRepository;
    private final PersonalAccountService personalAccountService;
    private final UserEventService<User, String, List<EventRecord>> userEventService;
    private User loggedUser;

    private UserServiceImpl() {
        this.userRepository = UserRepositoryImpl.INSTANCE;
        this.userEventService = UserEventServiceImpl.INSTANCE;
        this.personalAccountService = PersonalAccountServiceImp.INSTANCE;
        this.loggedUser = null;
    }

    @Override
    public User registration(String newLogin, String newPassword) {
        if (SystemUser.INSTANCE.getLogin().equals(newLogin)) {
            throw new UserServiceException("ERROR: This login does not work.");
        }

        try {
            userEventService.addLog(SystemUser.INSTANCE, "Start of registration");
            User user = new User();
            user.setLogin(newLogin);
            user.setPassword(newPassword);
            user.setRole(Role.USER);
            userRepository.save(user);
            userEventService.addLog(SystemUser.INSTANCE, "New user registered by name: " + newLogin);
            loggedUser = user;
            personalAccountService.createPersonalAccount(user);
            return user;
        } catch (SQLException e) {
            throw new UserServiceException(e);
        }
    }

    @Override
    public boolean authorization(String login, String password) {
        userEventService.addLog(SystemUser.INSTANCE, "Start of authorization");
        try {
            User incomingUser = userRepository.findByLogin(login).orElseThrow();
            if (incomingUser.getPassword().equals(password)) {
                loggedUser = incomingUser;
                userEventService.addLog(loggedUser, "Successful authorization");
            } else {
                userEventService.addLog(SystemUser.INSTANCE, "Authorisation Error");
                throw new UserServiceException("ERROR: Invalid credentials!");
            }
            return loggedIn();
        } catch (SQLException e) {
            throw new UserServiceException(e);
        }
    }

    @Override
    public User getLoggedUser() {
        if (Objects.isNull(loggedUser)) {
            throw new UserServiceException("ERROR: User is not authorized.");
        }
        return loggedUser;
    }

    @Override
    public void logout() {
        userEventService.addLog(loggedUser, "Logout");
        loggedUser = null;
    }

    @Override
    public User findByLogin(String login) throws SQLException {
        return userRepository.findByLogin(login).get();
    }

    @Override
    public boolean loggedIn() {
        return Objects.nonNull(loggedUser);
    }
}
