package com.antonio.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.antonio.model.Player;

public class PlayerRepository {
    /**
     * SQL query to insert a new player into the player table.
     * The query expects two parameters: the player's name and score.
     */
    private static final String INSERT_PLAYER = "INSERT INTO player (name, score) VALUES (?, ?)";

    /**
     * SQL query to select a player from the player table based on the player's
     * name.
     * The query expects one parameter: the player's name.
     */
    private static final String SELECT_PLAYER = "SELECT * FROM player WHERE name = ?";

    /**
     * SQL query to retrieve all players from the player table ordered by their
     * scores in descending order.
     */
    private static final String ORDER_BY_SCORE = "SELECT * FROM player ORDER BY score DESC";

    /**
     * SQL query to update the score of a player in the database.
     * The query sets the score to a specified value for a player with a given name.
     */
    private static final String UPDATE_PLAYER_SCORE = "UPDATE player SET score = ? WHERE name = ?";

    /**
     * Inserts a new player into the database.
     *
     * @param player the Player object containing the player's details to be
     *               inserted* 
     * @throws SQLException if a database access error occurs or the SQL statement
     *                      fails
     */
    public static void insertPlayer(final Player player) throws SQLException {
        try (Connection conn = Database.getDatabaseConnection();
                PreparedStatement stmt = conn.prepareStatement(INSERT_PLAYER)) {
            stmt.setString(1, player.getName());
            stmt.setInt(2, player.getScore());
            stmt.executeUpdate();
        }
    }

    /**
     * Selects a player from the database based on the provided player information.
     * If the player exists in the database, returns the player with updated
     * information.
     * If the player does not exist, inserts the player into the database and
     * returns the provided player.
     *
     * @param player the player to be selected or inserted
     * @return the player with updated information if found in the database,
     *         otherwise the provided player
     * @throws SQLException if a database access error occurs
     */
    public static Player selectPlayer(final Player player) throws SQLException {
        try (Connection conn = Database.getDatabaseConnection();
                PreparedStatement stmt = conn.prepareStatement(SELECT_PLAYER)) {
            stmt.setString(1, player.getName());
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                return new Player(result.getString("name"), player.getRole(), result.getInt("score"));
            } else {
                insertPlayer(player);
                return player;
            }
        }
    }

    /**
     * Updates the scores of the given players in the database.
     *
     * @param playerX the player X whose score needs to be updated
     * @param playerO the player O whose score needs to be updated
     * @throws SQLException if a database access error occurs
     */
    public static void updatePlayersScore(final Player playerX, final Player playerO) throws SQLException {
        try (Connection conn = Database.getDatabaseConnection();
                PreparedStatement updateStmt = conn.prepareStatement(UPDATE_PLAYER_SCORE);
                PreparedStatement selectPlayerStmt = conn.prepareStatement(SELECT_PLAYER)) {
            try (ResultSet resultX = selectPlayerStmt.executeQuery()) {
                selectPlayerStmt.setString(1, playerX.getName());
                if (resultX.next()) {
                    updateStmt.setInt(1, resultX.getInt("score") + playerX.getScore());
                    updateStmt.setString(2, playerX.getName());
                    updateStmt.executeUpdate();
                }
            }
            try (ResultSet resultO = selectPlayerStmt.executeQuery()) {
                selectPlayerStmt.setString(1, playerO.getName());
                if (resultO.next()) {
                    updateStmt.setInt(1, resultO.getInt("score") + playerO.getScore());
                    updateStmt.setString(2, playerO.getName());
                    updateStmt.executeUpdate();
                }
            }
        }
    }

    /**
     * Retrieves the leaderboard of players sorted by their scores.
     *
     * @return a list of players ordered by their scores in descending order.
     * @throws SQLException if a database access error occurs.
     */
    public static List<Player> getLeaderBoard() throws SQLException {
        List<Player> players = new ArrayList<>();
        try (Connection conn = Database.getDatabaseConnection();
                PreparedStatement stmt = conn.prepareStatement(ORDER_BY_SCORE)) {
            ResultSet result = stmt.executeQuery();
            Player p;
            while (result.next()) {
                p = new Player();
                p.setName(result.getString("name"));
                p.setScore(result.getInt("score"));
                players.add(p);
            }
        }
        return players;
    }
}
