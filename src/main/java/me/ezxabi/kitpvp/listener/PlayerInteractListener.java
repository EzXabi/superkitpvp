package me.ezxabi.kitpvp.listener;

import me.ezxabi.kitpvp.inventories.KitMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {
    @EventHandler
    public void playerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (player.getInventory().getItemInHand().getType() == Material.BLAZE_ROD) {
                KitMenu.open(event.getPlayer());
            }
        }
    }
}
