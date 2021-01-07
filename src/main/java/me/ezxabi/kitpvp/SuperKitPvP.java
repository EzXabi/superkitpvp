package me.ezxabi.kitpvp;

import me.ezxabi.kitpvp.command.BloodCMD;
import me.ezxabi.kitpvp.command.KitPvPCMD;
import me.ezxabi.kitpvp.listener.*;
import me.ezxabi.kitpvp.manager.ConfigManager;
import me.ezxabi.kitpvp.scoreboard.ScoreboardIngame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SuperKitPvP extends JavaPlugin {

    private ConfigManager configManager;
    private ScoreboardIngame scoreboardIngame;

    @Override
    public void onEnable() {
        this.configManager = new ConfigManager(this);
        this.scoreboardIngame = new ScoreboardIngame(this);

        loadConfigs();
        loadListeners();
        loadCommands();
    }

    public void loadListeners() {
        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new PlayerJoinListener(this), this);
        manager.registerEvents(new PlayerKillListener(this), this);
        manager.registerEvents(new PlayerInventoryListener(this), this);
        manager.registerEvents(new PlayerChatListener(), this);
        manager.registerEvents(new PlayerMoveListener(this), this);
        manager.registerEvents(new PlayerItemDropListener(), this);
        manager.registerEvents(new PlayerHungerListener(), this);
        manager.registerEvents(new PlayerInteractListener(this), this);
    }

    public void loadCommands() {

        getCommand("kitpvp").setExecutor(new KitPvPCMD(this));
        getCommand("blood").setExecutor(new BloodCMD(this));
    }

    public void loadConfigs() {
        configManager.loadConfig();
        configManager.loadKits();
        configManager.loadUsers();
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    public ScoreboardIngame getScoreboardIngame() {
        return this.scoreboardIngame;
    }

}
