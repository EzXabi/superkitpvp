package me.ezxabi.kitpvp.command;

import me.ezxabi.kitpvp.SuperKitPvP;
import me.ezxabi.kitpvp.inventories.KitMenu;
import me.ezxabi.kitpvp.manager.ConfigManager;
import me.ezxabi.kitpvp.util.Utils;
import org.bukkit.Bukkit;
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

        if (cmd.getName().equalsIgnoreCase("kitpvp")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.col("&cYou are not a player!"));
                return true;
            }

            Player p = (Player) sender;
            if (!p.hasPermission("kitpvp.admin")) {
                sender.sendMessage(Utils.col("&cYout don't have the permission kitpvp.admin!"));
                return true;
            }

            if (args.length == 0) {
                p.sendMessage(Utils.col("&2/kitpvp setlobby &a- Set a location where you spawn when you login."));
                p.sendMessage(Utils.col("&2/kitpvp lobby &a- Teleports you to the lobby."));
                p.sendMessage(Utils.col("&2/kitpvp kit &a- Opens a menu where you can choose a kit."));
                p.sendMessage(Utils.col("&2/kitpvp reload &a- Reload all files."));
                p.sendMessage(Utils.col("&2/blood <speler> &a- Gives someone the blood kill effect."));
                return true;
            } else if (args[0].equalsIgnoreCase("setlobby")) {
                double x = p.getLocation().getX();
                double y = p.getLocation().getY();
                double z = p.getLocation().getZ();
                float yaw = p.getLocation().getYaw();
                float pitch = p.getLocation().getPitch();
                String worldName = p.getLocation().getWorld().getName();

                config.set("lobby.x", x);
                config.set("lobby.y", y);
                config.set("lobby.z", z);
                config.set("lobby.yaw", (double) yaw);
                config.set("lobby.pitch", (double) pitch);
                config.set("lobby.world", worldName);
                configManager.saveConfig(config);
                final Location l = p.getLocation();
                p.getWorld().setSpawnLocation((int) l.getX(), (int) l.getY(), (int) l.getZ());

                p.sendMessage(Utils.col("&aYou have successfully set up the lobby!"));
                return true;
            } else if (args[0].equalsIgnoreCase("kit")) {
                KitMenu.open(plugin, p);
                return true;
            }else if (args[0].equalsIgnoreCase("lobby")) {
                tryTeleport(p,config);
                return true;
            }
            else if (args[0].equalsIgnoreCase("reload")) {
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

    public void tryTeleport(Player player, FileConfiguration config){
        if (config.getDouble("lobby.x") != 0.0
                && config.getDouble("lobby.y") != 0.0
                && config.getDouble("lobby.z") != 0.0
                && !config.getString("lobby.world").equals("")
                && config.getDouble("lobby.yaw") != 0.0
                && config.getDouble("lobby.pitch") != 0.0) {
            double spawnX = config.getDouble("lobby.x");
            double spawnY = config.getDouble("lobby.y");
            double spawnZ = config.getDouble("lobby.z");
            String spawnWorld = config.getString("lobby.world");
            float yaw = (float) config.getDouble("lobby.yaw");
            float pitch = (float) config.getDouble("lobby.pitch");

            Location spawnLocation = new Location(Bukkit.getWorld(spawnWorld), spawnX, spawnY, spawnZ, yaw, pitch);
            player.teleport(spawnLocation);
            player.sendMessage(Utils.col("&aYou have been teleported to the lobby!"));
        } else {
            player.sendMessage(Utils.col("&cYou have not set up a lobby yet!"));
        }
    }
}
