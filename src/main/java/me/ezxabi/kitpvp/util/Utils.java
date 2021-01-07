package me.ezxabi.kitpvp.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Random;

public class Utils {

    public static String color(final String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static void noPermission(final CommandSender sender, final String s) {
        sender.sendMessage(color("&cJe hebt de permissie" + s + " nodig."));
    }

    public static Integer randomValue(final Integer max, final Integer min) {
        final Random random = new Random();
        final int value = random.nextInt(max - min) + min;
        return value;
    }

    public static String col(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static boolean isInt(final String s) {
        boolean amIValid = false;
        try {
            Integer.parseInt(s);
            amIValid = true;
        } catch (NumberFormatException e) {
            amIValid = false;
        }
        return amIValid;
    }
}
