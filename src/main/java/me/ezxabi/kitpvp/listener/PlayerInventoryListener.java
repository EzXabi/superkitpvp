package me.ezxabi.kitpvp.listener;

import me.ezxabi.kitpvp.SuperKitPvP;
import me.ezxabi.kitpvp.manager.ConfigManager;
import me.ezxabi.kitpvp.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInventoryListener implements Listener {

    private final SuperKitPvP plugin;

    public PlayerInventoryListener(SuperKitPvP plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void close(InventoryClickEvent event) {

        Player p = (Player) event.getWhoClicked();
        if (p.getOpenInventory().getTopInventory().getTitle().equalsIgnoreCase("Selecteer jouw kit!")) {
            event.setCancelled(true);
            if (event.getClickedInventory().getTitle().equalsIgnoreCase("Selecteer jouw kit!")) {

                String displayName = event.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§", "&");
                ConfigManager configManager = plugin.getConfigManager();
                FileConfiguration users = configManager.getUsers();

                for (String key : configManager.getKits().getKeys(false)) {
                    if (displayName.equalsIgnoreCase(configManager.getKits().getString(key + ".options.kitname"))) {
                        for (String item : configManager.getKits().getStringList(key + ".items")) {
                            if (item == null) continue;
                            //Check als je niet 2 kits hebt
                            if (!event.getWhoClicked().getInventory().contains(Material.valueOf(item))) {
                                if (item.contains("HELMET")) {
                                    ItemStack it = new ItemStack(Material.valueOf(item));
                                    p.getInventory().setHelmet(it);
                                }
                                if (item.contains("CHESTPLATE")) {
                                    ItemStack it = new ItemStack(Material.valueOf(item));
                                    p.getInventory().setChestplate(it);
                                }
                                if (item.contains("LEGGINGS")) {
                                    ItemStack it = new ItemStack(Material.valueOf(item));
                                    p.getInventory().setLeggings(it);
                                }
                                if (item.contains("BOOTS")) {
                                    ItemStack it = new ItemStack(Material.valueOf(item));
                                    p.getInventory().setBoots(it);
                                }
                                if (!item.contains("HELMET") && !item.contains("CHESTPLATE") && !item.contains("LEGGINGS") && !item.contains("BOOTS")) {
                                    ItemStack it = new ItemStack(Material.valueOf(item));
                                    p.getInventory().addItem(it);
                                }
                            } else {
                                ItemStack it = new ItemStack(Material.valueOf(item));

                                p.closeInventory();

                                double spawnX = users.getDouble("pvp.x");
                                double spawnY = users.getDouble("pvp.y");
                                double spawnZ = users.getDouble("pvp.z");
                                String spawnWorld = users.getString("pvp.world");
                                float yaw = (float) users.getDouble("pvp.yaw");
                                float pitch = (float) users.getDouble("pvp.pitch");

                                Location spawnLocation = new Location(Bukkit.getWorld(spawnWorld), spawnX, spawnY, spawnZ, yaw, pitch);
                                event.getWhoClicked().teleport(spawnLocation);
                            }
                        }
                        if (users.getDouble("pvp.x") != 0.0
                                && users.getDouble("pvp.y") != 0.0
                                && users.getDouble("pvp.z") != 0.0
                                && users.getString("pvp.world") != ""
                                && users.getDouble("pvp.yaw") != 0.0
                                && users.getDouble("pvp.pitch") != 0.0) {
                            event.getWhoClicked().closeInventory();
                            double spawnX = users.getDouble("pvp.x");
                            double spawnY = users.getDouble("pvp.y");
                            double spawnZ = users.getDouble("pvp.z");
                            String spawnWorld = users.getString("pvp.world");
                            float yaw = (float) users.getDouble("pvp.yaw");
                            float pitch = (float) users.getDouble("pvp.pitch");

                            Location spawnLocation = new Location(Bukkit.getWorld(spawnWorld), spawnX, spawnY, spawnZ, yaw, pitch);
                            event.getWhoClicked().teleport(spawnLocation);
                        } else {
                            p.sendMessage(Utils.col("&cYou have not set up a spawn yet!"));
                        }
                    }
                }
            }
        }
    }
}