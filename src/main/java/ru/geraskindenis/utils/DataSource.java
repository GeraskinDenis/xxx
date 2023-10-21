package ru.geraskindenis.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class DataSource {
    public static final DataSource INSTANCE;
    private static final HikariConfig CONFIG = new HikariConfig();
    private final HikariDataSource hikariDataSource;
    private Connection connection;


    static {
        CONFIG.setJdbcUrl(PropertiesUtil.getString("db.connection.url"));
        CONFIG.setUsername(PropertiesUtil.getString("db.connection.username"));
        CONFIG.setPassword(PropertiesUtil.getString("db.connection.password"));
        INSTANCE = new DataSource();
    }

    private DataSource() {
        hikariDataSource = new HikariDataSource(CONFIG);
    }

    public Connection getConnection() throws SQLException {
        if (Objects.isNull(connection) || connection.isClosed()) {
            connection = hikariDataSource.getConnection();
        }
        return connection;
    }

    public void closeConnection() throws SQLException {
        if (connection.getAutoCommit()) {
            connection.close();
        }
    }

    public void close() {
        hikariDataSource.close();
    }
}
