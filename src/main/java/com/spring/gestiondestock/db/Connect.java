package com.spring.gestiondestock.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private String url;
    private String username;
    private String password;

    public Connect() {
        this.url = System.getenv("JDBC_URL");
        this.username = System.getenv("DB_USERNAME");
        this.password = System.getenv("DB_PASSWORD");
    }
    public Connection CreateConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        Connection connection = DriverManager.getConnection(url, username, password);

        return connection;
    }
}
