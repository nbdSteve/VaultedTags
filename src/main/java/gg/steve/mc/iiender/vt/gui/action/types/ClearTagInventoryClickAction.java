package gg.steve.mc.iiender.vt.gui.action.types;

import gg.steve.mc.iiender.vt.framework.message.DebugMessage;
import gg.steve.mc.iiender.vt.framework.message.GeneralMessage;
import gg.steve.mc.iiender.vt.gui.AbstractGui;
import gg.steve.mc.iiender.vt.gui.action.AbstractInventoryClickAction;
import gg.steve.mc.iiender.vt.gui.action.GuiClickAction;
import gg.steve.mc.iiender.vt.tags.TagsManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class ClearTagInventoryClickAction extends AbstractInventoryClickAction {

    public ClearTagInventoryClickAction() {
        super(GuiClickAction.CLEAR_TAG, "clear_tag", 1);
    }

    @Override
    public void onClick(AbstractGui gui, Player player, ConfigurationSection section) {
        if (TagsManager.getInstance().hasTagSelected(player)) {
            TagsManager.getInstance().clearTag(player);
            gui.refresh();
        } else {
            player.closeInventory();
            DebugMessage.NO_TAG_SELECTED.message(player);
        }
    }
}