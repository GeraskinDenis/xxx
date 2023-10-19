package ru.geraskindenis.repository.impl;

import ru.geraskindenis.domain.PersonalAccount;
import ru.geraskindenis.repository.PersonalAccountRepository;
import ru.geraskindenis.utils.DataSource;

import java.sql.*;
import java.util.Optional;

public class PersonalAccountRepositoryImpl implements PersonalAccountRepository<Long, PersonalAccount> {
    public static final PersonalAccountRepositoryImpl INSTANCE = new PersonalAccountRepositoryImpl();

    @Override
    public void save(PersonalAccount personalAccount) throws SQLException {
        String query = "INSERT INTO personal_accounts (owner_id, balance) VALUES (?,?)";
        try (Connection connection = DataSource.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, personalAccount.getOwnerId());
            preparedStatement.setBigDecimal(2, personalAccount.getBalance());
            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();
            if (keys.next()) {
                personalAccount.setId(keys.getObject("id", Long.class));
            }
        }
    }

    @Override
    public void update(PersonalAccount personalAccount) throws SQLException {
        String query = "UPDATE personal_accounts SET owner_id=?, balance=? WHERE id=?";
        try (Connection connection = DataSource.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, personalAccount.getOwnerId());
            preparedStatement.setBigDecimal(2, personalAccount.getBalance());
            preparedStatement.setLong(3, personalAccount.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public Optional<PersonalAccount> findById(Long id) throws SQLException {
        String query = "SELECT * FROM personal_accounts WHERE owner_id = ?";
        try (Connection connection = DataSource.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? Optional.of(buildPersonalAccount(resultSet)) : Optional.empty();
        }
    }

    private PersonalAccount buildPersonalAccount(ResultSet resultSet) throws SQLException {
        PersonalAccount result = new PersonalAccount();
        result.setId(resultSet.getLong("id"));
        result.setOwnerId(resultSet.getLong("owner_id"));
        result.setBalance(resultSet.getBigDecimal("balance"));
        return result;
    }
}
