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

public class PlayerKill implements Listener {

    private SuperKitPvP plugin;

    public PlayerKill(SuperKitPvP plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (event.getEntity().getKiller() instanceof Player) {
            //*
            // Announce bericht + kleine dingen
            //*
            event.setDeathMessage(Utils.col("&a"+ event.getEntity().getKiller().getName() + " &2killed&a " + event.getEntity().getName()));
            event.setDroppedExp(0);
            event.setKeepInventory(true);
            event.setKeepLevel(true);
            event.getEntity().setExp(0);
            event.getEntity().getKiller().setExp(0);

            // Update playerdata

            plugin.data.addKills(event.getEntity().getKiller().getUniqueId(), 1);
            plugin.data.addPoints(event.getEntity().getKiller().getUniqueId(), 1);
            plugin.data.addDeaths(event.getEntity().getUniqueId(), 1);
            //*
            // Update scoreboard
            //*
            ScoreboardIngame.show(event.getEntity());
            ScoreboardIngame.show(event.getEntity().getKiller());
            event.getEntity().getInventory().clear();


            if (plugin.data.getBlood(event.getEntity().getKiller().getUniqueId())) {
                event.getEntity().getWorld().playEffect(event.getEntity().getLocation(), Effect.STEP_SOUND, (Object) Material.REDSTONE_BLOCK);
            }

        } else {
            //*
            // Speler dood door fall damage of iets anders
            //*
            event.setDeathMessage(Utils.col("&a"+ event.getEntity().getName() + " &2died"));
            plugin.data.addDeaths(event.getEntity().getUniqueId(), 1);
            ScoreboardIngame.show(event.getEntity());
            event.setDroppedExp(0);
            event.setKeepInventory(true);
            event.setKeepLevel(true);
            ScoreboardIngame.show(event.getEntity());
            event.getEntity().getInventory().clear();
        }
    }
}
