package gg.steve.mc.iiender.vt.gui.action;

import gg.steve.mc.iiender.vt.framework.utils.LogUtil;
import gg.steve.mc.iiender.vt.gui.action.types.*;

public enum GuiClickAction {
    OPEN(new OpenInventoryClickAction()),
    CLOSE(new CloseInventoryClickAction()),
    CLEAR_TAG(new ClearTagInventoryClickAction()),
    APPLY_TAG(new ApplyTagInventoryClickAction()),
    PAGE(new PageInventoryClickAction()),
    BACK(new BackInventoryClickAction()),
    PERMISSION(new PermissionInventoryClickAction());

    private final AbstractInventoryClickAction action;

    GuiClickAction(AbstractInventoryClickAction action) {
        this.action = action;
    }

    public static GuiClickAction getActionFromString(String actionFromConfig) {
        String parse = actionFromConfig;
        if (actionFromConfig.contains(":")) {
            String[] parts = actionFromConfig.split(":");
            parse = parts[0];
        }
        try {
            return GuiClickAction.valueOf(parse.toUpperCase());
        } catch (Exception e) {
            LogUtil.warning("Invalid click action type detected.");
        }
        return null;
    }

    public AbstractInventoryClickAction getAction() {
        return action;
    }
}
