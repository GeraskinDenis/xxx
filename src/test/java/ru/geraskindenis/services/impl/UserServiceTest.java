package ru.geraskindenis.services.impl;

import org.junit.jupiter.api.*;
import ru.geraskindenis.containers.PostgresTestContainer;
import ru.geraskindenis.entity.User;
import ru.geraskindenis.services.UserService;
import ru.geraskindenis.utils.LiquibaseInitialization;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {
    private PostgresTestContainer container;
    private UserService userService;

    @BeforeEach
    public void beforeEach() {
        container = PostgresTestContainer.getInstance();
        container.start();
        LiquibaseInitialization.initialization();
        userService = UserServiceImpl.INSTANCE;
    }

    @AfterEach
    public void afterEach() {

    }

    @Test
    @DisplayName("Successful registration")
    @Order(1)
    void testCase1() {
        String login = "TestLogin1";
        String password = "TestPassword1";
        User user = userService.registration(login, password);
        assertThat(user).isNotNull();
        assertThat(user.getLogin()).isEqualTo(login);
        assertThat(user.getPassword()).isEqualTo(password);
    }

    @Test
    @DisplayName("Successful authorization")
    @Order(2)
    void testCase2() {
        String login = "TestLogin2";
        String password = "TestPassword2";
        User user = userService.registration(login, password);
        assertThat(userService.authorization(login, password)).isTrue();
        assertThat(userService.getLoggedUser()).isEqualTo(user);
    }
}
