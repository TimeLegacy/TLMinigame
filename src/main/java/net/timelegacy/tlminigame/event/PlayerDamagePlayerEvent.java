package net.timelegacy.tlminigame.event;

import net.timelegacy.tlminigame.game.Game;
import net.timelegacy.tlminigame.game.GamePlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.RegisteredListener;

public class PlayerDamagePlayerEvent extends Event implements Cancellable {

  private static final HandlerList handlers = new HandlerList();
  private boolean cancelled = false;
  private GamePlayer damager;
  private GamePlayer damaged;
  private Game game;

  public PlayerDamagePlayerEvent(GamePlayer damager, GamePlayer damaged, Game game) {
    this.damaged = damaged;
    this.damager = damager;
    this.game = game;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

  public GamePlayer getDamager() {
    return this.damager;
  }

  public GamePlayer getDamaged() {
    return this.damaged;
  }

  public Game getGame() {
    return this.game;
  }

  @Override
  public boolean isCancelled() {
    return cancelled;
  }

  @Override
  public void setCancelled(boolean cancel) {
    this.cancelled = cancel;
  }

  @Override
  public HandlerList getHandlers() {
    for (RegisteredListener listener : handlers.getRegisteredListeners()) {
      if (!listener.getPlugin().equals(game.getRegisteringPlugin())) {
        handlers.unregister(listener);
      }
    }
    return handlers;
  }


}
