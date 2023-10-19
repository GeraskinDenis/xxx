package ru.geraskindenis.utils;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.SQLException;

public class LiquibaseInitialization {
    public static void initialization() throws SQLException, LiquibaseException {
        String query = "CREATE SCHEMA IF NOT EXISTS wallet_service AUTHORIZATION postgres";
        Connection connection = DataSource.INSTANCE.getConnection();
        connection.createStatement().execute(query);
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        database.setDefaultSchemaName("wallet_service");
        Liquibase liquibase = new Liquibase("db/changelog/changelog-master.xml", new ClassLoaderResourceAccessor(), database);
        liquibase.update();
        connection.close();
    }
}
