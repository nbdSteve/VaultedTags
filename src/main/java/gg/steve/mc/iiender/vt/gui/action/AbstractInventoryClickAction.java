package gg.steve.mc.iiender.vt.gui.action;

import gg.steve.mc.iiender.vt.gui.AbstractGui;
import gg.steve.mc.iiender.vt.gui.utils.GuiItemCreationUtil;
import lombok.Data;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Data
public abstract class AbstractInventoryClickAction {
    private final GuiClickAction action;
    private final String id;
    private final int args;

    public AbstractInventoryClickAction(GuiClickAction action, String id, int args) {
        this.action = action;
        this.id = id;
        this.args = args;
    }

    public ItemStack getRenderedItem(Player player, ConfigurationSection section) {
        return GuiItemCreationUtil.createItem(section);
    }

    public abstract void onClick(AbstractGui gui, Player player, ConfigurationSection section, int slot);

    public boolean isIdMatch(String query) {
        return this.id.equalsIgnoreCase(query);
    }

    public boolean isValidFormat(String actionFromConfig) {
        String[] parts = actionFromConfig.split(":");
        if (!isIdMatch(parts[0])) return false;
        return this.args == parts.length;
    }
}
