package ezxabi.kitpvp.commands;

import ezxabi.kitpvp.SuperKitPvP;
import ezxabi.kitpvp.config.ConfigManager;
import ezxabi.kitpvp.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sun.security.krb5.Config;

public class BloodCMD implements CommandExecutor {
    private SuperKitPvP plugin;

    public BloodCMD(SuperKitPvP plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //*
        // Geef een speler het bloodkill effect
        //*
        Player player = (Player) sender;
        if (player.hasPermission("kitpvp.blood")) {
            String bloodPath = String.format("Rank.%s.%s", player.getUniqueId(), "blood");
            try {
                Player target = Bukkit.getPlayerExact(args[0]);
                if (!ConfigManager.getData().getBoolean(bloodPath)) {
                    ConfigManager.getData().set(bloodPath,true);
                    player.sendMessage(Utils.col("&2Succesfully given a &abloodkill effect&2 to &a"  + target.getName() +"&2!"));
                }
            } catch (Exception e) {
                player.sendMessage(Utils.col("&c/blood <player>!"));

            }
        }
        return true;
    }
}
