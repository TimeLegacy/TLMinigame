package net.timelegacy.tlminigame.event;

import net.timelegacy.tlminigame.game.Game;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.RegisteredListener;

public class GameEndEvent extends Event {

  private static final HandlerList handlers = new HandlerList();
  private Game game;

  public GameEndEvent(Game game) {
    this.game = game;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

  /**
   * Get the game this event refers to.
   *
   * @return Game
   */
  public Game getGame() {
    return game;
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
