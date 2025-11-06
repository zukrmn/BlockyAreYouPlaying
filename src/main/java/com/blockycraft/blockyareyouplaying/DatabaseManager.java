package com.blockycraft.blockyareyouplaying;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private Connection connection;

    public DatabaseManager(String path) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + path);
            createTable();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS are_you_playing (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "uuid TEXT NOT NULL," +
                    "username TEXT NOT NULL," +
                    "x_pos REAL NOT NULL," +
                    "y_pos REAL NOT NULL," +
                    "z_pos REAL NOT NULL," +
                    "yaw REAL NOT NULL," +
                    "pitch REAL NOT NULL," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL"
                    + ");");
        }
    }

    public void logPlayer(Player player) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO are_you_playing (uuid, username, x_pos, y_pos, z_pos, yaw, pitch) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            Location location = player.getLocation();
            statement.setString(1, player.getUniqueId().toString());
            statement.setString(2, player.getName());
            statement.setDouble(3, location.getX());
            statement.setDouble(4, location.getY());
            statement.setDouble(5, location.getZ());
            statement.setFloat(6, location.getYaw());
            statement.setFloat(7, location.getPitch());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
