package me.ezxabi.kitpvp.manager;

import me.ezxabi.kitpvp.SuperKitPvP;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private final SuperKitPvP plugin;
    private File usersFile;

    //By EzXabi
    private FileConfiguration usersConfig;
    private File configFile;
    private FileConfiguration configConfig;
    private File kitsFile;
    private FileConfiguration kitsConfig;
    public ConfigManager(SuperKitPvP plugin) {
        this.plugin = plugin;
    }

    public FileConfiguration getConfig() {
        return configConfig;
    }

    public void saveConfig(FileConfiguration config) {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource("config.yml", false);
        }

        configConfig = new YamlConfiguration();
        try {
            configConfig.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    /* Users Config
     */

    public FileConfiguration getUsers() {
        return usersConfig;
    }

    public void loadUsers() {
        usersFile = new File(plugin.getDataFolder(), "players.yml");
        if (!usersFile.exists()) {
            usersFile.getParentFile().mkdirs();
            plugin.saveResource("players.yml", false);
        }

        usersConfig = new YamlConfiguration();
        try {
            usersConfig.load(usersFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void saveUsers(FileConfiguration users) {
        try {
            users.save(usersFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Messages Config
     */

    public FileConfiguration getKits() {
        return kitsConfig;
    }

    public void loadKits() {
        kitsFile = new File(plugin.getDataFolder(), "kits.yml");
        if (!kitsFile.exists()) {
            kitsFile.getParentFile().mkdirs();
            plugin.saveResource("kits.yml", false);
        }

        kitsConfig = new YamlConfiguration();
        try {
            kitsConfig.load(kitsFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void saveKits(FileConfiguration kits) {
        try {
            kits.save(kitsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
