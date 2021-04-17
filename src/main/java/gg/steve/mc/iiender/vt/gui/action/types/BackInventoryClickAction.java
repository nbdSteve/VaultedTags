package gg.steve.mc.iiender.vt.gui.action.types;

import gg.steve.mc.iiender.vt.gui.AbstractGui;
import gg.steve.mc.iiender.vt.gui.GuiManager;
import gg.steve.mc.iiender.vt.gui.action.AbstractInventoryClickAction;
import gg.steve.mc.iiender.vt.gui.action.GuiClickAction;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class BackInventoryClickAction extends AbstractInventoryClickAction {

    public BackInventoryClickAction() {
        super(GuiClickAction.BACK, "back", 1);
    }

    @Override
    public void onClick(AbstractGui gui, Player player, ConfigurationSection section, int slot) {
        if (!gui.isHasParentGui()) return;
        gui.close(player);
        GuiManager.getInstance().openGui(player, gui.getParentGuiId());
    }
}