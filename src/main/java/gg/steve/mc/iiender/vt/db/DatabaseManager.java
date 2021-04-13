package gg.steve.mc.iiender.vt.db;

import gg.steve.mc.iiender.vt.db.implementations.MySQLInjector;
import gg.steve.mc.iiender.vt.db.implementations.SQLiteInjector;
import gg.steve.mc.iiender.vt.framework.AbstractManager;
import gg.steve.mc.iiender.vt.framework.utils.LogUtil;

public class DatabaseManager extends AbstractManager {
    private static DatabaseManager dbInstance;
    private static DatabaseInjector dbInjector;

    public DatabaseManager() {
        dbInstance = this;
        DatabaseImplementation implementation = DatabaseImplementation.getImplementationUsed();
        switch (implementation) {
            case MYSQL:
                dbInjector = new MySQLInjector();
                break;
            case SQLITE:
                dbInjector = new SQLiteInjector();
                break;
        }
    }

    @Override
    public void onLoad() {
        dbInjector.connect();
    }

    @Override
    public void onShutdown() {
        dbInjector.disconnect();
    }

    public static DatabaseManager getDbInstance() {
        if (dbInstance == null) {
            LogUtil.warning("DB INSTANCE IS NULL");
        }
        return dbInstance;
    }

    public static DatabaseInjector getDbInjector() {
        if (dbInstance == null) {
            LogUtil.warning("DB INSTANCE IS NULL");
        }
        if (dbInjector == null) {
            LogUtil.warning("DB INJECTOR IS NULL");
        }
        return dbInjector;
    }
}
