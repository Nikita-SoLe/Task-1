package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "pannik";
    private static final String PASSWORD = "1234";
    private static Connection connect;
    private static SessionFactory factory;
    private Util() {}

    public static SessionFactory getFactory() {
        if (factory == null) createFactory();
        return factory;
    }
    private static void createFactory() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySetting(Environment.URL, URL)
                .applySetting(Environment.DRIVER, "org.postgresql.Driver")
                .applySetting(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect")
                .applySetting(Environment.USER, USERNAME)
                .applySetting(Environment.PASS, PASSWORD)
                .applySetting(Environment.HBM2DDL_CHARSET_NAME, "none")
                .applySetting(Environment.SHOW_SQL, "true")
                .applySetting(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread")
                .build();

        Metadata metadata = new MetadataSources(registry)
                .addAnnotatedClass(User.class)
                .buildMetadata();

        factory = metadata.buildSessionFactory();
    }

    public static Connection getConnection() {
        if (connect == null) {
            try {
                connect = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Connect create");
            } catch (SQLException e) {
                System.out.println("Проблемы подключения к Базе Данных.");
                System.out.println(e);
            }
        }
        return connect;
    }
}
