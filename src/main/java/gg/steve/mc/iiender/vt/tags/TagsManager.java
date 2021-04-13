package gg.steve.mc.iiender.vt.tags;

import gg.steve.mc.iiender.vt.VaultedTagsPlugin;
import gg.steve.mc.iiender.vt.db.TagDBUtil;
import gg.steve.mc.iiender.vt.framework.AbstractManager;
import gg.steve.mc.iiender.vt.framework.utils.LogUtil;
import gg.steve.mc.iiender.vt.framework.yml.Files;
import gg.steve.mc.iiender.vt.framework.yml.utils.YamlFileUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public class TagsManager extends AbstractManager {
    private static TagsManager instance;
    private List<Category> categories;
    private Map<UUID, Tag> playerTags;

    @Override
    public void onLoad() {
        instance = this;
        this.categories = new ArrayList<>();
        for (String entry : Files.CONFIG.get().getStringList("categories")) {
            YamlConfiguration config = new YamlFileUtil().load("categories" + File.separator + entry + ".yml", VaultedTagsPlugin.getInstance()).get();
            this.categories.add(new Category(entry, config));
        }
        LogUtil.info("Loaded " + this.categories.size() + " categorys");
        this.playerTags = new HashMap<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (TagDBUtil.hasTagSelected(player.getUniqueId())) {
                this.playerTags.put(player.getUniqueId(), getTagById(TagDBUtil.getSelectedTagForPlayer(player.getUniqueId())));
            }
        }
    }

    @Override
    public void onShutdown() {
        if (this.playerTags != null && !this.playerTags.isEmpty()) {
//            for (UUID playerId : this.playerTags.keySet()) {
//                TagDBUtil.setSelectedTagForPlayer(playerId, playerTags.get(playerId).getId());
//            }
            this.playerTags.clear();
        }
        if (this.categories != null && !this.categories.isEmpty()) this.categories.clear();
    }

    public static TagsManager getInstance() {
        return instance;
    }

    public Category getCategoryById(String categoryId) {
        for (Category category : this.categories) {
            if (category.getId().equalsIgnoreCase(categoryId)) return category;
        }
        return null;
    }

    public Tag getTagById(String tagId) {
        for (Category category : this.categories) {
            for (Tag tag : category.getTags()) {
                if (tag.getId().equalsIgnoreCase(tagId)) return tag;
            }
        }
        return null;
    }

    public Tag getTagForPlayer(Player player) {
        if (!this.hasTagSelected(player)) return null;
        return this.playerTags.get(player.getUniqueId());
    }

    public boolean canUseTag(Player player, String tagId) {
        return player.hasPermission(getTagById(tagId).getPermission());
    }

    public boolean hasTagSelected(Player player) {
        if (this.playerTags == null || this.playerTags.isEmpty()) return false;
        return this.playerTags.containsKey(player.getUniqueId()) || TagDBUtil.hasTagSelected(player.getUniqueId());
    }

    public boolean setTag(Player player, String tagId) {
        if (this.hasTagSelected(player)) this.clearTag(player);
        TagDBUtil.setSelectedTagForPlayer(player.getUniqueId(), tagId);
        return this.playerTags.put(player.getUniqueId(), this.getTagById(tagId)) != null;
    }

    public boolean clearTag(Player player) {
        if (TagDBUtil.hasTagSelected(player.getUniqueId())) {
            TagDBUtil.deleteSelectedTagForPlayer(player.getUniqueId());
        }
        return this.playerTags.remove(player.getUniqueId()) != null;
    }
}
