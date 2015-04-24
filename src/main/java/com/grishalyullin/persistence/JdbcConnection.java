package com.grishalyullin.persistence;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnection {

    private static final Logger LOG = Logger.getLogger(JdbcConnection.class.getName());

    private static final String URL = "jdbc:mysql://localhost:3306/sql_injection_practise";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "toor";

    private Connection connection;

    public JdbcConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (!connection.isClosed()) {
                LOG.info("JDBC connection has been successfully created");
            }
        } catch (ClassNotFoundException | SQLException e) {
            LOG.warn(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
