package ezxabi.kitpvp.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class PlayerHungerListener implements Listener {
    //*
    // Anti hunger
    //*
    @EventHandler
    public void hunger(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }
}

