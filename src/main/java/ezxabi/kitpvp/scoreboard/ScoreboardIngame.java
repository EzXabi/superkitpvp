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
        String name = String.format("Rank.%s.%s", player.getUniqueId(), "name");
        String killsPath = String.format("Rank.%s.%s", player.getUniqueId(), "kills");
        String deathsPath = String.format("Rank.%s.%s", player.getUniqueId(), "deaths");
        String coinsPath = String.format("Rank.%s.%s", player.getUniqueId(), "coins");
        String bloodPath = String.format("Rank.%s.%s", player.getUniqueId(), "blood");

        double kills = ConfigManager.getData().getDouble(killsPath);
        double deaths = ConfigManager.getData().getDouble(deathsPath);
        int points = ConfigManager.getData().getInt(coinsPath);
        double kdRatio = kills / deaths;
        DecimalFormat df = new DecimalFormat("0.00");

        //*
        // Scoreboard aanmaken
        //*
        org.bukkit.scoreboard.Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("kitpvp", "dum");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(Utils.col(ConfigManager.getConfig().getString("scoreboard.title")));
        obj.getScore("    ").setScore(8);
        obj.getScore("§cStats").setScore(7);
        obj.getScore("§7Kills: §f" + kills).setScore(6);
        obj.getScore("§7Deaths: §f" + deaths).setScore(5);
        obj.getScore("§7Punten: §f" + points + " ✪").setScore(3);

        obj.getScore("§7KD Ratio: §f" + df.format(kdRatio)).setScore(4);
        obj.getScore("   ").setScore(2);
        obj.getScore("§cplay.dusdavidgames.nl").setScore(1);

        player.setScoreboard(board);
    }

}
