package ezxabi.kitpvp.events;

import ezxabi.kitpvp.config.ConfigManager;
import ezxabi.kitpvp.inventories.KitMenu;
import ezxabi.kitpvp.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MultipleEvents implements Listener {

    public static List<Player> list = new ArrayList<>();

    //*
    // Listener voor het maken van een join sign
    //*
    //*
    // Inventory event voor het kit menu
    //*
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


    //*
    // Listener voor het joinen van een game via sign
    //*
    @EventHandler
    public void blazeRod(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                if (player.getInventory().getItemInHand().getType() == Material.BLAZE_POWDER) {
                        KitMenu.open(event.getPlayer());
                }
        }
    }

    private int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}