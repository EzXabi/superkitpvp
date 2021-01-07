package ezxabi.kitpvp;

import ezxabi.kitpvp.commands.BloodCMD;
import ezxabi.kitpvp.commands.KitPvPCMD;
import ezxabi.kitpvp.config.ConfigManager;
import ezxabi.kitpvp.events.*;
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
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerKill(this), this);
        Bukkit.getPluginManager().registerEvents(new MultipleEvents(), this);
        Bukkit.getPluginManager().registerEvents(new Chat(), this);
        Bukkit.getPluginManager().registerEvents(new OnMove(this), this);
        Bukkit.getPluginManager().registerEvents(new ItemDrop(), this);
        Bukkit.getPluginManager().registerEvents(new Hunger(), this);

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
