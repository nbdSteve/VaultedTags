package gg.steve.mc.iiender.vt.db;

import gg.steve.mc.iiender.vt.framework.yml.Files;

import java.util.Locale;

public enum DatabaseImplementation {
    SQLITE(),
    MYSQL(),;

    public static DatabaseImplementation getImplementationUsed() {
        try {
            return DatabaseImplementation.valueOf(Files.CONFIG.get().getString("database.implementation").toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            return SQLITE;
        }
    }
}
