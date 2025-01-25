package com.antonio.repository;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    /**
     * The URL for connecting to the SQLite database.
     * The database file is located at "src/db/fxtictactoe.db".
     * The URL is constructed using the JDBC SQLite protocol.
     */
    private static final String URL = "jdbc:sqlite:" + Paths.get("src/main/resources/db/fxtictactoe.db").toString();

    /**
     * Establishes a connection to the database using the specified URL.
     *
     * @return a {@link Connection} object that represents the connection to the
     *         database.
     * @throws SQLException if a database access error occurs or the URL is null.
     */
    public static Connection getDatabaseConnection() throws SQLException {
        createTablesIfNotExist();
        return DriverManager.getConnection(URL);
    }

    private static void createTablesIfNotExist() {
        try (Connection conn = DriverManager.getConnection(URL)) {
            conn.createStatement().execute("CREATE TABLE IF NOT EXISTS player (name TEXT PRIMARY KEY, score INTEGER)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
