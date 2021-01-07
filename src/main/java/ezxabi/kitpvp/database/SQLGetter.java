package ezxabi.kitpvp.database;

import ezxabi.kitpvp.SuperKitPvP;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLGetter {
// SQl Manager by EzXabi

    private SuperKitPvP plugin;

    public SQLGetter(SuperKitPvP plugin) {
        this.plugin = plugin;
    }

    //*
    // Maak table aan als die er nog niet is
    //*
    public void createTable() {
        PreparedStatement ps;
        try {
            ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS playerdata "
                    + "(NAME VARCHAR(100),UUID VARCHAR(100),DEATHS INT(100),KILLS INT(100),POINTS INT(100),BLOOD TINYINT(0),PRIMARY KEY (NAME))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //*
    // Functie voor het maken van een nieuw player als die er nog niet is
    //*
    public void createPlayer(Player player) {
        try {
            UUID uuid = player.getUniqueId();
            if (!exists(uuid)) {
                PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("INSERT IGNORE INTO playerdata"
                        + " (NAME,UUID) VALUES (?,?)");
                ps.setString(1, player.getName());
                ps.setString(2, uuid.toString());
                ps.executeUpdate();

                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //*
    // Een check voor het kijken als een player bestaat
    //*
    public boolean exists(UUID uuid) {
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM playerdata WHERE UUID=?");
            ps.setString(1, uuid.toString());

            ResultSet results = ps.executeQuery();
            if (results.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //*
    // Voeg een death toe bij de player
    //*
    public void addDeaths(UUID uuid, int deaths) {
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE playerdata SET DEATHS=? WHERE UUID=?");
            ps.setInt(1, (getDeaths(uuid) + deaths));
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //*
    // Krijg het aantal deaths van een player
    //*
    public int getDeaths(UUID uuid) {
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT DEATHS FROM playerdata WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            int deaths = 0;
            if (rs.next()) {
                deaths = rs.getInt("DEATHS");
                return deaths;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //*
    // Voeg een kill toe bij een player
    //*
    public void addKills(UUID uuid, int kills) {
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE playerdata SET KILLS=? WHERE UUID=?");
            ps.setInt(1, (getKills(uuid) + kills));
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //*
    // Krijg het aantal kills van een speler
    //*
    public int getKills(UUID uuid) {
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT KILLS FROM playerdata WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            int kills = 0;
            if (rs.next()) {
                kills = rs.getInt("KILLS");
                return kills;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //*
    // Voeg een punt toe bij een speler
    //*
    public void addPoints(UUID uuid, int points) {
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE playerdata SET POINTS=? WHERE UUID=?");
            ps.setInt(1, (getPoints(uuid) + points));
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //*
    // Krijg het aantal punten van een speler
    //*
    public int getPoints(UUID uuid) {
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT POINTS FROM playerdata WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            int points = 0;
            if (rs.next()) {
                points = rs.getInt("POINTS");
                return points;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void updateBlood(UUID uuid, boolean blood) {
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE playerdata SET BLOOD=? WHERE UUID=?");
            ps.setBoolean(1, blood);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //*
    // Krijg het aantal punten van een speler
    //*
    public boolean getBlood(UUID uuid) {
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT BLOOD FROM playerdata WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            boolean blood = true;
            if (rs.next()) {
                blood = rs.getBoolean("BLOOD");
                return blood;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
