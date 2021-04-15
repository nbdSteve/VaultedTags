package gg.steve.mc.iiender.vt.tags;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Tag {
    private final String id, permission, tag;
    private final Category category;

    public Tag(String id, String permission, String tag, Category category) {
        this.id = id;
        this.permission = permission;
        this.tag = tag;
        this.category = category;
    }

    public boolean hasTag(Player player) {
        return player.hasPermission(this.permission);
    }

    public String getId() {
        return id;
    }

    public String getPermission() {
        return permission;
    }

    public String getTag() {
        return tag;
    }

    public Category getCategory() {
        return category;
    }
}
