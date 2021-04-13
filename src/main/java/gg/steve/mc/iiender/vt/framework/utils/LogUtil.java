package gg.steve.mc.iiender.vt.framework.utils;

import gg.steve.mc.iiender.vt.VaultedTagsPlugin;

public class LogUtil {

    public static void info(String message) {
        VaultedTagsPlugin.getInstance().getLogger().info(message);
    }

    public static void warning(String message) {
        VaultedTagsPlugin.getInstance().getLogger().warning(message);
    }
}
