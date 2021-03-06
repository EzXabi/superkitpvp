package me.ezxabi.kitpvp.listener;

import me.ezxabi.kitpvp.SuperKitPvP;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerMoveListener implements Listener {
    List<UUID> jumppad = new ArrayList<UUID>();
    private final SuperKitPvP plugin;

    public PlayerMoveListener(SuperKitPvP plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (p.getLocation().getBlock().getType() == Material.GOLD_PLATE) {
            Vector v = p.getLocation().getDirection().multiply(3.0).setY(1.0);
            jumppad.add(p.getUniqueId());
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> jumppad.remove(p.getUniqueId()), 200);

            p.setVelocity(v);
            p.playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 3);
            p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 3.0f, 2.0f);
        }
    }

    @EventHandler
    public void fall(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                Player p = (Player) e.getEntity();
                if (jumppad.contains(p.getUniqueId())) {
                    e.setCancelled(true);
                }
            }
        }
    }
}

