package me.ezxabi.kitpvp.command;

import me.ezxabi.kitpvp.SuperKitPvP;
import me.ezxabi.kitpvp.manager.ConfigManager;
import me.ezxabi.kitpvp.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class BloodCMD implements CommandExecutor {
    private final SuperKitPvP plugin;

    public BloodCMD(SuperKitPvP plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //*
        // Geef een speler het bloodkill effect
        //*
        Player player = (Player) sender;
        Player target = Bukkit.getPlayerExact(args[0]);
        ConfigManager configManager = plugin.getConfigManager();
        FileConfiguration users = configManager.getUsers();

        if (player.hasPermission("kitpvp.blood")) {
            String bloodPath = String.format("Players.%s.%s", target.getUniqueId(), "blood");
            try {
                if (!users.getBoolean(bloodPath)) {
                    users.set(bloodPath, true);
                    player.sendMessage(Utils.col("&2Succesfully given a &abloodkill effect&2 to &a" + target.getName() + "&2!"));
                    configManager.saveUsers(users);
                    configManager.loadUsers();
                } else {
                    player.sendMessage(Utils.col("&cThis player already has the bloodkill effect!"));
                }
            } catch (Exception e) {
                player.sendMessage(Utils.col("&c/blood <player>!"));

            }
        }
        return true;
    }
}
