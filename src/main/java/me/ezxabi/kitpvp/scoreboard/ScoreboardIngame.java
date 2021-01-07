package me.ezxabi.kitpvp.scoreboard;

import me.ezxabi.kitpvp.SuperKitPvP;
import me.ezxabi.kitpvp.manager.ConfigManager;
import me.ezxabi.kitpvp.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ScoreboardIngame {

    private final List<Player> playerList = new ArrayList<>();
    private final SuperKitPvP plugin;

    public ScoreboardIngame(SuperKitPvP plugin) {
        this.plugin = plugin;
    }

    public void show(Player player) {
        String name = String.format("Players.%s.%s", player.getUniqueId(), "name");
        String killsPath = String.format("Players.%s.%s", player.getUniqueId(), "kills");
        String deathsPath = String.format("Players.%s.%s", player.getUniqueId(), "deaths");
        String coinsPath = String.format("Players.%s.%s", player.getUniqueId(), "coins");
        String bloodPath = String.format("Players.%s.%s", player.getUniqueId(), "blood");

        ConfigManager configManager = plugin.getConfigManager();
        FileConfiguration users = configManager.getUsers();
        FileConfiguration config = configManager.getConfig();

        double kills = users.getDouble(killsPath);
        double deaths = users.getDouble(deathsPath);
        int points = users.getInt(coinsPath);
        double kdRatio = kills / deaths;
        DecimalFormat df = new DecimalFormat("#,###.##");

        /*
         * Make scoreboard
         */

        List<String> scoreboard = config.getStringList("scoreboard.lines");
        String line1 = Utils.col(scoreboard.get(1));
        String line2 = Utils.col(scoreboard.get(2)).replace("%user-kills%", String.valueOf(kills));
        String line3 = Utils.col(scoreboard.get(3)).replace("%user-deaths%", String.valueOf(deaths));
        String line4 = Utils.col(scoreboard.get(4)).replace("%user-kd-ratio%", df.format(kdRatio));
        String line5 = Utils.col(scoreboard.get(5)).replace("%user-points%", String.valueOf(points));
        String line6 = Utils.col(scoreboard.get(7));

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("kitpvp", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(Utils.col(config.getString("scoreboard.title")));
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

    public List<Player> getPlayerList() {
        return this.playerList;
    }
}
