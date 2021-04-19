package gg.steve.mc.iiender.vt.gui.implementations;

import gg.steve.mc.iiender.vt.framework.utils.LogUtil;
import gg.steve.mc.iiender.vt.gui.AbstractGui;
import gg.steve.mc.iiender.vt.gui.action.GuiClickAction;
import gg.steve.mc.iiender.vt.gui.action.types.ApplyTagInventoryClickAction;
import gg.steve.mc.iiender.vt.gui.action.types.ClearTagInventoryClickAction;
import gg.steve.mc.iiender.vt.gui.utils.GuiItemCreationUtil;
import gg.steve.mc.iiender.vt.tags.Category;
import gg.steve.mc.iiender.vt.tags.Tag;
import gg.steve.mc.iiender.vt.tags.TagsManager;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class TagPageGui extends AbstractGui {
    private final Map<Integer, Tag> tags;
    private final ItemStack noPermissionItem, noTagSelectedItem;
    private final long noPermissionItemDisplayLength, noTagSelectedItemDisplayLength;

    public TagPageGui(String guiId, YamlConfiguration config, JavaPlugin instance) {
        super(guiId, config, instance);
        this.tags = new HashMap<>();
        loadCategoryTags();
        this.noPermissionItem = GuiItemCreationUtil.createItem(config.getConfigurationSection("no-permission-item"));
        this.noTagSelectedItem = GuiItemCreationUtil.createItem(config.getConfigurationSection("no-tag-selected-item"));
        this.noPermissionItemDisplayLength = config.getLong("no-permission-item.display-length");
        this.noTagSelectedItemDisplayLength = config.getLong("no-tag-selected-item.display-length");
    }

    @Override
    public void refresh() {
        loadCategoryTags();
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
        renderTags();
    }

    public void loadCategoryTags() {
        if (this.tags == null || this.tags.isEmpty()) {
            Category category = TagsManager.getInstance().getCategoryById(this.getConfig().getString("tags.category"));
            if (category == null) return;
            int i = 0;
            for (Tag tag : category.getTags()) {
                this.tags.put(i, tag);
                i++;
            }
        }
    }

    public void renderTags() {
        List<Integer> slots = this.getConfig().getIntegerList("tags.slots");
        int startIndex, finishIndex;
        startIndex = slots.size() * this.getPage();
        finishIndex = startIndex + slots.size();
        List<Tag> tagsToRender = new ArrayList<>();
        while (startIndex < finishIndex) {
            if (!this.tags.containsKey(startIndex)) break;
            tagsToRender.add(this.tags.get(startIndex));
            startIndex++;
        }
        if (tagsToRender.isEmpty()) return;
        int index = 0;
        for (Integer slot : slots) {
            if (slot - slots.get(0) >= tagsToRender.size()) {
                if (getInventory().getItem(slot).getType() != Material.AIR) {
                    setItemInSlot(slot, new ItemStack(Material.AIR), player -> {});
                }
                continue;
            }
            Tag tag = tagsToRender.get(index);
            setItemInSlot(slot, ((ApplyTagInventoryClickAction) GuiClickAction.APPLY_TAG.getAction()).getRenderedItem(this.getOwner(), tag.getCategory().getConfig().getConfigurationSection(tag.getId() + ".gui"), tag), player -> {
                ((ApplyTagInventoryClickAction) GuiClickAction.APPLY_TAG.getAction()).onClick(this, this.getOwner(), tag, slot);
            });
            index++;
        }
    }

    @Override
    public AbstractGui clone() {
        return new TagPageGui(this.getGuiId(), this.getConfig(), this.getInstance());
    }

    public ItemStack getNoPermissionItem() {
        return noPermissionItem;
    }

    public ItemStack getNoTagSelectedItem() {
        return noTagSelectedItem;
    }

    public long getNoPermissionItemDisplayLength() {
        return noPermissionItemDisplayLength;
    }

    public long getNoTagSelectedItemDisplayLength() {
        return noTagSelectedItemDisplayLength;
    }
}