package net.timelegacy.tlminigame.listener;

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
      if (player.getGame().getGameSettings().shouldLeavePlayerOnDisconnect()) {
        player.getGame().removePlayer(player);
      }
    }
  }

}
