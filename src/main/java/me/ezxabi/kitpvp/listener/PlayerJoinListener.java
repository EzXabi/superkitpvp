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
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerJoinListener implements Listener {

    private SuperKitPvP plugin;

    public PlayerJoinListener(SuperKitPvP plugin) {
        this.plugin = plugin;
    }

    //*
    // Join listener
    //*
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String name = String.format("Players.%s.%s", player.getUniqueId(), "name");
        String kills = String.format("Players.%s.%s", player.getUniqueId(), "kills");
        String deaths = String.format("Players.%s.%s", player.getUniqueId(), "deaths");
        String coins = String.format("Players.%s.%s", player.getUniqueId(), "coins");
        String blood = String.format("Players.%s.%s", player.getUniqueId(), "blood");
        ConfigManager configManager = plugin.getConfigManager();
        FileConfiguration config = configManager.getConfig();
        FileConfiguration users = configManager.getUsers();

        player.getInventory().clear();
        if (config.getDouble("spawn.x") != 0.0
                && config.getDouble("spawn.y") != 0.0
                && config.getDouble("spawn.z") != 0.0
                && config.getString("spawn.world") != ""
                && config.getDouble("spawn.yaw") != 0.0
                && config.getDouble("spawn.pitch") != 0.0) {
            double spawnX = config.getDouble("spawn.x");
            double spawnY = config.getDouble("spawn.y");
            double spawnZ = config.getDouble("spawn.z");
            String spawnWorld = config.getString("spawn.world");
            float yaw = (float) config.getDouble("spawn.yaw");
            float pitch = (float) config.getDouble("spawn.pitch");

            Location spawnLocation = new Location(Bukkit.getWorld(spawnWorld), spawnX, spawnY, spawnZ, yaw, pitch);
            player.getInventory().addItem(new ItemStack(Material.BLAZE_ROD, 1));
            player.teleport(spawnLocation);
        } else {
            if (player.isOp()) {
                player.sendMessage(Utils.col("&cYou have not set up a lobby yet!"));
            }
        }

        //*
        // Aanmaken van player bij eerste join
        //*
        if (!users.contains(name)) {
            users.set(name, player.getName());
            users.set(blood, false);
            users.set(kills, "0");
            users.set(deaths, "0");
            users.set(coins, "0");

            configManager.saveUsers(users);
            configManager.loadUsers();
        }
        plugin.getScoreboardIngame().show(player);
    }

}
