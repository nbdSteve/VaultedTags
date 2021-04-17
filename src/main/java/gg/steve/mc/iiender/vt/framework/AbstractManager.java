package gg.steve.mc.iiender.vt.framework;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractManager implements Loadable {
    private static List<AbstractManager> managers;

    private static void initialiseManagerList() {
        if (managers == null) managers = new LinkedList<>();
    }

    private static void addManager(AbstractManager manager) {
        initialiseManagerList();
        if (managers.contains(manager)) return;
        managers.add(manager);
    }

    public static void loadManagers() {
        if (managers == null || managers.isEmpty()) return;
        for (AbstractManager manager : managers) {
            manager.onLoad();
        }
    }

    public static void shutdownManagers() {
        if (managers == null || managers.isEmpty()) return;
        for (int i = managers.size() - 1; i >= 0; i--) {
            managers.get(i).onShutdown();
        }
        managers.clear();
//        for (AbstractManager manager : managers.re) {
//            manager.onShutdown();
//        }
    }

    public static List<AbstractManager> getActiveManagers() {
        initialiseManagerList();
        return managers;
    }

    public AbstractManager() {
        addManager(this);
    }
}
