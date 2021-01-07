package ezxabi.kitpvp.listener;

import ezxabi.kitpvp.config.ConfigManager;
import ezxabi.kitpvp.inventories.KitMenu;
import ezxabi.kitpvp.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerInventoryListener implements Listener {

    @EventHandler
    public void close(InventoryClickEvent event) {

        Player p = (Player) event.getWhoClicked();
        if (p.getOpenInventory().getTopInventory().getTitle().equalsIgnoreCase("Selecteer jouw kit!")) {
            event.setCancelled(true);
            if (event.getClickedInventory().getTitle().equalsIgnoreCase("Selecteer jouw kit!")) {

                String displayName = event.getCurrentItem().getItemMeta().getDisplayName();

                displayName = displayName.replaceAll("§", "&");

                for (String key : ConfigManager.getKits().getKeys(false)) {
                    if (displayName.equalsIgnoreCase(ConfigManager.getKits().getString(key + ".options.kitname"))) {
                        for (String item : ConfigManager.getKits().getStringList(key + ".items")) {
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
                                double spawnX = ConfigManager.getUsers().getDouble("pvp.x");
                                double spawnY = ConfigManager.getUsers().getDouble("pvp.y");
                                double spawnZ = ConfigManager.getUsers().getDouble("pvp.z");
                                String spawnWorld = ConfigManager.getUsers().getString("pvp.world");
                                float yaw = (float) ConfigManager.getUsers().getDouble("pvp.yaw");
                                float pitch = (float) ConfigManager.getUsers().getDouble("pvp.pitch");

                                Location spawnLocation = new Location(Bukkit.getWorld(spawnWorld), spawnX, spawnY, spawnZ, yaw, pitch);
                                event.getWhoClicked().teleport(spawnLocation);
                            }
                        }
                        if (ConfigManager.getUsers().getDouble("pvp.x") != 0.0
                                && ConfigManager.getUsers().getDouble("pvp.y") != 0.0
                                && ConfigManager.getUsers().getDouble("pvp.z") != 0.0
                                && ConfigManager.getUsers().getString("pvp.world") != ""
                                && ConfigManager.getUsers().getDouble("pvp.yaw") != 0.0
                                && ConfigManager.getUsers().getDouble("pvp.pitch") != 0.0) {
                            event.getWhoClicked().closeInventory();
                            double spawnX = ConfigManager.getUsers().getDouble("pvp.x");
                            double spawnY = ConfigManager.getUsers().getDouble("pvp.y");
                            double spawnZ = ConfigManager.getUsers().getDouble("pvp.z");
                            String spawnWorld = ConfigManager.getUsers().getString("pvp.world");
                            float yaw = (float) ConfigManager.getUsers().getDouble("pvp.yaw");
                            float pitch = (float) ConfigManager.getUsers().getDouble("pvp.pitch");

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