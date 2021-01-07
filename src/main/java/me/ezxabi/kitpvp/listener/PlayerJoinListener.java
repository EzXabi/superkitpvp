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

    private final SuperKitPvP plugin;

    public PlayerJoinListener(SuperKitPvP plugin) {
        this.plugin = plugin;
    }

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

        if(config.getBoolean("check-for-updates")){
            tryTeleport(player,config);
        }

        player.getInventory().clear();
        player.getInventory().addItem(new ItemStack(Material.BLAZE_ROD, 1));

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

    public void tryTeleport(Player player, FileConfiguration config){
        if (config.getDouble("lobby.x") != 0.0
                && config.getDouble("lobby.y") != 0.0
                && config.getDouble("lobby.z") != 0.0
                && !config.getString("lobby.world").equals("")
                && config.getDouble("lobby.yaw") != 0.0
                && config.getDouble("lobby.pitch") != 0.0) {
            double spawnX = config.getDouble("lobby.x");
            double spawnY = config.getDouble("lobby.y");
            double spawnZ = config.getDouble("lobby.z");
            String spawnWorld = config.getString("lobby.world");
            float yaw = (float) config.getDouble("lobby.yaw");
            float pitch = (float) config.getDouble("lobby.pitch");

            Location spawnLocation = new Location(Bukkit.getWorld(spawnWorld), spawnX, spawnY, spawnZ, yaw, pitch);
            player.teleport(spawnLocation);
            player.sendMessage(Utils.col("&aYou have been teleported to the lobby!"));
        } else {
            player.sendMessage(Utils.col("&cYou have not set up a lobby yet!"));
        }
    }

}
