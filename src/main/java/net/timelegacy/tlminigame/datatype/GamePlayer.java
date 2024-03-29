package net.timelegacy.tlminigame.datatype;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class GamePlayer {

  private UUID uuid;
  private Mode mode;

  public GamePlayer(UUID uuid) {
    this.uuid = uuid;
    this.mode = Mode.PLAYER;
  }

  public Player getPlayer() {
    return Bukkit.getPlayer(this.uuid);
  }

  public UUID getUUID() {
    return uuid;
  }

  public Mode getMode() {
    return mode;
  }

  public void setMode(Mode mode) {
    this.mode = mode;

    if (mode.equals(Mode.SPECTATOR)) {
      if (getPlayer() != null) {
        Player player = getPlayer();
        player.setGameMode(GameMode.SPECTATOR);
      }
    }
  }

  public enum Mode {
    SPECTATOR, PLAYER;
  }

}
