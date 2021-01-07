package ezxabi.kitpvp.scoreboard;

import ezxabi.kitpvp.SuperKitPvP;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ScoreboardIngame {

    public static List<Player> playerList = new ArrayList<Player>();

    private SuperKitPvP plugin;

    public ScoreboardIngame(SuperKitPvP plugin) {
        this.plugin = plugin;
    }

    public static void show(Player p) {
        //*
        // Ophalen van stats
        //*
        double kills = SuperKitPvP.data2.getKills(p.getUniqueId());
        double deaths = SuperKitPvP.data2.getDeaths(p.getUniqueId());
        int points = SuperKitPvP.data2.getPoints(p.getUniqueId());
        double kdRatio = kills / deaths;

        //*
        // Scoreboard aanmaken
        //*
        org.bukkit.scoreboard.Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("kitpvp", "dum");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName("§f§lDDG-PvP");
        obj.getScore("    ").setScore(8);
        obj.getScore("§cStats").setScore(7);
        obj.getScore("§7Kills: §f" + kills).setScore(6);
        obj.getScore("§7Deaths: §f" + deaths).setScore(5);
        obj.getScore("§7Punten: §f" + points + " ✪").setScore(3);

        DecimalFormat df = new DecimalFormat("0.00");

        obj.getScore("§7KD Ratio: §f" + df.format(kdRatio)).setScore(4);
        obj.getScore("   ").setScore(2);
        obj.getScore("§cplay.dusdavidgames.nl").setScore(1);

        p.setScoreboard(board);
    }

}
