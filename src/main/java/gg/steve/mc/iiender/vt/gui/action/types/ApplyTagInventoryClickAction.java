package gg.steve.mc.iiender.vt.gui.action.types;

import gg.steve.mc.iiender.vt.VaultedTagsPlugin;
import gg.steve.mc.iiender.vt.framework.utils.SoundUtil;
import gg.steve.mc.iiender.vt.framework.yml.Files;
import gg.steve.mc.iiender.vt.gui.AbstractGui;
import gg.steve.mc.iiender.vt.gui.action.AbstractInventoryClickAction;
import gg.steve.mc.iiender.vt.gui.action.GuiClickAction;
import gg.steve.mc.iiender.vt.gui.implementations.TagPageGui;
import gg.steve.mc.iiender.vt.gui.utils.GuiItemCreationUtil;
import gg.steve.mc.iiender.vt.tags.Tag;
import gg.steve.mc.iiender.vt.tags.TagsManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ApplyTagInventoryClickAction extends AbstractInventoryClickAction {

    public ApplyTagInventoryClickAction() {
        super(GuiClickAction.APPLY_TAG, "apply_tag", 2);
    }

    public ItemStack getRenderedItem(Player player, ConfigurationSection section, Tag tag) {
        if (TagsManager.getInstance().hasTagSelected(player)
                && TagsManager.getInstance().getTagForPlayer(player).getId().equalsIgnoreCase(tag.getId())) {
            return GuiItemCreationUtil.createItem(section.getConfigurationSection("selected"), tag);
        } else if (TagsManager.getInstance().canUseTag(player, tag.getId())) {
            return GuiItemCreationUtil.createItem(section.getConfigurationSection("available"), tag);
        } else {
            return GuiItemCreationUtil.createItem(section.getConfigurationSection("not-available"), tag);
        }
    }

    @Override
    public void onClick(AbstractGui gui, Player player, ConfigurationSection section, int slot) {
    }

    public void onClick(AbstractGui gui, Player player, Tag tag, int slot) {
        if (TagsManager.getInstance().hasTagSelected(player)
                && TagsManager.getInstance().getTagForPlayer(player).getId().equalsIgnoreCase(tag.getId())) {
            TagsManager.getInstance().clearTag(player);
            gui.refresh();
        } else if (TagsManager.getInstance().canUseTag(player, tag.getId())) {
            TagsManager.getInstance().setTag(player, tag.getId());
            gui.refresh();
        } else {
            ItemStack item = gui.getInventory().getItem(slot);
            gui.getInventory().setItem(slot, ((TagPageGui) gui).getNoPermissionItem());
            SoundUtil.playSound(Files.CONFIG.get(), "no-permission", player);
            Bukkit.getScheduler().runTaskLater(VaultedTagsPlugin.getInstance(), () -> {
                gui.getInventory().setItem(slot, item);
            }, ((TagPageGui) gui).getNoPermissionItemDisplayLength());
        }
    }
}
