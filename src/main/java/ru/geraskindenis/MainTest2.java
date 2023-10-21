package ru.geraskindenis;

import liquibase.exception.LiquibaseException;
import ru.geraskindenis.utils.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class MainTest2 {
    public static void main(String[] args) throws SQLException, LiquibaseException {
        Connection connection = DataSource.INSTANCE.getConnection();
        System.out.println(connection.getAutoCommit());
        connection.close();
        DataSource.INSTANCE.close();
    }
}
