package gg.steve.mc.iiender.vt.papi;

import gg.steve.mc.iiender.vt.VaultedTagsPlugin;
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
        return "sf";
    }

    @Override
    public String getVersion() {
        return VaultedTagsPlugin.getInstance().getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        return "";
    }
}
