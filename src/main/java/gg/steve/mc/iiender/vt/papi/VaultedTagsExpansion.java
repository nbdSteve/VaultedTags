package gg.steve.mc.iiender.vt.papi;

import gg.steve.mc.iiender.vt.VaultedTagsPlugin;
import gg.steve.mc.iiender.vt.framework.utils.ColorUtil;
import gg.steve.mc.iiender.vt.framework.yml.Files;
import gg.steve.mc.iiender.vt.tags.TagsManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class VaultedTagsExpansion extends PlaceholderExpansion {

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getAuthor() {
        return VaultedTagsPlugin.getInstance().getDescription().getAuthors().toString();
    }

    @Override
    public String getIdentifier() {
        return "vt";
    }

    @Override
    public String getVersion() {
        return VaultedTagsPlugin.getInstance().getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (identifier.equalsIgnoreCase("selected")) {
            if (TagsManager.getInstance().hasTagSelected(player)) {
                return ColorUtil.colorize(Files.CONFIG.get().getString("placeholders.tag.format")
                        .replace("{format}", TagsManager.getInstance().getActiveTagForPlayer(player))
                        .replace("{format_raw}", TagsManager.getInstance().getTagForPlayer(player).getId()));
            } else {
                return ColorUtil.colorize(Files.CONFIG.get().getString("placeholders.tag.none"));
            }
        } else if (identifier.equalsIgnoreCase("selected_chat")) {
            if (TagsManager.getInstance().hasTagSelected(player)) {
                return ColorUtil.colorize(Files.CONFIG.get().getString("placeholders.tag-chat.format")
                        .replace("{format}", TagsManager.getInstance().getActiveTagForPlayer(player))
                        .replace("{format_raw}", TagsManager.getInstance().getTagForPlayer(player).getId()));
            } else {
                return ColorUtil.colorize(Files.CONFIG.get().getString("placeholders.tag-chat.none"));
            }
        } else if (identifier.equalsIgnoreCase("selected_tab")) {
            if (TagsManager.getInstance().hasTagSelected(player)) {
                return ColorUtil.colorize(Files.CONFIG.get().getString("placeholders.tag-tab.format")
                        .replace("{format}", TagsManager.getInstance().getActiveTagForPlayer(player))
                        .replace("{format_raw}", TagsManager.getInstance().getTagForPlayer(player).getId()));
            } else {
                return ColorUtil.colorize(Files.CONFIG.get().getString("placeholders.tag-tab.none"));
            }
        } else if (identifier.equalsIgnoreCase("selected_identifier")) {
            if (TagsManager.getInstance().hasTagSelected(player)) {
                return ColorUtil.colorize(Files.CONFIG.get().getString("placeholders.tag-identifier.format")
                        .replace("{format}", TagsManager.getInstance().getActiveTagForPlayer(player))
                        .replace("{format_raw}", TagsManager.getInstance().getTagForPlayer(player).getId()));
            } else {
                return ColorUtil.colorize(Files.CONFIG.get().getString("placeholders.tag-identifier.none"));
            }
        } else if (identifier.equalsIgnoreCase("selected_scoreboard")) {
            if (TagsManager.getInstance().hasTagSelected(player)) {
                return ColorUtil.colorize(Files.CONFIG.get().getString("placeholders.tag-scoreboard.format")
                        .replace("{format}", TagsManager.getInstance().getActiveTagForPlayer(player))
                        .replace("{format_raw}", TagsManager.getInstance().getTagForPlayer(player).getId()));
            } else {
                return ColorUtil.colorize(Files.CONFIG.get().getString("placeholders.tag-scoreboard.none"));
            }
        }
        return "Invalid Placeholder";
    }
}
