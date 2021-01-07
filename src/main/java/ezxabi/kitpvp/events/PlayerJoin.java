package ezxabi.kitpvp.events;

import ezxabi.kitpvp.SuperKitPvP;
import ezxabi.kitpvp.config.ConfigManager;
import ezxabi.kitpvp.scoreboard.ScoreboardIngame;
import ezxabi.kitpvp.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerJoin implements Listener {

    private SuperKitPvP plugin;

    public PlayerJoin(SuperKitPvP plugin) {
        this.plugin = plugin;
    }

    //*
    // Join listener
    //*
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.getInventory().clear();
        if (ConfigManager.getData().getDouble("spawn.x") != 0.0
                && ConfigManager.getData().getDouble("spawn.y") != 0.0
                && ConfigManager.getData().getDouble("spawn.z") != 0.0
                && ConfigManager.getData().getString("spawn.world") != ""
                && ConfigManager.getData().getDouble("spawn.yaw") != 0.0
                && ConfigManager.getData().getDouble("spawn.pitch") != 0.0) {
            double spawnX = ConfigManager.getData().getDouble("spawn.x");
            double spawnY = ConfigManager.getData().getDouble("spawn.y");
            double spawnZ = ConfigManager.getData().getDouble("spawn.z");
            String spawnWorld = ConfigManager.getData().getString("spawn.world");
            float yaw = (float) ConfigManager.getData().getDouble("spawn.yaw");
            float pitch = (float) ConfigManager.getData().getDouble("spawn.pitch");

            Location spawnLocation = new Location(Bukkit.getWorld(spawnWorld), spawnX, spawnY, spawnZ, yaw, pitch);
            player.getInventory().clear();
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
        if (!plugin.data.exists(player.getUniqueId())) {
            plugin.data.createPlayer(player);
        }
        ScoreboardIngame.show(player);
    }

}
