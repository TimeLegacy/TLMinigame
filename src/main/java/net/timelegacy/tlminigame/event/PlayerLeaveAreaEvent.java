package net.timelegacy.tlminigame.event;

import net.timelegacy.tlminigame.game.Area;
import net.timelegacy.tlminigame.game.GamePlayer;

public class PlayerLeaveAreaEvent extends AreaEvent {

  public PlayerLeaveAreaEvent(GamePlayer player, Area area) {
    super(player, area);
  }

}
