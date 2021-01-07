package me.ezxabi.kitpvp.listener;

import me.ezxabi.kitpvp.SuperKitPvP;
import me.ezxabi.kitpvp.manager.ConfigManager;
import me.ezxabi.kitpvp.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerKillListener implements Listener {

    private final SuperKitPvP plugin;

    public PlayerKillListener(SuperKitPvP plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(EntityDamageEvent event) {
        ConfigManager configManager = plugin.getConfigManager();
        FileConfiguration users = configManager.getUsers();
        FileConfiguration config = configManager.getConfig();
        if (event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent newEvent = (EntityDamageByEntityEvent) event;
            if (newEvent.getEntity().getType() == EntityType.PLAYER) {
                if (newEvent.getDamager().getType() == EntityType.PLAYER) {
                    Player player = (Player) newEvent.getEntity();
                    Player killer = (Player) newEvent.getDamager();
                    String deaths = String.format("Players.%s.%s", player.getUniqueId(), "deaths");
                    String coins = String.format("Players.%s.%s", killer.getUniqueId(), "coins");
                    String blood = String.format("Players.%s.%s", killer.getUniqueId(), "blood");
                    String kills = String.format("Players.%s.%s", killer.getUniqueId(), "kills");
                    if (player.getHealth() <= event.getDamage()) {
                        event.setDamage(0.0);
                        player.setHealth(player.getMaxHealth());
                        player.getInventory().clear();
                        player.getInventory().addItem(new ItemStack(Material.BLAZE_ROD, 1));

                        users.set(kills, (users.getInt(kills) + 1));
                        users.set(deaths, (users.getInt(deaths) + 1));
                        users.set(coins, (users.getInt(coins) + 5));
                        configManager.saveUsers(users);
                        configManager.loadUsers();
                        plugin.getScoreboardIngame().show(killer);
                        plugin.getScoreboardIngame().show(player);
                        tryTeleport(player, config);

                        if (users.getBoolean(blood)) {
                            newEvent.getEntity().getWorld().playEffect(newEvent.getEntity().getLocation(), Effect.STEP_SOUND, (Object) Material.REDSTONE_BLOCK);
                        }
                    }
                } else {
                    Player player = (Player) event.getEntity();
                    if (player.getHealth() <= event.getDamage()) {
                        String deaths = String.format("Players.%s.%s", player.getUniqueId(), "deaths");

                        event.setDamage(0.0);
                        player.setHealth(player.getMaxHealth());
                        player.getInventory().clear();
                        player.getInventory().addItem(new ItemStack(Material.BLAZE_ROD, 1));

                        users.set(deaths, (users.getInt(deaths) + 1));
                        configManager.saveUsers(users);
                        configManager.loadUsers();
                        plugin.getScoreboardIngame().show(player);
                        tryTeleport(player, config);
                    }
                }
            }
        }else {
            if (event.getEntity().getType() == EntityType.PLAYER) {
                Player player = (Player) event.getEntity();
                if (player.getHealth() <= event.getDamage()) {
                    String deaths = String.format("Players.%s.%s", player.getUniqueId(), "deaths");

                    event.setDamage(0.0);
                    player.setHealth(player.getMaxHealth());
                    player.getInventory().clear();
                    player.getInventory().addItem(new ItemStack(Material.BLAZE_ROD, 1));

                    users.set(deaths, (users.getInt(deaths) + 1));
                    configManager.saveUsers(users);
                    configManager.loadUsers();
                    plugin.getScoreboardIngame().show(player);
                    tryTeleport(player, config);
                }
            }
        }
    }


    public void tryTeleport(Player player, FileConfiguration config){
        if (config.getDouble("spawn.x") != 0.0
                && config.getDouble("spawn.y") != 0.0
                && config.getDouble("spawn.z") != 0.0
                && !config.getString("spawn.world").equals("")
                && config.getDouble("spawn.yaw") != 0.0
                && config.getDouble("spawn.pitch") != 0.0) {
            double spawnX = config.getDouble("spawn.x");
            double spawnY = config.getDouble("spawn.y");
            double spawnZ = config.getDouble("spawn.z");
            String spawnWorld = config.getString("spawn.world");
            float yaw = (float) config.getDouble("spawn.yaw");
            float pitch = (float) config.getDouble("spawn.pitch");

            Location spawnLocation = new Location(Bukkit.getWorld(spawnWorld), spawnX, spawnY, spawnZ, yaw, pitch);
            player.teleport(spawnLocation);
        } else {
            player.sendMessage(Utils.col("&cYou have not set up a spawn yet!"));
        }
    }
}
