package ru.geraskindenis.repository.impl;

import ru.geraskindenis.entity.User;
import ru.geraskindenis.model.Role;
import ru.geraskindenis.repository.UserRepository;
import ru.geraskindenis.utils.DataSource;

import java.sql.*;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    public static final UserRepositoryImpl INSTANCE = new UserRepositoryImpl();

    @Override
    public void save(User user) throws SQLException {
        String query = "INSERT INTO users (login, password, role) VALUES (?, ?, CAST( ? AS user_roles))";
        Connection connection = DataSource.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, user.getLogin());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getRole().name());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            user.setId(resultSet.getObject("id", Long.class));
        }
        resultSet.close();
        preparedStatement.close();
        DataSource.INSTANCE.closeConnection();
    }

    @Override
    public void update(User user) throws SQLException {
        String query = "UPDATE users SET login=?, password=?, role=? WHERE id=?";
        Connection connection = DataSource.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, user.getLogin());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getRole().name());
        preparedStatement.setLong(4, user.getId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        DataSource.INSTANCE.closeConnection();
    }

    @Override
    public Optional<User> findById(Long id) throws SQLException {
        String query = "SELECT * FROM users WHERE id = ?";
        Connection connection = DataSource.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Optional<User> optional = resultSet.next() ? Optional.of(buildUser(resultSet)) : Optional.empty();
        resultSet.close();
        preparedStatement.close();
        DataSource.INSTANCE.closeConnection();
        return optional;
    }

    @Override
    public Optional<User> findByLogin(String login) throws SQLException {
        String query = "SELECT * FROM users WHERE login = ?";
        Connection connection = DataSource.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, login);
        ResultSet resultSet = preparedStatement.executeQuery();
        Optional<User> optional = resultSet.next() ? Optional.of(buildUser(resultSet)) : Optional.empty();
        resultSet.close();
        preparedStatement.close();
        DataSource.INSTANCE.closeConnection();
        return optional;
    }

    @Override
    public void deleteByLogin(String login) throws SQLException {
        String query = "DELETE FROM users WHERE login = ?";
        Connection connection = DataSource.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, login);
        preparedStatement.execute();
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
