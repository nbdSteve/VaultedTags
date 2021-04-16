package gg.steve.mc.iiender.vt.gui.utils;

import gg.steve.mc.iiender.vt.framework.utils.ItemBuilderUtil;
import gg.steve.mc.iiender.vt.tags.Tag;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class GuiItemCreationUtil {

    public static ItemStack createItem(ConfigurationSection section) {
        ItemBuilderUtil builder = ItemBuilderUtil.getBuilderForMaterial(section.getString("material"), section.getString("data"));
        builder.addName(section.getString("name"));
        builder.addLore(section.getStringList("lore"));
        builder.addEnchantments(section.getStringList("enchantments"));
        builder.addItemFlags(section.getStringList("item-flags"));
        builder.addNBT(section.getBoolean("unbreakable"));
        return builder.getItem();
    }

    public static ItemStack createItem(ConfigurationSection section, Tag tag) {
        ItemBuilderUtil builder = ItemBuilderUtil.getBuilderForMaterial(section.getString("material"), section.getString("data"));
        builder.addName(section.getString("name"));
        builder.setLorePlaceholders("{format}");
        builder.addLore(section.getStringList("lore"), tag.getTag());
        builder.addEnchantments(section.getStringList("enchantments"));
        builder.addItemFlags(section.getStringList("item-flags"));
        builder.addNBT(section.getBoolean("unbreakable"));
        return builder.getItem();
    }

    public static ItemStack createItem(ConfigurationSection section, String tagString) {
        ItemBuilderUtil builder = ItemBuilderUtil.getBuilderForMaterial(section.getString("material"), section.getString("data"));
        builder.addName(section.getString("name"));
        builder.setLorePlaceholders("{format}");
        builder.addLore(section.getStringList("lore"), tagString);
        builder.addEnchantments(section.getStringList("enchantments"));
        builder.addItemFlags(section.getStringList("item-flags"));
        builder.addNBT(section.getBoolean("unbreakable"));
        return builder.getItem();
    }
}
