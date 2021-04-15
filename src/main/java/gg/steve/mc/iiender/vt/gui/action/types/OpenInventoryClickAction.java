package gg.steve.mc.iiender.vt.gui.action.types;

import gg.steve.mc.iiender.vt.gui.GuiManager;
import gg.steve.mc.iiender.vt.gui.action.AbstractInventoryClickAction;
import gg.steve.mc.iiender.vt.gui.action.GuiClickAction;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class OpenInventoryClickAction extends AbstractInventoryClickAction {

    public OpenInventoryClickAction() {
        super(GuiClickAction.OPEN, "open", 2);
    }

    @Override
    public void onClick(Player player, ConfigurationSection section) {
        GuiManager.getInstance().openGui(player, getGuiId(section.getString("action")));
    }

    public String getGuiId(String actionFromConfig) {
        return actionFromConfig.split(":")[1];
    }
}
