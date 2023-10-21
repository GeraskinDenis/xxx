package ru.geraskindenis.containers;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import ru.geraskindenis.utils.PropertiesUtil;

public class PostgresTestContainer extends PostgreSQLContainer<PostgresTestContainer> {

    private static final String IMAGE_VERSION;
    private static final String USERNAME;
    private static final String PASSWORD;
    private static final String DATABASE_NAME;
    private static final String LANG;
    public static PostgresTestContainer container;


    static {
        IMAGE_VERSION = PropertiesUtil.getString("db.connection.image_version");
        USERNAME = PropertiesUtil.getString("db.connection.username");
        PASSWORD = PropertiesUtil.getString("db.connection.password");
        DATABASE_NAME = PropertiesUtil.getString("db.connection.databasename");
        LANG = PropertiesUtil.getString("db.connection.schema");
    }

    private PostgresTestContainer() {
        super(DockerImageName.parse(IMAGE_VERSION));
    }

    public static PostgresTestContainer getInstance() {
        if (container == null) {
            container = new PostgresTestContainer().withUsername(USERNAME).withPassword(PASSWORD)
                    .withDatabaseName(DATABASE_NAME).withEnv("LANG", LANG);
        }
        return container;
    }

    @Override
    public void start() {
        if (!container.isRunning()) {
            super.start();
            PropertiesUtil.setProperty("db.connection.url", container.getJdbcUrl());
        }
    }

    @Override
    public void stop() {
    }
}
