package ezxabi.kitpvp.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Chat implements Listener {
    //*
    // Custom chat
    //*
    @EventHandler
    public void Chat(AsyncPlayerChatEvent e) {
        String message = e.getMessage();
        e.setCancelled(true);
        Bukkit.getServer().broadcastMessage("§7" + e.getPlayer().getName() + "» §7" + message);
    }
}
