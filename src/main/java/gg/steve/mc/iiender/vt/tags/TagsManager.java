package gg.steve.mc.iiender.vt.tags;

import gg.steve.mc.iiender.vt.VaultedTagsPlugin;
import gg.steve.mc.iiender.vt.db.DatabaseManager;
import gg.steve.mc.iiender.vt.framework.AbstractManager;
import gg.steve.mc.iiender.vt.framework.message.GeneralMessage;
import gg.steve.mc.iiender.vt.framework.utils.ColorUtil;
import gg.steve.mc.iiender.vt.framework.utils.SoundUtil;
import gg.steve.mc.iiender.vt.framework.yml.Files;
import gg.steve.mc.iiender.vt.framework.yml.utils.YamlFileUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class TagsManager extends AbstractManager {
    private static TagsManager instance;
    private final List<Category> categories;
    private final Map<UUID, Tag> playerTags;

    public TagsManager() {
        instance = this;
        this.categories = new ArrayList<>();
        this.playerTags = new HashMap<>();
    }

    @Override
    public void onLoad() {
        File dataFolder = new File(VaultedTagsPlugin.getInstance().getDataFolder(), "categories");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        for (File guiFile : dataFolder.listFiles()) {
            YamlConfiguration config = new YamlFileUtil().load("categories" + File.separator + guiFile.getName(), VaultedTagsPlugin.getInstance()).get();
            String id = guiFile.getName().split(".yml")[0];
            this.categories.add(new Category(id, config));
        }
//        for (String entry : Files.CONFIG.get().getStringList("categories")) {
//            YamlConfiguration config = new YamlFileUtil().load("categories" + File.separator + entry + ".yml", VaultedTagsPlugin.getInstance()).get();
//            this.categories.add(new Category(entry, config));
//        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (DatabaseManager.hasTagSelected(player.getUniqueId())) {
                this.playerTags.put(player.getUniqueId(), getTagById(DatabaseManager.getSelectedTagForPlayer(player.getUniqueId())));
            }
        }
    }

    @Override
    public void onShutdown() {
        if (!this.playerTags.isEmpty()) {
            this.playerTags.clear();
        }
        if (!this.categories.isEmpty()) this.categories.clear();
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

    public String getActiveTagForPlayer(Player player) {
        if (!this.hasTagSelected(player)) return ColorUtil.colorize(Files.CONFIG.get().getString("no-tag-selected-placeholder"));
        return getTagForPlayer(player).getTag();
    }

    public boolean canUseTag(Player player, String tagId) {
        return player.hasPermission(getTagById(tagId).getPermission());
    }

    public boolean hasTagSelected(Player player) {
        if (this.playerTags.isEmpty()) return false;
        return this.playerTags.containsKey(player.getUniqueId()) || DatabaseManager.hasTagSelected(player.getUniqueId());
    }

    public boolean addTagPlayer(Player player) {
        return this.playerTags.put(player.getUniqueId(), getTagById(DatabaseManager.getSelectedTagForPlayer(player.getUniqueId()))) != null;
    }

    public boolean removeTagPlayer(Player player) {
        return this.playerTags.remove(player.getUniqueId()) != null;
    }

    public boolean setTag(Player player, String tagId) {
        if (this.hasTagSelected(player)) this.clearTag(player);
        DatabaseManager.setSelectedTagForPlayer(player.getUniqueId(), tagId);
        GeneralMessage.APPLY_TAG.message(player, getTagById(tagId).getTag());
        SoundUtil.playSound(Files.CONFIG.get(), "apply", player);
        return this.playerTags.put(player.getUniqueId(), this.getTagById(tagId)) != null;
    }

    public boolean clearTag(Player player) {
        if (DatabaseManager.hasTagSelected(player.getUniqueId())) {
            DatabaseManager.deleteSelectedTagForPlayer(player.getUniqueId());
        }
        SoundUtil.playSound(Files.CONFIG.get(), "clear", player);
        GeneralMessage.CLEAR_TAG.message(player, getTagForPlayer(player).getTag());
        return this.playerTags.remove(player.getUniqueId()) != null;
    }
}
