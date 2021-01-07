package me.ezxabi.kitpvp.listener;

import me.ezxabi.kitpvp.SuperKitPvP;
import me.ezxabi.kitpvp.manager.ConfigManager;
import me.ezxabi.kitpvp.util.Utils;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerKillListener implements Listener {

    private final SuperKitPvP plugin;

    public PlayerKillListener(SuperKitPvP plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        String deaths = String.format("Players.%s.%s", player.getUniqueId(), "deaths");
        ConfigManager configManager = plugin.getConfigManager();
        FileConfiguration users = configManager.getUsers();

        if (event.getEntity().getKiller() != null) {

            Player killer = event.getEntity().getKiller();
            String coins = String.format("Players.%s.%s", killer.getUniqueId(), "coins");
            String blood = String.format("Players.%s.%s", killer.getUniqueId(), "blood");
            String kills = String.format("Players.%s.%s", killer.getUniqueId(), "kills");

            event.setDeathMessage(Utils.col("&a" + event.getEntity().getKiller().getName() + " &2killed&a " + event.getEntity().getName()));
            event.setDroppedExp(0);
            event.setKeepInventory(true);
            event.setKeepLevel(true);
            event.getEntity().setExp(0);
            event.getEntity().getKiller().setExp(0);

            // Update playerdata

            users.set(kills, (users.getInt(kills) + 1));
            users.set(deaths, (users.getInt(deaths) + 1));
            users.set(coins, (users.getInt(coins) + 5));
            configManager.saveUsers(users);
            configManager.loadUsers();
            /*
            * Update scoreboard
            */
            plugin.getScoreboardIngame().show(event.getEntity());
            plugin.getScoreboardIngame().show(event.getEntity().getKiller());
            event.getEntity().getInventory().clear();

            if (users.getBoolean(blood)) event.getEntity().getWorld().playEffect(event.getEntity().getLocation(), Effect.STEP_SOUND, (Object) Material.REDSTONE_BLOCK);
        } else {
            event.setDeathMessage(Utils.col("&a" + event.getEntity().getName() + " &2died"));
            users.set(deaths, (users.getInt(deaths) + 1));
            configManager.saveUsers(users);
            configManager.loadUsers();
            plugin.getScoreboardIngame().show(event.getEntity());
            event.setDroppedExp(0);
            event.setKeepInventory(true);
            event.setKeepLevel(true);
            plugin.getScoreboardIngame().show(event.getEntity());
            event.getEntity().getInventory().clear();
        }
    }
}
