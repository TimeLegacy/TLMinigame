package net.timelegacy.tlminigame.listener;

import net.timelegacy.tlminigame.TLMinigame;
import net.timelegacy.tlminigame.enums.GamePlayerType;
import net.timelegacy.tlminigame.enums.GameStatus;
import net.timelegacy.tlminigame.enums.PlayerJoinLimitAction;
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
      if (game.getGameStatus() == GameStatus.INGAME) {
        PlayerJoinLimitAction playerJoinLimitAction = game.getGameSettings().getLimitedPlayerJoinAction();
        switch (playerJoinLimitAction) {
          case SPECTATOR:
            game.addPlayer(player);
            game.setPlayerMode(GamePlayerType.SPECTATOR, player);
            player.getOnlinePlayer().teleport(game.getArena().getSpectatorSpawn());
            break;
          case DISALLOW:
            e.getPlayer().kickPlayer("This game is currently ingame.");
        }
        break;
      } else {
        game.addPlayer(player);

        player.getOnlinePlayer().setFoodLevel(20);
        player.getOnlinePlayer().setExp(0);
        player.getOnlinePlayer().getInventory().clear();

        TLMinigame.sendDebugMessage("Adding " + player.getOnlinePlayer().getName() + " to " + game.getID(),
            TLMinigame.getPlugin());
        break;
      }
    }
  }
}
