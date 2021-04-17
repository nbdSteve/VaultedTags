package gg.steve.mc.iiender.vt.gui.implementations;

import gg.steve.mc.iiender.vt.framework.utils.LogUtil;
import gg.steve.mc.iiender.vt.gui.AbstractGui;
import gg.steve.mc.iiender.vt.gui.action.GuiClickAction;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Locale;

public class GenericGui extends AbstractGui {

    public GenericGui(String guiId, YamlConfiguration config, JavaPlugin instance) {
        super(guiId, config, instance);
    }

    @Override
    public void refresh() {
        for (String entry : this.getConfig().getKeys(false)) {
            try {
                Integer.parseInt(entry);
            } catch (NumberFormatException e) {
                continue;
            }
            List<Integer> slots = this.getConfig().getIntegerList(entry + ".slots");
            GuiClickAction action = GuiClickAction.getActionFromString(this.getConfig().getString(entry + ".action").split(":")[0].toLowerCase(Locale.ROOT));
            if (action == null) {
                LogUtil.warning("Invalid gui click action present for item in slots, " + slots + ".");
                continue;
            }
            for (Integer slot : slots) {
                setItemInSlot(slot, action.getAction().getRenderedItem(this.getOwner(), this.getConfig().getConfigurationSection(entry)), player -> {
                    action.getAction().onClick(this, player, this.getConfig().getConfigurationSection(entry), slot);
                });
            }
        }
    }

    @Override
    public AbstractGui clone() {
        return new GenericGui(this.getGuiId(), this.getConfig(), this.getInstance());
    }
}
