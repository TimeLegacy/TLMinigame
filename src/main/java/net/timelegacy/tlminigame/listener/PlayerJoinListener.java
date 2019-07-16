package net.timelegacy.tlminigame.listener;

import net.timelegacy.tlminigame.TLMinigame;
import net.timelegacy.tlminigame.game.Game;
import net.timelegacy.tlminigame.game.GamePlayer;
import net.timelegacy.tlminigame.manager.GameManager;
import net.timelegacy.tlminigame.manager.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

  @EventHandler
  public void playerJoin(PlayerJoinEvent e) {
    GamePlayer player = PlayerManager.getGamePlayer(e.getPlayer());
    player.setPlayer(e.getPlayer());
    for (Game game : GameManager.getGames()) {
      if (game.getGameSettings().usesBungee()) {

        game.addPlayer(player);

        TLMinigame.sendDebugMessage("Adding player to " + game.getID(), TLMinigame.getPlugin());

        break;
      }
    }
  }
}
