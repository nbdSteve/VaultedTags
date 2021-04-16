package gg.steve.mc.iiender.vt.cmd;

import gg.steve.mc.iiender.vt.framework.cmd.SubCommandType;
import gg.steve.mc.iiender.vt.framework.message.DebugMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

public class TagsCmd implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (!SubCommandType.GUI_CMD.getSub().isValidCommand(sender, args)) return true;
            SubCommandType.GUI_CMD.getSub().onCommand(sender, args);
            return true;
        }
        for (SubCommandType subCommand : SubCommandType.values()) {
            if (!subCommand.getSub().isExecutor(args[0])) continue;
            if (!subCommand.getSub().isValidCommand(sender, args)) return true;
            subCommand.getSub().onCommand(sender, args);
            return true;
        }
        DebugMessage.INVALID_COMMAND.message(sender);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completion = new ArrayList<>();
        completion.add("help");
        completion.add("reload");
        completion.add("gui");
        return completion;
    }
}
