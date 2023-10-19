package ru.geraskindenis.repository.impl;

import ru.geraskindenis.domain.User;
import ru.geraskindenis.in.commands.Role;
import ru.geraskindenis.repository.UserRepository;
import ru.geraskindenis.utils.DataSource;

import java.sql.*;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository<Long, User> {
    public static final UserRepositoryImpl INSTANCE = new UserRepositoryImpl();

    @Override
    public void save(User user) throws SQLException {
        String query = "INSERT INTO users (login, password, role) VALUES (?, ?, CAST( ? AS user_roles))";
        try (Connection connection = DataSource.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole().name());
            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();
            if (keys.next()) {
                user.setId(keys.getObject("id", Long.class));
            }
        }
    }

    @Override
    public void update(User user) throws SQLException {
        String query = "UPDATE users SET login=?, password=?, role=? WHERE id=?";
        try (Connection connection = DataSource.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole().name());
            preparedStatement.setLong(4, user.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public Optional<User> findById(Long id) throws SQLException {
        String query = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = DataSource.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? Optional.of(buildUser(resultSet)) : Optional.empty();
        }
    }

    @Override
    public Optional<User> findByLogin(String login) throws SQLException {
        String query = "SELECT * FROM users WHERE login = ?";
        try (Connection connection = DataSource.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? Optional.of(buildUser(resultSet)) : Optional.empty();
        }
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        User result = new User();
        result.setId(resultSet.getLong("id"));
        result.setLogin(resultSet.getString("login"));
        result.setPassword(resultSet.getString("password"));
        result.setRole(Role.valueOf(resultSet.getString("role")));
        return result;
    }

}
