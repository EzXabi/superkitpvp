package ezxabi.kitpvp;

import ezxabi.kitpvp.commands.BloodCMD;
import ezxabi.kitpvp.commands.KitPvPCMD;
import ezxabi.kitpvp.config.ConfigManager;
import ezxabi.kitpvp.database.MySQL;
import ezxabi.kitpvp.database.SQLGetter;
import ezxabi.kitpvp.events.*;
import ezxabi.kitpvp.scoreboard.ScoreboardIngame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;


public final class SuperKitPvP extends JavaPlugin {



    public MySQL SQL;
    public SQLGetter data;
    public static SQLGetter data2;


    @Override
    public void onEnable() {

        //*
        // Gemaakt door EzXabi voor DDG
        // Deze plugin is zeer basic en kan nog vele malen cooler en uitgebrijd worden. Aleen was de vraag een simpele kitpvp plugin en
        // wil ik niet speciaal doen.
        // Ik heb er wat creativiteit ingestoken door jump pads toe te voegen en een punten systeem waar je nog niet zoveel mee kan en ook een bloodkill effect met /blood !
        //*


        loadConfigs();
        loadListeners();
        loadCommands();


        this.SQL = new MySQL();
        this.data = new SQLGetter(this);
        data2 = new SQLGetter(this);

        try {
            SQL.connect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (SQL.isConnected()) {
            Bukkit.getConsoleSender().sendMessage("[DDG-PvP] Database succesfuly connected.");
            data.createTable();
        }

        runScoreboardTimer();
        refreshSQL();
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
        getCommand("ddgpvp").setExecutor(new KitPvPCMD(this));
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

    public void refreshSQL() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(SuperKitPvP.getPlugin(SuperKitPvP.class), new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    SQL.connect();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (SQL.isConnected()) {
                    Bukkit.getConsoleSender().sendMessage("[DDG-PvP] Database succesfully connected.");
                    data.createTable();
                }

            }
        }, 0L, 72000L);
    }

}
