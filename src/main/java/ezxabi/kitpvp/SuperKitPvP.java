package ezxabi.kitpvp;

import ezxabi.kitpvp.command.BloodCMD;
import ezxabi.kitpvp.command.KitPvPCMD;
import ezxabi.kitpvp.config.ConfigManager;
import ezxabi.kitpvp.listener.*;
import ezxabi.kitpvp.scoreboard.ScoreboardIngame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;


public final class SuperKitPvP extends JavaPlugin {

    @Override
    public void onEnable() {
        loadConfigs();
        loadListeners();
        loadCommands();


    }

    public void loadListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerKillListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerItemDropListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerHungerListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(),this);
    }

    public void loadCommands() {
        getCommand("kitpvp").setExecutor(new KitPvPCMD(this));
        getCommand("blood").setExecutor(new BloodCMD(this));

    }

    public void loadConfigs() {
        ConfigManager.loadConfig();
        ConfigManager.loadKits();
        ConfigManager.loadUsers();
    }

    public static void runScoreboardTimer() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(SuperKitPvP.getPlugin(SuperKitPvP.class), new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (ScoreboardIngame.playerList.contains(p)) {
                        ScoreboardIngame.show(p);
                    }
                }
            }
        }, 0L, 600L);
    }


}
