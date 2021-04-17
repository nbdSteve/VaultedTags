package gg.steve.mc.iiender.vt.tags;

import gg.steve.mc.iiender.vt.db.DatabaseManager;
import gg.steve.mc.iiender.vt.gui.GuiManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {

    @EventHandler
    public void connect(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (DatabaseManager.hasTagSelected(player.getUniqueId())) {
            TagsManager.getInstance().addTagPlayer(player);
        }
    }

    @EventHandler
    public void disconnect(PlayerQuitEvent event) {
        GuiManager.getInstance().removePlayer(event.getPlayer());
        TagsManager.getInstance().removeTagPlayer(event.getPlayer());
    }
}
