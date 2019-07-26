package net.timelegacy.tlminigame.listeners;

import net.timelegacy.tlminigame.enums.GameState;
import net.timelegacy.tlminigame.handler.GameHandler;
import org.bukkit.GameRule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.world.WorldLoadEvent;

public class GameListener implements Listener {

  @EventHandler
  public void onWorldLoad(WorldLoadEvent e) {
    e.getWorld().setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent e) {
    if (GameHandler.getGame().getState() == GameState.STARTING
        || GameHandler.getGame().getState() == GameState.WAITING) {

      e.setCancelled(true);
    }
  }

  @EventHandler
  public void onBlockPlace(BlockPlaceEvent e) {
    if (GameHandler.getGame().getState() == GameState.STARTING
        || GameHandler.getGame().getState() == GameState.WAITING) {

      e.setCancelled(true);
    }
  }

  @EventHandler
  public void onHunger(FoodLevelChangeEvent e) {
    if (e.getEntity() instanceof Player) {

      if (GameHandler.getGame().getState() == GameState.WAITING
          || GameHandler.getGame().getState() == GameState.STARTING) {
        e.setCancelled(true);
      }
    }
  }

  @EventHandler
  public void onDamage(EntityDamageEvent e) {
    if (e.getEntity() instanceof Player) {

      if (GameHandler.getGame().getState() == GameState.WAITING
          || GameHandler.getGame().getState() == GameState.STARTING) {
        e.setCancelled(true);
      }
    }
  }
}
