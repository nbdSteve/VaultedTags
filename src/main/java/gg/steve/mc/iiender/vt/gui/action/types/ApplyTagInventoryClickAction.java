package gg.steve.mc.iiender.vt.gui.action.types;

import gg.steve.mc.iiender.vt.framework.utils.LogUtil;
import gg.steve.mc.iiender.vt.gui.AbstractGui;
import gg.steve.mc.iiender.vt.gui.action.AbstractInventoryClickAction;
import gg.steve.mc.iiender.vt.gui.action.GuiClickAction;
import gg.steve.mc.iiender.vt.gui.utils.GuiItemCreationUtil;
import gg.steve.mc.iiender.vt.tags.Tag;
import gg.steve.mc.iiender.vt.tags.TagsManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ApplyTagInventoryClickAction extends AbstractInventoryClickAction {

    public ApplyTagInventoryClickAction() {
        super(GuiClickAction.APPLY_TAG, "apply_tag", 2);
    }

    @Override
    public ItemStack getRenderedItem(Player player, ConfigurationSection section) {
//        Tag tag = TagsManager.getInstance().getTagById(getTagId(section.getString("action")));
//        if (TagsManager.getInstance().hasTagSelected(player)
//                && TagsManager.getInstance().getTagForPlayer(player).getId().equalsIgnoreCase(tag.getId())) {
//            return GuiItemCreationUtil.createItem(section.getConfigurationSection("selected"));
//        } else if (TagsManager.getInstance().canUseTag(player, tag.getId())) {
//            return GuiItemCreationUtil.createItem(section.getConfigurationSection("available"));
//        } else {
//            return GuiItemCreationUtil.createItem(section.getConfigurationSection("not-available"));
//        }
        return new ItemStack(Material.STONE);
    }

    public ItemStack getRenderedItem(Player player, ConfigurationSection section, Tag tag) {
        if (TagsManager.getInstance().hasTagSelected(player)
                && TagsManager.getInstance().getTagForPlayer(player).getId().equalsIgnoreCase(tag.getId())) {
            return GuiItemCreationUtil.createItem(section.getConfigurationSection("selected"));
        } else if (TagsManager.getInstance().canUseTag(player, tag.getId())) {
            return GuiItemCreationUtil.createItem(section.getConfigurationSection("available"));
        } else {
            return GuiItemCreationUtil.createItem(section.getConfigurationSection("not-available"));
        }
    }

    @Override
    public void onClick(AbstractGui gui, Player player, ConfigurationSection section) {
//        Tag tag = TagsManager.getInstance().getTagById(getTagId(section.getString("action")));
//        if (TagsManager.getInstance().hasTagSelected(player)
//                && TagsManager.getInstance().getTagForPlayer(player).getId().equalsIgnoreCase(tag.getId())) {
//            TagsManager.getInstance().clearTag(player);
//        } else if (TagsManager.getInstance().canUseTag(player, tag.getId())) {
//            TagsManager.getInstance().setTag(player, tag.getId());
//        } else {
//            player.closeInventory();
//            player.sendMessage(ChatColor.RED + "You need to purchase that tag");
//        }
    }

    public void onClick(AbstractGui gui, Player player, Tag tag) {
        if (TagsManager.getInstance().hasTagSelected(player)
                && TagsManager.getInstance().getTagForPlayer(player).getId().equalsIgnoreCase(tag.getId())) {
            TagsManager.getInstance().clearTag(player);
            gui.refresh();
        } else if (TagsManager.getInstance().canUseTag(player, tag.getId())) {
            TagsManager.getInstance().setTag(player, tag.getId());
            gui.refresh();
        } else {
            player.closeInventory();
            player.sendMessage(ChatColor.RED + "You need to purchase that tag");
        }
    }
}
