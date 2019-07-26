package net.timelegacy.tlminigame.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameEndEvent extends Event {

  private static final HandlerList handlers = new HandlerList();

  public GameEndEvent() {
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

  public HandlerList getHandlers() {
    return handlers;
  }
}
