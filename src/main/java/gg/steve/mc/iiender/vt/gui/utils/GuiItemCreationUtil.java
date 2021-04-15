package gg.steve.mc.iiender.vt.gui.utils;

import gg.steve.mc.iiender.vt.framework.utils.ItemBuilderUtil;
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
}
