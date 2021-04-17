package gg.steve.mc.iiender.vt.framework.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import gg.steve.mc.iiender.vt.framework.nbt.NBTItem;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Class that handles creating and item
 */
public class ItemBuilderUtil {
    private Material material;
    private Short dataValue;
    private ItemStack item;
    private ItemMeta itemMeta;
    private List<String> lore = new ArrayList<>();
    private Set<ItemFlag> flags = new HashSet<>();
    private List<String> placeholders = new ArrayList<>();
    private NBTItem nbtItem;

    public static ItemBuilderUtil getBuilderForMaterial(String material, String data) {
        if (material.startsWith("hdb")) {
            String[] parts = material.split("-");
            if (Bukkit.getPluginManager().getPlugin("HeadDatabase") != null) {
                try {
                    return new ItemBuilderUtil(new HeadDatabaseAPI().getItemHead(parts[1]));
                } catch (NullPointerException e) {
                    try {
                        return new ItemBuilderUtil(new ItemStack(Material.valueOf("SKULL_ITEM")));
                    } catch (Exception e1) {
                        return new ItemBuilderUtil(new ItemStack(Material.valueOf("LEGACY_SKULL_ITEM")));
                    }
                }
            } else {
                LogUtil.warning("Tried to create a custom head but the plugin HeadDatabase is not installed, setting to default skull.");
                try {
                    return new ItemBuilderUtil(new ItemStack(Material.valueOf("SKULL_ITEM")));
                } catch (Exception e1) {
                    return new ItemBuilderUtil(new ItemStack(Material.valueOf("LEGACY_SKULL_ITEM")));
                }
            }
        } else if (material.startsWith("head")) {
            String[] parts = material.split("-");
            ItemStack item;
            try {
                item = new ItemStack(Material.valueOf("PLAYER_HEAD"));
            } catch (Exception e){
                item = new ItemStack(Material.valueOf("LEGACY_SKULL_ITEM"));
            }
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            GameProfile profile = new GameProfile(UUID.randomUUID(), "");
            profile.getProperties().put("textures", new Property("textures", parts[1]));
            Field profileField;
            try {
                profileField = meta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(meta, profile);
            }
            catch (Exception e) {
                LogUtil.info("Error assigning custom texture to skull item.");
                e.printStackTrace();
            }
            if (parts[1].length() <= 16) {
                meta.setOwner(parts[1]);
            }
            item.setItemMeta(meta);
            return new ItemBuilderUtil(item);
        }
        try {
            return new ItemBuilderUtil(material, data);
        } catch (Exception e) {
            return new ItemBuilderUtil("LEGACY_" + material, data);
        }
    }

    public ItemBuilderUtil(ItemStack item) {
        this.item = item;
        this.material = item.getType();
        this.dataValue = item.getDurability();
        this.itemMeta = item.getItemMeta();
        this.lore = item.getItemMeta().getLore();
        this.flags = item.getItemMeta().getItemFlags();
    }

    public ItemBuilderUtil(String material, String dataValue) {
        this.material = Material.getMaterial(material.toUpperCase());
        this.dataValue = Short.parseShort(dataValue);
        this.item = new ItemStack(this.material, 1, this.dataValue);
        this.itemMeta = item.getItemMeta();
    }

    public void addName(String name) {
        itemMeta.setDisplayName(ColorUtil.colorize(name));
        item.setItemMeta(itemMeta);
    }

    public void setLorePlaceholders(String... placeholder) {
        this.placeholders = Arrays.asList(placeholder);
    }

    public void addLore(List<String> lore, String... replacement) {
        if (this.lore == null) this.lore = new ArrayList<>();
        List<String> replacements = Arrays.asList(replacement);
        for (String line : lore) {
            for (int i = 0; i < this.placeholders.size(); i++) {
                line = line.replace(this.placeholders.get(i), replacements.get(i));
            }
            this.lore.add(ColorUtil.colorize(line));
        }
        itemMeta.setLore(this.lore);
        item.setItemMeta(itemMeta);
    }

    public void addEnchantments(List<String> enchants) {
        for (String enchantment : enchants) {
            String[] enchantmentParts = enchantment.split(":");
            itemMeta.addEnchant(Enchantment.getByName(enchantmentParts[0].toUpperCase()),
                    Integer.parseInt(enchantmentParts[1]), true);
        }
        item.setItemMeta(itemMeta);
    }

    public void addItemFlags(List<String> itemFlags) {
        for (String flag : itemFlags) {
            itemMeta.addItemFlags(ItemFlag.valueOf(flag.toUpperCase()));
            this.flags.add(ItemFlag.valueOf(flag.toUpperCase()));
        }
        item.setItemMeta(itemMeta);
    }

    public void addNBT(boolean unbreakable) {
        nbtItem = new NBTItem(item);
        if (unbreakable) {
            nbtItem.setBoolean("Unbreakable", true);
        }
    }

    public void setItemMeta(ItemMeta itemMeta) {
        this.itemMeta = itemMeta;
    }

    public Material getMaterial() {
        return material;
    }

    public NBTItem getNbtItem() {
        return nbtItem;
    }

    public ItemStack getItem() {
        if (this.nbtItem != null) {
            return this.nbtItem.getItem();
        }
        return this.item;
    }

    public ItemMeta getItemMeta() {
        return itemMeta;
    }

    public List<String> getLore() {
        return lore;
    }

    public Map<Enchantment, Integer> getEnchantments() {
        return itemMeta.getEnchants();
    }

    public Set<ItemFlag> getFlags() {
        return flags;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
        this.itemMeta.setLore(lore);
    }
}