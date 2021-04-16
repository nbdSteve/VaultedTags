package gg.steve.mc.iiender.vt.framework.cmd;

import gg.steve.mc.iiender.vt.cmd.sub.GuiCmd;
import gg.steve.mc.iiender.vt.framework.cmd.misc.HelpSubCmd;
import gg.steve.mc.iiender.vt.framework.cmd.misc.ReloadSubCmd;

public enum SubCommandType {
    HELP_CMD(new HelpSubCmd()),
    RELOAD_CMD(new ReloadSubCmd()),
    GUI_CMD(new GuiCmd());

    private SubCommand sub;

    SubCommandType(SubCommand sub) {
        this.sub = sub;
    }

    public SubCommand getSub() {
        return sub;
    }
}
