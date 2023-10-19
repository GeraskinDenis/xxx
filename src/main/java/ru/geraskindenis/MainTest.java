package ru.geraskindenis;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import ru.geraskindenis.utils.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class MainTest {
    public static void main(String[] args) throws SQLException, LiquibaseException {
        String query = "CREATE SCHEMA IF NOT EXISTS wallet_service AUTHORIZATION postgres";
        Connection connection = DataSource.INSTANCE.getConnection();
        connection.createStatement().execute(query);
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        database.setDefaultSchemaName("wallet_service");
        Liquibase liquibase = new Liquibase("db/changelog/changelog-master.xml", new ClassLoaderResourceAccessor(), database);
        liquibase.update();
        connection.close();
        DataSource.INSTANCE.close();
    }
}
