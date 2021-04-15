package gg.steve.mc.iiender.vt.gui.implementations;

import gg.steve.mc.iiender.vt.framework.utils.LogUtil;
import gg.steve.mc.iiender.vt.gui.AbstractGui;
import gg.steve.mc.iiender.vt.gui.action.GuiClickAction;
import gg.steve.mc.iiender.vt.gui.action.types.ApplyTagInventoryClickAction;
import gg.steve.mc.iiender.vt.tags.Category;
import gg.steve.mc.iiender.vt.tags.Tag;
import gg.steve.mc.iiender.vt.tags.TagsManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class TagPageGui extends AbstractGui {
    private final Map<Integer, Tag> tags;

    public TagPageGui(String guiId, YamlConfiguration config, JavaPlugin instance) {
        super(guiId, config, instance);
        this.tags = new HashMap<>();
        Category category = TagsManager.getInstance().getCategoryById(config.getString("tags.category"));
        int i = 0;
        for (Tag tag : category.getTags()) {
            this.tags.put(i, tag);
            i++;
        }
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
                    action.getAction().onClick(this, player, this.getConfig().getConfigurationSection(entry));
                });
            }
        }
        renderTags();
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
            if (slot >= tagsToRender.size()) return;
            Tag tag = tagsToRender.get(index);
            setItemInSlot(slot, ((ApplyTagInventoryClickAction) GuiClickAction.APPLY_TAG.getAction()).getRenderedItem(this.getOwner(), tag.getCategory().getConfig().getConfigurationSection(tag.getId() + ".gui"), tag), player -> {
                ((ApplyTagInventoryClickAction) GuiClickAction.APPLY_TAG.getAction()).onClick(this, this.getOwner(), tag);
            });
            index++;
        }
    }

    @Override
    public AbstractGui clone() {
        return new TagPageGui(this.getGuiId(), this.getConfig(), this.getInstance());
    }
}