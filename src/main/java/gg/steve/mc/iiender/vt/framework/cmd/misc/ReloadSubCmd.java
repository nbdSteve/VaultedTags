package gg.steve.mc.iiender.vt.framework.cmd.misc;

import gg.steve.mc.iiender.vt.VaultedTagsPlugin;
import gg.steve.mc.iiender.vt.framework.cmd.SubCommand;
import gg.steve.mc.iiender.vt.framework.message.GeneralMessage;
import gg.steve.mc.iiender.vt.framework.permission.PermissionNode;
import gg.steve.mc.iiender.vt.framework.yml.Files;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class ReloadSubCmd extends SubCommand {

    public ReloadSubCmd() {
        super("reload", 1, 1, false, PermissionNode.RELOAD);
        addAlias("r");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Files.reload();
        Bukkit.getPluginManager().disablePlugin(VaultedTagsPlugin.getInstance());
        Bukkit.getPluginManager().enablePlugin(VaultedTagsPlugin.getInstance());
        GeneralMessage.RELOAD.message(sender, VaultedTagsPlugin.getInstance().getDescription().getVersion());
    }
}
