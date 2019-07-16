package net.timelegacy.tlminigame.event;

import net.timelegacy.tlminigame.game.Area;
import net.timelegacy.tlminigame.game.GamePlayer;

public class PlayerEnterAreaEvent extends AreaEvent {

  public PlayerEnterAreaEvent(GamePlayer player, Area area) {
    super(player, area);
  }


}
