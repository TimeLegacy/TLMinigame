package net.timelegacy.tlminigame.listener;

import net.timelegacy.tlminigame.enums.GamePlayerType;
import net.timelegacy.tlminigame.enums.GameStatus;
import net.timelegacy.tlminigame.game.GamePlayer;
import net.timelegacy.tlminigame.manager.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

  @EventHandler
  public void playerQuit(PlayerQuitEvent e) {
    GamePlayer player = PlayerManager.getGamePlayer(e.getPlayer());
    if (player.getGame() != null) {
      if (player.getGame().getGameStatus() == GameStatus.INGAME
          && player.getGame().getGamePlayerByMode(GamePlayerType.PLAYER).size() <= 1) {
        player.getGame().endGame();
      }

      if (player.getGame().getGameSettings().shouldLeavePlayerOnDisconnect()) {
        player.getGame().removePlayer(player);
      }
    }
  }
}
