package ru.geraskindenis.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.geraskindenis.domain.User;

class UserServiceTest {
    private UserService userService;
    private User user;

    @BeforeEach
    public void beforeEach() {

    }

    @Test
    @DisplayName("Authorization")
    void testCase1() {
        String login = "TestLogin";
        String password = "TestPassword";
    }
}
