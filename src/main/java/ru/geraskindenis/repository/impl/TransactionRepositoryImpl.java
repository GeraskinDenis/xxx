package ru.geraskindenis.repository.impl;

import ru.geraskindenis.domain.Transaction;
import ru.geraskindenis.domain.TransactionTypes;
import ru.geraskindenis.repository.TransactionRepository;
import ru.geraskindenis.utils.DataSource;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


public class TransactionRepositoryImpl implements TransactionRepository<UUID, Transaction> {
    public static final TransactionRepositoryImpl INSTANCE = new TransactionRepositoryImpl();

    @Override
    public void save(Transaction transaction) throws SQLException {
        String query = "INSERT INTO transactions (uuid, personal_account_id, create_at, accept, transaction_type, amount) VALUES (CAST(? AS UUID), ?, ?, ?, CAST(? AS transaction_type), ?)";
        try (Connection connection = DataSource.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, transaction.getUuid().toString());
            preparedStatement.setLong(2, transaction.getPersonalAccountId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(transaction.getCreatedAt()));
            preparedStatement.setBoolean(4, transaction.getAccept());
            preparedStatement.setString(5, transaction.getTransactionType().getName());
            preparedStatement.setBigDecimal(6, transaction.getAmount());
            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();
            if (keys.next()) {
                transaction.setId(keys.getObject("id", Long.class));
            }
        }
    }

    @Override
    public Optional<Transaction> findByUuid(UUID uuid) throws SQLException {
        String query = "SELECT * FROM transactions WHERE uuid = ?";
        try (Connection connection = DataSource.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? Optional.of(buildTransaction(resultSet)) : Optional.empty();
        }
    }

    @Override
    public Map<UUID, Transaction> findAllByPersonalAccountId(Long personalAccountId) throws SQLException {
        String query = "SELECT * FROM transactions WHERE personal_account_id = ?";
        try (Connection connection = DataSource.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, personalAccountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<UUID, Transaction> transactionMap = new HashMap<>();
            while (resultSet.next()) {
                Transaction transaction = buildTransaction(resultSet);
                transactionMap.put(transaction.getUuid(), transaction);
            }
            return transactionMap;
        }
    }

    private Transaction buildTransaction(ResultSet resultSet) throws SQLException {
        Transaction result = new Transaction();
        result.setId(resultSet.getLong("id"));
        result.setUuid(UUID.fromString(resultSet.getString("uuid")));
        result.setPersonalAccountId(resultSet.getLong("personal_account_id"));
        result.setCreatedAt(resultSet.getTimestamp("create_at").toLocalDateTime());
        result.setAccept(resultSet.getBoolean("accept"));
        result.setTransactionType(TransactionTypes.valueOf(resultSet.getString("transaction_type")));
        result.setAmount(resultSet.getBigDecimal("amount"));
        return result;
    }
}
