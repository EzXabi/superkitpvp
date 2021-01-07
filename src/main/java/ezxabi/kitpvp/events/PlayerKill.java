package ezxabi.kitpvp.events;

import ezxabi.kitpvp.SuperKitPvP;
import ezxabi.kitpvp.config.ConfigManager;
import ezxabi.kitpvp.scoreboard.ScoreboardIngame;
import ezxabi.kitpvp.util.Utils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import sun.security.krb5.Config;

public class PlayerKill implements Listener {

    private SuperKitPvP plugin;

    public PlayerKill(SuperKitPvP plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        String deaths = String.format("Players.%s.%s", player.getUniqueId(), "deaths");
        String blood = String.format("Rank.%s.%s", player.getUniqueId(), "blood");
        if (event.getEntity().getKiller() instanceof Player) {

            Player killer = event.getEntity().getKiller();
            String coins = String.format("Players.%s.%s", killer.getUniqueId(), "coins");
            String kills = String.format("Players.%s.%s", killer.getUniqueId(), "kills");

            event.setDeathMessage(Utils.col("&a"+ event.getEntity().getKiller().getName() + " &2killed&a " + event.getEntity().getName()));
            event.setDroppedExp(0);
            event.setKeepInventory(true);
            event.setKeepLevel(true);
            event.getEntity().setExp(0);
            event.getEntity().getKiller().setExp(0);

            // Update playerdata

            ConfigManager.getData().set(kills, (ConfigManager.getData().getInt(kills) + 1));
            ConfigManager.getData().set(deaths, (ConfigManager.getData().getInt(deaths) + 1));
            ConfigManager.getData().set(coins, (ConfigManager.getData().getInt(coins) + 5));
            //*
            // Update scoreboard
            //*
            ScoreboardIngame.show(event.getEntity());
            ScoreboardIngame.show(event.getEntity().getKiller());
            event.getEntity().getInventory().clear();


            if (ConfigManager.getData().getBoolean(blood)) {
                event.getEntity().getWorld().playEffect(event.getEntity().getLocation(), Effect.STEP_SOUND, (Object) Material.REDSTONE_BLOCK);
            }

        } else {
            //*
            // Speler dood door fall damage of iets anders
            //*
            event.setDeathMessage(Utils.col("&a"+ event.getEntity().getName() + " &2died"));
            ConfigManager.getData().set(deaths, (ConfigManager.getData().getInt(deaths) + 1));
            ScoreboardIngame.show(event.getEntity());
            event.setDroppedExp(0);
            event.setKeepInventory(true);
            event.setKeepLevel(true);
            ScoreboardIngame.show(event.getEntity());
            event.getEntity().getInventory().clear();
        }
    }
}
