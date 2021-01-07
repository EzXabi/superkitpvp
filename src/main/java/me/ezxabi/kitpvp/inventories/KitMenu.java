package me.ezxabi.kitpvp.inventories;

import me.ezxabi.kitpvp.SuperKitPvP;
import me.ezxabi.kitpvp.manager.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class KitMenu {

    public static void open(SuperKitPvP plugin, Player player) {
        Inventory inv = Bukkit.createInventory(player, 3 * 9, "Select your kit!");
        ConfigManager configManager = plugin.getConfigManager();
        FileConfiguration kits = configManager.getKits();

        for (String key : kits.getKeys(false)) {
            ItemStack item = new ItemStack(Material.valueOf(kits.getString(key + ".options.item")));
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', kits.getString(key + ".options.kitname")));
            List<String> loreToSet = new ArrayList<>();
            for (String s : kits.getStringList(key + ".options.lore")) {
                loreToSet.add(ChatColor.translateAlternateColorCodes('&', s));
            }

            meta.setLore(loreToSet);
            item.setItemMeta(meta);
            inv.addItem(item);
        }
        player.openInventory(inv);
    }
}
