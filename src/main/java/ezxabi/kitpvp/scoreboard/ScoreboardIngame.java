package ezxabi.kitpvp.scoreboard;

import ezxabi.kitpvp.SuperKitPvP;
import ezxabi.kitpvp.config.ConfigManager;
import ezxabi.kitpvp.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import sun.security.krb5.Config;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ScoreboardIngame {

    public static List<Player> playerList = new ArrayList<Player>();

    private SuperKitPvP plugin;

    public ScoreboardIngame(SuperKitPvP plugin) {
        this.plugin = plugin;
    }

    public static void show(Player player) {
        String name = String.format("Players.%s.%s", player.getUniqueId(), "name");
        String killsPath = String.format("Players.%s.%s", player.getUniqueId(), "kills");
        String deathsPath = String.format("Players.%s.%s", player.getUniqueId(), "deaths");
        String coinsPath = String.format("Players.%s.%s", player.getUniqueId(), "coins");
        String bloodPath = String.format("Players.%s.%s", player.getUniqueId(), "blood");

        double kills = ConfigManager.getUsers().getDouble(killsPath);
        double deaths = ConfigManager.getUsers().getDouble(deathsPath);
        int points = ConfigManager.getUsers().getInt(coinsPath);
        double kdRatio = kills / deaths;
        DecimalFormat df = new DecimalFormat("0.00");

        //*
        // Scoreboard aanmaken
        //*
        List scoreboard = ConfigManager.getConfig().getStringList("scoreboard.lines");
        String line1 = Utils.col((String) scoreboard.get(1));
        String line2 = Utils.col((String) scoreboard.get(2));
        line2 = line2.replace("%user-kills%", String.valueOf(kills));

        String line3 = Utils.col((String) scoreboard.get(3));
        line3 = line3.replace("%user-deaths%", String.valueOf(deaths));

        String line4 = Utils.col((String) scoreboard.get(4));
        line4 = line4.replace("%user-kd-ratio%", df.format(kdRatio));

        String line5 = Utils.col((String) scoreboard.get(5));
        line5 = line5.replace("%user-points%", String.valueOf(points));

        String line6 = Utils.col((String) scoreboard.get(7));


        org.bukkit.scoreboard.Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("kitpvp", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(Utils.col(ConfigManager.getConfig().getString("scoreboard.title")));
        obj.getScore("    ").setScore(8);
        obj.getScore(line1).setScore(7);
        obj.getScore(line2).setScore(6);
        obj.getScore(line3).setScore(5);
        obj.getScore(line4).setScore(3);

        obj.getScore(line5).setScore(4);
        obj.getScore("   ").setScore(2);
        obj.getScore(line6).setScore(1);

        player.setScoreboard(board);
    }

}
