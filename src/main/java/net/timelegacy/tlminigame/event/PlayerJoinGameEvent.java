package net.timelegacy.tlminigame.event;

import net.timelegacy.tlminigame.enums.GamePlayerType;
import net.timelegacy.tlminigame.game.Game;
import net.timelegacy.tlminigame.game.GamePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerJoinGameEvent extends Event {

  private static final HandlerList handlers = new HandlerList();

  private GamePlayer player;
  private Game game;
  private GamePlayerType type;

  public PlayerJoinGameEvent(GamePlayer player, Game game) {
    this.player = player;
    this.game = game;
    this.type = GamePlayerType.PLAYER;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

  public GamePlayerType getGamePlayerType() {
    return this.type;
  }

  public void setGamePlayerType(GamePlayerType type) {
    this.type = type;
  }

  public GamePlayer getPlayer() {
    return this.player;
  }

  public Game getGame() {
    return this.game;
  }

  public HandlerList getHandlers() {

    return handlers;
  }

}
