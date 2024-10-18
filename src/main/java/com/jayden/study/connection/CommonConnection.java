package com.jayden.study.connection;

import com.jayden.study.utils.JdbcUtils;

import java.sql.*;

/**
 * JDBC Common Connection
 *
 * <p>Typical flow of using JDBC</p>
 *
 * 1. Load the JDBC driver
 * 2. Create a Connection
 * 3. Create a Statement and Get a ResultSet
 * 4. Traverse and Process the ResultSet
 * 5. Close ResultSet, Statement and Connection
 *
 * @author jayden-lee
 */
public class CommonConnection {
    private static final String url = "jdbc:mysql://" + System.getenv("MYSQL_HOST") + ":3306/mysql"; // Environment variable for database URL
    private static final String user = System.getenv("MYSQL_ADMIN_USER"); // Environment variable for database user
    private static final String password = System.getenv("MYSQL_ADMIN_PASSWORD"); // Environment variable for database password

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static void main(String[] args) {
        try {
            loadJdbcDriver();

            createConnection();

            long startTime = System.currentTimeMillis();
            createStatementAndGetResultSet();

            traverseAndProcessResultSet();

            long endTime = System.currentTimeMillis();
            System.out.println("Time to process: " + (endTime - startTime) + " milliseconds");

            closeResources();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Register JDBC driver
     *
     * @throws ClassNotFoundException
     */
    private static void loadJdbcDriver() throws ClassNotFoundException {
        // 5.X -> com.mysql.jdbc.Driver
        // 8.X -> com.mysql.cj.jdbc.Driver
        Class.forName("com.mysql.cj.jdbc.Driver");
    }

    /**
     * Create a Connection Instance
     *
     * @throws SQLException
     */
    private static void createConnection() throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
    }

    /**
     * Create a Statement Instance and Get a ResultSet
     *
     * @throws SQLException
     */
    private static void createStatementAndGetResultSet() throws SQLException {
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SHOW DATABASES;");
    }

    /**
     * Traverse and Process ResultSet
     *
     * @throws SQLException
     */
    private static void traverseAndProcessResultSet() throws SQLException {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        int columnCount = resultSetMetaData.getColumnCount();

        // Print column info
        for (int i = 1; i <= columnCount; i++) {
            System.out.print(resultSetMetaData.getColumnName(i) + " ");
        }

        System.out.println();

        // Print row info
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(resultSet.getString(i) + " ");
            }

            System.out.println();
        }
    }

    /**
     * Close Resources
     */
    private static void closeResources() {
        JdbcUtils.closeResultSet(resultSet);
        JdbcUtils.closeStatement(statement);
        JdbcUtils.closeConnection(connection);
    }
}
