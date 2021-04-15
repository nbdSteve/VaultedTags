package gg.steve.mc.iiender.vt.gui;

import gg.steve.mc.iiender.vt.framework.Loadable;
import gg.steve.mc.iiender.vt.framework.utils.ColorUtil;
import gg.steve.mc.iiender.vt.gui.utils.GuiItemCreationUtil;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

@Data
public abstract class AbstractGui implements Listener, Loadable {
    private final UUID id;
    private final String guiId;
    private final JavaPlugin instance;
    private Inventory inventory;
    private boolean hasParentGui, playersCanTakeItems;
    private InventoryType type;
    private final int size;
    private Map<Integer, GuiClickableAction> actions;
    private List<UUID> viewers;
    private YamlConfiguration config;
    private String name;
    private Player owner;

    public AbstractGui(String guiId, YamlConfiguration config, JavaPlugin instance) {
        this.id = UUID.randomUUID();
        this.guiId = guiId;
        this.instance = instance;
        this.config = config;
        this.size = config.getInt("size");
        this.hasParentGui = config.getString("parent-gui").equalsIgnoreCase("none");
        this.playersCanTakeItems = config.getBoolean("players-can-take-items");
        this.name = ColorUtil.colorize(config.getString("name"));
        try {
            this.type = InventoryType.valueOf(config.getString("type").toUpperCase(Locale.ROOT));
            this.inventory = Bukkit.createInventory(null, this.type, this.name);
        } catch (Exception e) {
            this.inventory = Bukkit.createInventory(null, this.size, this.name);
        }
        this.actions = new HashMap<>();
        this.viewers = new ArrayList<>();
        instance.getServer().getPluginManager().registerEvents(this, instance);
        doFillers();
    }

    public abstract void refresh();

    public abstract AbstractGui clone();

    public void doFillers() {
        for (String entry : this.config.getConfigurationSection("fillers").getKeys(false)) {
            ConfigurationSection section = this.config.getConfigurationSection("fillers." + entry);
            List<Integer> slots = section.getIntegerList("slots");
            ItemStack filler = GuiItemCreationUtil.createItem(section);
            for (Integer slot : slots) {
                setItemInSlot(slot, filler, player -> {
                });
            }
        }
    }

    public void setItemInSlot(int slot, ItemStack item, GuiClickableAction action) {
        this.inventory.setItem(slot, item);
        if (action != null) this.actions.put(slot, action);
    }

    public void setOwner(Player player) {
        this.owner = player;
    }

    public void open() {
        if (this.owner.isOnline()) open(this.owner);
    }

    public void open(Player player) {
        refresh();
        this.viewers.add(player.getUniqueId());
        player.openInventory(this.inventory);
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onShutdown() {
        if (this.viewers != null && !this.viewers.isEmpty()) {
            this.viewers.forEach(uuid -> Bukkit.getPlayer(id).closeInventory());
            this.viewers.clear();
        }
        if (this.actions != null && !this.actions.isEmpty()) this.actions.clear();
    }

    @EventHandler
    public void guiItemClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (this.viewers.contains(player.getUniqueId())) {
            event.setCancelled(true);
            if (!this.actions.containsKey(event.getSlot())) return;
            this.actions.get(event.getSlot()).onClick(player);
        }
    }

    @EventHandler
    public void guiClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        this.viewers.remove(player.getUniqueId());
    }

    @EventHandler
    public void guiCloseByQuit(PlayerQuitEvent event) {
        Player player = (Player) event.getPlayer();
        this.viewers.remove(player.getUniqueId());
    }

    public interface GuiClickableAction {
        void onClick(Player player);
    }
}
