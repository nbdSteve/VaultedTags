package gg.steve.mc.iiender.vt.db;

import gg.steve.mc.iiender.vt.VaultedTagsPlugin;
import gg.steve.mc.iiender.vt.framework.utils.LogUtil;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class TagDBUtil {

    public static void generateTables() {
        Connection connection = DatabaseManager.getDbInjector().getConnection();
        Bukkit.getScheduler().runTaskAsynchronously(VaultedTagsPlugin.getInstance(), () -> {
            try {
                PreparedStatement table =
                        connection.prepareStatement("CREATE TABLE IF NOT EXISTS player_tags " +
                                "(player_id VARCHAR(36) NOT NULL," +
                                "selected_tag_id VARCHAR(255) NOT NULL, " +
                                "PRIMARY KEY (player_id))");
                table.execute();
                table.close();
            } catch (SQLException e) {
                e.printStackTrace();
                LogUtil.warning("SQLite error creating the player tags table.");
            }
        });
    }

    public static String getSelectedTagForPlayer(UUID playerId) {
        Connection connection = DatabaseManager.getDbInjector().getConnection();
        String tagId = "";
        synchronized (VaultedTagsPlugin.getInstance()) {
            try {
                PreparedStatement query =
                        connection.prepareStatement("SELECT * FROM player_tags WHERE player_id='" + String.valueOf(playerId) + "'");
                ResultSet rs = query.executeQuery();
                while (rs.next()) {
                    tagId = rs.getString("selected_tag_id");
                }
                query.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tagId;
    }

    public static void setSelectedTagForPlayer(UUID playerId, String tagId) {
        if (!getSelectedTagForPlayer(playerId).equalsIgnoreCase("")) deleteSelectedTagForPlayer(playerId);
        Connection connection = DatabaseManager.getDbInjector().getConnection();
        Bukkit.getScheduler().runTaskAsynchronously(VaultedTagsPlugin.getInstance(), () -> {
            try {
                PreparedStatement set =
                        connection.prepareStatement("INSERT INTO player_tags (player_id, selected_tag_id) VALUES (?, ?);");
                set.setString(1, String.valueOf(playerId));
                set.setString(2, tagId);
                set.executeUpdate();
                set.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public static void deleteSelectedTagForPlayer(UUID playerId) {
        Connection connection = DatabaseManager.getDbInjector().getConnection();
        Bukkit.getScheduler().runTaskAsynchronously(VaultedTagsPlugin.getInstance(), () -> {
            try {
                PreparedStatement set =
                        connection.prepareStatement("DELETE FROM player_tags WHERE player_id='" + String.valueOf(playerId) + "'");
                set.executeUpdate();
                set.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public static boolean hasTagSelected(UUID playerId) {
        return !getSelectedTagForPlayer(playerId).equalsIgnoreCase("");
    }
}
