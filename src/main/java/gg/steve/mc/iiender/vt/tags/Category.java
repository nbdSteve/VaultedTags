package gg.steve.mc.iiender.vt.tags;

import gg.steve.mc.iiender.vt.framework.utils.ColorUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private final String id;
    private final YamlConfiguration config;
    private List<Tag> tags;
    //private Gui gui;

    public Category(String id, YamlConfiguration config) {
        this.id = id;
        this.config = config;
        this.tags = new ArrayList<>();
        for (String entry : config.getKeys(false)) {
            String permission = config.getString(entry + ".permission-node");
            String actualTag = ColorUtil.colorize(config.getString(entry + ".tag"));
            tags.add(new Tag(entry, permission, actualTag, this));
        }
        loadGui();
    }

    public void loadGui() {}

    public String getId() {
        return id;
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public List<Tag> getTags() {
        return tags;
    }
}
