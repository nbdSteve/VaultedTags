package gg.steve.mc.iiender.vt.gui.action.types;

import gg.steve.mc.iiender.vt.gui.AbstractGui;
import gg.steve.mc.iiender.vt.gui.action.AbstractInventoryClickAction;
import gg.steve.mc.iiender.vt.gui.action.GuiClickAction;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class CloseInventoryClickAction extends AbstractInventoryClickAction {

    public CloseInventoryClickAction() {
        super(GuiClickAction.CLOSE, "close", 1);
    }

    @Override
    public void onClick(AbstractGui gui, Player player, ConfigurationSection section, int slot) {
        player.closeInventory();
    }
}
