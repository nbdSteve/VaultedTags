package gg.steve.mc.iiender.vt.gui.action.types;

import gg.steve.mc.iiender.vt.gui.action.AbstractInventoryClickAction;
import gg.steve.mc.iiender.vt.gui.action.GuiClickAction;
import gg.steve.mc.iiender.vt.tags.TagsManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class ClearTagInventoryClickAction extends AbstractInventoryClickAction {

    public ClearTagInventoryClickAction() {
        super(GuiClickAction.CLEAR_TAG, "clear_tag", 1);
    }

    @Override
    public void onClick(Player player, ConfigurationSection section) {
        if (TagsManager.getInstance().hasTagSelected(player)) {
            TagsManager.getInstance().clearTag(player);
        } else {
            player.closeInventory();
            player.sendMessage(ChatColor.RED + "You do not have a tag selected.");
        }
    }
}
