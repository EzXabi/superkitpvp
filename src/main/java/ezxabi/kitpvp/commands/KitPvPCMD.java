package ezxabi.kitpvp.commands;

import ezxabi.kitpvp.SuperKitPvP;
import ezxabi.kitpvp.config.ConfigManager;
import ezxabi.kitpvp.inventories.KitMenu;
import ezxabi.kitpvp.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitPvPCMD implements CommandExecutor {

    private SuperKitPvP plugin;

    public KitPvPCMD(SuperKitPvP plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

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

                ConfigManager.getConfig().set("spawn.x", x);
                ConfigManager.getConfig().set("spawn.y", y);
                ConfigManager.getConfig().set("spawn.z", z);
                ConfigManager.getConfig().set("spawn.yaw", (double) yaw);
                ConfigManager.getConfig().set("spawn.pitch", (double) pitch);
                ConfigManager.getConfig().set("spawn.world", worldName);
                ConfigManager.saveConfig();
                final Location l = p.getLocation();
                p.getWorld().setSpawnLocation((int) l.getX(), (int) l.getY(), (int) l.getZ());

                p.sendMessage(Utils.col("&aYou have successfully set up the lobby!"));
                return true;
            } else if (args[0].equalsIgnoreCase("kit")) {
                KitMenu.open(p);
                return true;
            } else if (args[0].equalsIgnoreCase("reload")) {
                ConfigManager.loadKits();
                ConfigManager.loadUsers();
                ConfigManager.loadConfig();

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
