package ru.geraskindenis.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    private static final HikariConfig CONFIG = new HikariConfig();
    public static final DataSource INSTANCE;
    private final HikariDataSource hikariDataSource;


    static {
        String environment = System.getProperty("app.environment", "dev");
        CONFIG.setJdbcUrl(PropertiesUtil.getString("db.connection.url." + environment));
        CONFIG.setUsername(PropertiesUtil.getString("db.connection.username." + environment));
        CONFIG.setPassword(PropertiesUtil.getString("db.connection.password." + environment));
        CONFIG.setSchema(PropertiesUtil.getString("db.connection.schema." + environment));
        CONFIG.setMaximumPoolSize(PropertiesUtil.getInteger("db.connection.poolSize." + environment));
        INSTANCE = new DataSource();
    }

    private DataSource() {
        hikariDataSource = new HikariDataSource(CONFIG);
    }

    public Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }

    public void close() {
        hikariDataSource.close();
    }
}
