package net.timelegacy.tlminigame.listeners;

import net.timelegacy.tlminigame.match.GameHandler;
import net.timelegacy.tlminigame.match.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class LobbyListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (GameHandler.getState() == GameState.STARTING || GameHandler.getState() == GameState.WAITING) {

            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (GameHandler.getState() == GameState.STARTING || GameHandler.getState() == GameState.WAITING) {

            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player) {

            if (GameHandler.getState() == GameState.WAITING
                    || GameHandler.getState() == GameState.STARTING) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {

            if (GameHandler.getState() == GameState.WAITING
                    || GameHandler.getState() == GameState.STARTING) {
                e.setCancelled(true);
            }
        }
    }

}
