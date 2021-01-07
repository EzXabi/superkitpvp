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
    public void onInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        if (player.getOpenInventory().getTopInventory().getTitle().equalsIgnoreCase("Select your kit!")) {
            event.setCancelled(true);
            if (event.getClickedInventory().getTitle().equalsIgnoreCase("Select your kit!")) {

                if(event.getCurrentItem().getItemMeta() == null){
                    return;
                }
                if(event.getCurrentItem().getType() == Material.BLAZE_ROD){
                    return;
                }
                player.getInventory().clear();
                String displayName = event.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§", "&");
                ConfigManager configManager = plugin.getConfigManager();
                FileConfiguration users = configManager.getUsers();

                for (String key : configManager.getKits().getKeys(false)) {
                    if (displayName.equalsIgnoreCase(configManager.getKits().getString(key + ".options.kitname"))) {
                        for (String item : configManager.getKits().getStringList(key + ".items")) {
                            if (item == null) continue;
                            if (!event.getWhoClicked().getInventory().contains(Material.valueOf(item))) {
                                player.closeInventory();
                                if (item.contains("HELMET")) {
                                    ItemStack it = new ItemStack(Material.valueOf(item));
                                    player.getInventory().setHelmet(it);
                                }
                                if (item.contains("CHESTPLATE")) {
                                    ItemStack it = new ItemStack(Material.valueOf(item));
                                    player.getInventory().setChestplate(it);
                                }
                                if (item.contains("LEGGINGS")) {
                                    ItemStack it = new ItemStack(Material.valueOf(item));
                                    player.getInventory().setLeggings(it);
                                }
                                if (item.contains("BOOTS")) {
                                    ItemStack it = new ItemStack(Material.valueOf(item));
                                    player.getInventory().setBoots(it);
                                }
                                if (!item.contains("HELMET") && !item.contains("CHESTPLATE") && !item.contains("LEGGINGS") && !item.contains("BOOTS")) {
                                    ItemStack it = new ItemStack(Material.valueOf(item));
                                    player.getInventory().addItem(it);
                                }
                            } else {
                                player.closeInventory();
                                // TODO: Dingen maken hier die moeten gebeuren na kit selection
                            }
                        }
                        player.closeInventory();
                    }
                }
            }
        }
    }
}