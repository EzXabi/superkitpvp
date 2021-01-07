package me.ezxabi.kitpvp.command;

import me.ezxabi.kitpvp.SuperKitPvP;
import me.ezxabi.kitpvp.inventories.KitMenu;
import me.ezxabi.kitpvp.manager.ConfigManager;
import me.ezxabi.kitpvp.util.Utils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class KitPvPCMD implements CommandExecutor {

    private final SuperKitPvP plugin;

    public KitPvPCMD(SuperKitPvP plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ConfigManager configManager = plugin.getConfigManager();
        FileConfiguration config = configManager.getConfig();

        //*
        // Algemene commando
        //*

        if (cmd.getName().equalsIgnoreCase("kitpvp")) {
            //*
            // Checken als sender een speler is
            //*
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.col("&cYou are not a player!"));
                return true;
            }

            Player p = (Player) sender;
            //*
            // Checken voor permission
            //*
            if (!p.hasPermission("kitpvp.admin")) {
                sender.sendMessage(Utils.col("&cYout don't have the permission kitpvp.admin!"));
                return true;
            }

            //*
            // Help bericht
            //*
            if (args.length == 0) {
                p.sendMessage(Utils.col("&2/kitpvp setlobby &a- &2Set a location where you spawn when you login."));
                p.sendMessage(Utils.col("&2/kitpvp kit &a- &2Opens a menu where you can choose a kit."));
                p.sendMessage(Utils.col("&2/kitpvp reload &a- &2Reload all files."));
                p.sendMessage(Utils.col("&2/blood <speler> &a- &2Gives someone the blood kill effect."));
                return true;
            } else if (args[0].equalsIgnoreCase("setlobby")) {
                //*
                // Instellen van de lobby locatie
                //*
                double x = p.getLocation().getX();
                double y = p.getLocation().getY();
                double z = p.getLocation().getZ();
                float yaw = p.getLocation().getYaw();
                float pitch = p.getLocation().getPitch();
                String worldName = p.getLocation().getWorld().getName();

                config.set("spawn.x", x);
                config.set("spawn.y", y);
                config.set("spawn.z", z);
                config.set("spawn.yaw", (double) yaw);
                config.set("spawn.pitch", (double) pitch);
                config.set("spawn.world", worldName);
                configManager.saveConfig(config);
                final Location l = p.getLocation();
                p.getWorld().setSpawnLocation((int) l.getX(), (int) l.getY(), (int) l.getZ());

                p.sendMessage(Utils.col("&aYou have successfully set up the lobby!"));
                return true;
            } else if (args[0].equalsIgnoreCase("kit")) {
                KitMenu.open(plugin, p);
                return true;
            } else if (args[0].equalsIgnoreCase("reload")) {
                configManager.loadKits();
                configManager.loadUsers();
                configManager.loadConfig();

                p.sendMessage(Utils.col("&aYou have successfully reloaded all files!"));
                return true;
            } else {
                p.sendMessage(Utils.col("&cThis command does not exist!"));
                return true;
            }
        }
        return false;
    }
}
