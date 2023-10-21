package ru.geraskindenis.repository.impl;

import ru.geraskindenis.entity.Transaction;
import ru.geraskindenis.model.TransactionType;
import ru.geraskindenis.repository.TransactionRepository;
import ru.geraskindenis.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class TransactionRepositoryImpl implements TransactionRepository {
    public static final TransactionRepositoryImpl INSTANCE = new TransactionRepositoryImpl();

    @Override
    public void save(Transaction transaction) throws SQLException {
        String query = "INSERT INTO transactions (uuid, personal_account_id, create_at, accept, transaction_type, amount) VALUES (CAST(? AS UUID), ?, ?, ?, CAST(? AS transaction_type), ?)";
        Connection connection = DataSource.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, transaction.getUuid().toString());
        preparedStatement.setLong(2, transaction.getPersonalAccountId());
        preparedStatement.setTimestamp(3, Timestamp.valueOf(transaction.getCreatedAt()));
        preparedStatement.setBoolean(4, transaction.getAccept());
        preparedStatement.setString(5, transaction.getTransactionType().getName());
        preparedStatement.setBigDecimal(6, transaction.getAmount());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            transaction.setId(resultSet.getObject("id", Long.class));
        }
        resultSet.close();
        preparedStatement.close();
        DataSource.INSTANCE.closeConnection();
    }

    @Override
    public Optional<Transaction> findByUuid(UUID uuid) throws SQLException {
        String query = "SELECT * FROM transactions WHERE uuid = ?";
        Connection connection = DataSource.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, uuid.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        Optional<Transaction> optional = resultSet.next() ? Optional.of(buildTransaction(resultSet)) : Optional.empty();
        resultSet.close();
        preparedStatement.close();
        DataSource.INSTANCE.closeConnection();
        return optional;
    }

    @Override
    public List<Transaction> findAllByPersonalAccountId(Long personalAccountId) throws SQLException {
        String query = "SELECT * FROM transactions WHERE personal_account_id = ?";
        Connection connection = DataSource.INSTANCE.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, personalAccountId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Transaction> transactions = new ArrayList<>();
        while (resultSet.next()) {
            Transaction transaction = buildTransaction(resultSet);
            transactions.add(transaction);
        }
        resultSet.close();
        preparedStatement.close();
        DataSource.INSTANCE.closeConnection();
        return transactions;
    }

    private Transaction buildTransaction(ResultSet resultSet) throws SQLException {
        Transaction result = new Transaction();
        result.setId(resultSet.getLong("id"));
        result.setUuid(UUID.fromString(resultSet.getString("uuid")));
        result.setPersonalAccountId(resultSet.getLong("personal_account_id"));
        result.setCreatedAt(resultSet.getTimestamp("create_at").toLocalDateTime());
        result.setAccept(resultSet.getBoolean("accept"));
        result.setTransactionType(TransactionType.valueOf(resultSet.getString("transaction_type")));
        result.setAmount(resultSet.getBigDecimal("amount"));
        return result;
    }
}
