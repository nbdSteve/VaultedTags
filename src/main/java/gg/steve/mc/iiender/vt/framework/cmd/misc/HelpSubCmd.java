package gg.steve.mc.iiender.vt.framework.cmd.misc;

import gg.steve.mc.iiender.vt.VaultedTagsPlugin;
import gg.steve.mc.iiender.vt.framework.cmd.SubCommand;
import gg.steve.mc.iiender.vt.framework.message.GeneralMessage;
import gg.steve.mc.iiender.vt.framework.permission.PermissionNode;
import org.bukkit.command.CommandSender;

public class HelpSubCmd extends SubCommand {

    public HelpSubCmd() {
        super("help", 0, 1, false, PermissionNode.HELP);
        addAlias("h");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        GeneralMessage.HELP.message(sender, VaultedTagsPlugin.getInstance().getDescription().getVersion());
    }
}
