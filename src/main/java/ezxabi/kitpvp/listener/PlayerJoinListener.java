package ezxabi.kitpvp.listener;

import ezxabi.kitpvp.SuperKitPvP;
import ezxabi.kitpvp.config.ConfigManager;
import ezxabi.kitpvp.scoreboard.ScoreboardIngame;
import ezxabi.kitpvp.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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

        player.getInventory().clear();
        if (ConfigManager.getConfig().getDouble("spawn.x") != 0.0
                && ConfigManager.getConfig().getDouble("spawn.y") != 0.0
                && ConfigManager.getConfig().getDouble("spawn.z") != 0.0
                && ConfigManager.getConfig().getString("spawn.world") != ""
                && ConfigManager.getConfig().getDouble("spawn.yaw") != 0.0
                && ConfigManager.getConfig().getDouble("spawn.pitch") != 0.0) {
            double spawnX = ConfigManager.getConfig().getDouble("spawn.x");
            double spawnY = ConfigManager.getConfig().getDouble("spawn.y");
            double spawnZ = ConfigManager.getConfig().getDouble("spawn.z");
            String spawnWorld = ConfigManager.getConfig().getString("spawn.world");
            float yaw = (float) ConfigManager.getConfig().getDouble("spawn.yaw");
            float pitch = (float) ConfigManager.getConfig().getDouble("spawn.pitch");

            Location spawnLocation = new Location(Bukkit.getWorld(spawnWorld), spawnX, spawnY, spawnZ, yaw, pitch);
            player.getInventory().addItem(new ItemStack(Material.BLAZE_ROD,1));
            player.teleport(spawnLocation);
        } else {
            if (player.isOp()) {
                player.sendMessage(Utils.col("&cYou have not set up a lobby yet!"));
            }
        }

        //*
        // Aanmaken van player bij eerste join
        //*
        if (!ConfigManager.getUsers().contains(name)) {
            ConfigManager.getUsers().set(name, player.getName());
            ConfigManager.getUsers().set(blood, false);
            ConfigManager.getUsers().set(kills, "0");
            ConfigManager.getUsers().set(deaths, "0");
            ConfigManager.getUsers().set(coins, "0");

            ConfigManager.saveUsers();
            ConfigManager.loadUsers();
        }
        ScoreboardIngame.show(player);
    }

}
