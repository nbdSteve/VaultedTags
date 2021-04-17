package gg.steve.mc.iiender.vt.gui;

import gg.steve.mc.iiender.vt.VaultedTagsPlugin;
import gg.steve.mc.iiender.vt.framework.AbstractManager;
import gg.steve.mc.iiender.vt.framework.utils.LogUtil;
import gg.steve.mc.iiender.vt.framework.yml.utils.YamlFileUtil;
import gg.steve.mc.iiender.vt.gui.implementations.GenericGui;
import gg.steve.mc.iiender.vt.gui.implementations.TagPageGui;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public class GuiManager extends AbstractManager {
    private static GuiManager instance;
    private Map<String, AbstractGui> guis;
    private Map<UUID, List<AbstractGui>> playerGuis;

    public GuiManager() {
        instance = this;
        this.guis = new HashMap<>();
        this.playerGuis = new HashMap<>();
    }

    @Override
    public void onLoad() {
        File dataFolder = new File(VaultedTagsPlugin.getInstance().getDataFolder(), "guis");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        for (File guiFile : dataFolder.listFiles()) {
            YamlConfiguration config = new YamlFileUtil().load("guis" + File.separator + guiFile.getName(), VaultedTagsPlugin.getInstance()).get();
            String id = guiFile.getName().split(".yml")[0];
            if (config.getBoolean("is-tag-gui")) {
                this.guis.put(id, new TagPageGui(id, config, VaultedTagsPlugin.getInstance()));
            } else this.guis.put(id, new GenericGui(id, config, VaultedTagsPlugin.getInstance()));
        }
    }

    @Override
    public void onShutdown() {
        if (this.guis != null && !this.guis.isEmpty()) {
            this.guis.forEach((s, gui) -> {
                gui.onShutdown();
            });
            this.guis.clear();
        }
        if (this.playerGuis != null && !this.playerGuis.isEmpty()) this.playerGuis.clear();
    }

    public static GuiManager getInstance() {
        return instance;
    }

    public boolean guiExists(String guiId) {
        if (guis == null || guis.isEmpty()) return false;
        return guis.containsKey(guiId);
    }

    public void removePlayer(Player player) {
        this.playerGuis.remove(player.getUniqueId());
    }

    public void openGui(Player player, String guiId) {
        if (!this.guiExists(guiId)) return;
        if (this.playerGuis.containsKey(player.getUniqueId())) {
            for (AbstractGui gui : this.playerGuis.get(player.getUniqueId())) {
                if (gui.getGuiId().equalsIgnoreCase(guiId)) {
                    gui.open();
                    return;
                }
            }
        } else {
            this.playerGuis.put(player.getUniqueId(), new ArrayList<>());
        }
        AbstractGui gui = this.guis.get(guiId).clone();
        gui.setOwner(player);
        this.playerGuis.get(player.getUniqueId()).add(gui);
        gui.open();
    }
}
