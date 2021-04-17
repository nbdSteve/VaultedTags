package gg.steve.mc.iiender.vt.gui.action.types;

import gg.steve.mc.iiender.vt.VaultedTagsPlugin;
import gg.steve.mc.iiender.vt.framework.message.DebugMessage;
import gg.steve.mc.iiender.vt.framework.utils.SoundUtil;
import gg.steve.mc.iiender.vt.framework.yml.Files;
import gg.steve.mc.iiender.vt.gui.AbstractGui;
import gg.steve.mc.iiender.vt.gui.action.AbstractInventoryClickAction;
import gg.steve.mc.iiender.vt.gui.action.GuiClickAction;
import gg.steve.mc.iiender.vt.gui.implementations.TagPageGui;
import gg.steve.mc.iiender.vt.gui.utils.GuiItemCreationUtil;
import gg.steve.mc.iiender.vt.tags.TagsManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ClearTagInventoryClickAction extends AbstractInventoryClickAction {

    public ClearTagInventoryClickAction() {
        super(GuiClickAction.CLEAR_TAG, "clear_tag", 1);
    }

    @Override
    public ItemStack getRenderedItem(Player player, ConfigurationSection section) {
        return GuiItemCreationUtil.createItem(section, TagsManager.getInstance().getActiveTagForPlayer(player));
    }

    @Override
    public void onClick(AbstractGui gui, Player player, ConfigurationSection section, int slot) {
        if (TagsManager.getInstance().hasTagSelected(player)) {
            TagsManager.getInstance().clearTag(player);
            gui.refresh();
        } else {
            ItemStack item = gui.getInventory().getItem(slot);
            gui.getInventory().setItem(slot, ((TagPageGui) gui).getNoTagSelectedItem());
            SoundUtil.playSound(Files.CONFIG.get(), "no-tag-selected", player);
            Bukkit.getScheduler().runTaskLater(VaultedTagsPlugin.getInstance(), () -> {
                gui.getInventory().setItem(slot, item);
            }, ((TagPageGui) gui).getNoTagSelectedItemDisplayLength());
//            player.closeInventory();
            DebugMessage.NO_TAG_SELECTED.message(player);
        }
    }
}
