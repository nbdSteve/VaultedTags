package gg.steve.mc.iiender.vt.cmd;

import gg.steve.mc.iiender.vt.tags.TagsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        TagsManager.getInstance().setTag(player, args[0]);
        player.sendMessage(TagsManager.getInstance().getTagForPlayer(player).getTag());
        return true;
    }
}
