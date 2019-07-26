package net.timelegacy.tlminigame.listeners;

import net.timelegacy.tlcore.utils.MessageUtils;
import net.timelegacy.tlminigame.datatype.GamePlayer.Mode;
import net.timelegacy.tlminigame.enums.GameState;
import net.timelegacy.tlminigame.handler.GameHandler;
import net.timelegacy.tlminigame.handler.PlayerHandler;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {

  @EventHandler
  public void onPreJoin(AsyncPlayerPreLoginEvent e) {
    if (!(GameHandler.getGame().getState() == GameState.WAITING
        || GameHandler.getGame().getState() == GameState.STARTING)) {
      e.disallow(Result.KICK_OTHER, "Game is in game.");
    } else {
      if (PlayerHandler.getGamePlayerByMode(Mode.PLAYER).size()
          >= GameHandler.getGame().getMaxPlayers()) {
        e.disallow(Result.KICK_FULL, "Game is full.");
      }
    }
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent e) {
    Player p = e.getPlayer();
    PlayerHandler.addPlayer(p.getUniqueId());

    p.teleport(GameHandler.getGame().getWaitingLobby());

    p.getInventory().clear();
    p.getInventory().setHelmet(null);
    p.getInventory().setChestplate(null);
    p.getInventory().setLeggings(null);
    p.getInventory().setBoots(null);
    p.getActivePotionEffects().clear();
    p.setExp(0);

    p.setGameMode(GameMode.ADVENTURE);

    p.setFoodLevel(20);
    p.setHealth(20);

    Bukkit.broadcastMessage(
        MessageUtils.colorize(
            MessageUtils.MAIN_COLOR
                + e.getPlayer().getName() + " has joined the game! &f(&7"
                + PlayerHandler.getGamePlayerByMode(Mode.PLAYER).size()
                + "&f/&7"
                + GameHandler.getGame().getMaxPlayers()
                + "&f)"));
  }

  @EventHandler
  public void onLeave(PlayerQuitEvent e) {
    Player p = e.getPlayer();
    PlayerHandler.removePlayer(p.getUniqueId());
  }
}
