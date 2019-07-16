package net.timelegacy.tlminigame.event;

import net.timelegacy.tlminigame.game.Game;
import net.timelegacy.tlminigame.game.GamePlayer;
import net.timelegacy.tlminigame.game.Team;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AutoTeamCompensationEvent extends Event implements Cancellable {

  public static final HandlerList handlers = new HandlerList();
  private boolean cancelled = false;

  private Team from;
  private Team to;
  private Game game;
  private GamePlayer player;

  public AutoTeamCompensationEvent(Team from, Team to, GamePlayer player, Game game) {
    this.from = from;
    this.to = to;
    this.player = player;
    this.game = game;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

  public Team getFrom() {
    return this.from;
  }

  public Team getTo() {
    return this.to;
  }

  public GamePlayer getPlayer() {
    return this.player;
  }

  public Game getGame() {
    return this.game;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  @Override
  public boolean isCancelled() {
    return cancelled;
  }

  @Override
  public void setCancelled(boolean cancel) {
    this.cancelled = cancel;
  }

}
