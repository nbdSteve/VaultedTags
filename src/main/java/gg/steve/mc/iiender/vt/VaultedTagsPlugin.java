package gg.steve.mc.iiender.vt;

import gg.steve.mc.iiender.vt.framework.SetupManager;
import gg.steve.mc.iiender.vt.framework.utils.LogUtil;
import gg.steve.mc.iiender.vt.framework.yml.utils.FileManagerUtil;
import org.bukkit.plugin.java.JavaPlugin;

public final class VaultedTagsPlugin extends JavaPlugin {
    private static VaultedTagsPlugin instance;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        // setup plugin
        SetupManager.setupFiles(new FileManagerUtil(instance));
        SetupManager.registerCommands(instance);
        SetupManager.registerEvents(instance);
        // this method loads things like modules, tools, maps and data lists
        SetupManager.loadPluginCache();
        // register placeholder expansion if PAPI is present
        SetupManager.registerPlaceholderExpansions(instance);
        LogUtil.info("Thanks for using VaultedTags v" + getDescription().getVersion() + ", please contact nbdSteve#0583 on discord if you find any bugs.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        SetupManager.shutdownPluginCache();
        LogUtil.info("Thanks for using VaultedTags v" + getDescription().getVersion() + ", please contact nbdSteve#0583 on discord if you find any bugs.");
    }

    public static VaultedTagsPlugin getInstance() {
        return instance;
    }
}
