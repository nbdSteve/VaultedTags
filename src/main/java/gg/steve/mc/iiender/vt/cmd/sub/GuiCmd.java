package gg.steve.mc.iiender.vt.cmd.sub;

import gg.steve.mc.iiender.vt.framework.cmd.SubCommand;
import gg.steve.mc.iiender.vt.framework.permission.PermissionNode;
import gg.steve.mc.iiender.vt.gui.GuiManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuiCmd extends SubCommand {

    public GuiCmd() {
        super("gui", 0, 1, true, PermissionNode.GUI);
        this.addAlias("gui");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        GuiManager.getInstance().openGui((Player) sender, "menu");
    }
}
