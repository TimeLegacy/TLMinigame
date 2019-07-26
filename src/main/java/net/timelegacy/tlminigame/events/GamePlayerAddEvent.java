package net.timelegacy.tlminigame.events;

import net.timelegacy.tlminigame.datatype.GamePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GamePlayerAddEvent extends Event {

  private static final HandlerList handlers = new HandlerList();

  private GamePlayer gamePlayer;

  public GamePlayerAddEvent(GamePlayer gamePlayer) {
    this.gamePlayer = gamePlayer;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

  public GamePlayer getGamePlayer() {
    return gamePlayer;
  }

  public HandlerList getHandlers() {
    return handlers;
  }
}