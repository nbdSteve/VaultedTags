package gg.steve.mc.iiender.vt;

import gg.steve.mc.iiender.vt.framework.SetupManager;
import gg.steve.mc.iiender.vt.framework.utils.LogUtil;
import gg.steve.mc.iiender.vt.framework.yml.Files;
import gg.steve.mc.iiender.vt.framework.yml.utils.FileManagerUtil;
import gg.steve.mc.iiender.vt.license.AdvancedLicense;
import org.bukkit.plugin.Plugin;
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
        // setup licensing
        if (Files.CONFIG.get().getString("license") == null || Files.CONFIG.get().getString("license").equalsIgnoreCase("XXXX-XXXX-XXXX-XXXX")) {
            this.getServer().getPluginManager().disablePlugin(this);
            LogUtil.warning("\"license\" option in config is either null or default.");
            LogUtil.warning("Therefore the plugin will be disabled.");
            return;
        } else if (!new AdvancedLicense(Files.CONFIG.get().getString("license"), "https://vaulted.cc/license/verify.php", this).register()) {
            return;
        }
        // other setup
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
