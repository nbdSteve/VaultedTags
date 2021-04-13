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
                                "selected_tag_id VARCHAR(36) NOT NULL, " +
                                "PRIMARY KEY (player_id))");
                table.execute();
                table.close();
            } catch (SQLException e) {
                e.printStackTrace();
                LogUtil.warning("SQLite error creating the player tags table.");
            }
        });
    }

//    public static UUID getSelectedTagForPlayer(UUID playerId) {
//        Connection connection = DatabaseManager.getDbInstance().getDbInjector().getConnection();
//        UUID tagId = null;
//        Bukkit.getScheduler().runTaskAsynchronously(StrixPunishmentGui.getInstance(), () -> {
//            try {
//                PreparedStatement query =
//                        connection.prepareStatement("SELECT * FROM `player_tags` WHERE uuid='"+ playerId + "'");
//                ResultSet rs = query.executeQuery();
//                while (rs.next()) {
//                    Date expiry;
//                    try {
//                        expiry = StrixPunishmentGui.getFormat().parse(rs.getString("expiration"));
//                    } catch (ParseException e) {
//                        LogUtil.warning("Error converting the expiry date to an actual date for punishment: " + rs.getString("punishment_id") + " for player: " + rs.getString("uuid"));
//                        continue;
//                    }
//                    if (!current.after(expiry)) continue;
//                    expired.add(rs.getString("uuid") + "<>" + rs.getString("punishment_id") + "<>" + rs.getInt("level"));
//                }
//                query.close();
//            } catch (SQLException e) {
//                LogUtil.warning("SQL error fetching and checking punishment data for all players.");
//            }
//            for (String entry : expired) {
//                String[] parts = entry.split("<>");
//                removePunishment(parts[0], parts[1], parts[2]);
//            }
//        });
//    }

    public static UUID getSelectedTagForPlayer(UUID playerId) {
        Connection connection = DatabaseManager.getDbInjector().getConnection();
        UUID tagId = null;
        synchronized (VaultedTagsPlugin.getInstance()) {
            try {
                PreparedStatement query =
                        connection.prepareStatement("SELECT * FROM player_tags WHERE player_id='" + String.valueOf(playerId) + "'");
                ResultSet rs = query.executeQuery();
                while (rs.next()) {
                    tagId = UUID.fromString(rs.getString("selected_tag_id"));
                }
                query.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tagId;
    }

    public static void setSelectedTagForPlayer(UUID playerId, UUID tagId) {
        deleteSelectedTagForPlayer(playerId);
        Connection connection = DatabaseManager.getDbInjector().getConnection();
        Bukkit.getScheduler().runTaskAsynchronously(VaultedTagsPlugin.getInstance(), () -> {
            try {
                PreparedStatement set =
                        connection.prepareStatement("INSERT INTO player_tags (player_id, selected_tag_id) VALUES (?, ?);");
                set.setString(1, String.valueOf(playerId));
                set.setString(2, String.valueOf(tagId));
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
}
