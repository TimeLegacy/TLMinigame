package net.timelegacy.tlminigame.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.timelegacy.tlminigame.countdowns.CountdownHandler;
import net.timelegacy.tlminigame.countdowns.StartCountdown;
import net.timelegacy.tlminigame.datatype.GamePlayer;
import net.timelegacy.tlminigame.enums.GameState;
import net.timelegacy.tlminigame.events.GamePlayerAddEvent;
import net.timelegacy.tlminigame.events.GamePlayerRemoveEvent;
import org.bukkit.Bukkit;

public class PlayerHandler {

  private static HashMap<UUID, GamePlayer> gamePlayers = new HashMap<>();

  public static void addPlayer(UUID uuid) {
    if (!gamePlayers.containsKey(uuid)) {
      gamePlayers.put(uuid, new GamePlayer(uuid));

      Bukkit.getServer().getPluginManager().callEvent(new GamePlayerAddEvent(new GamePlayer(uuid)));
    }

    if (gamePlayers.size() >= GameHandler.getGame().getMinPlayers() && !(GameHandler.getGame().getState()
        == GameState.STARTING)) {
      GameHandler.getGame().setState(GameState.STARTING);
      CountdownHandler.start(new StartCountdown(), 15);
    }
  }

  public static void removePlayer(UUID uuid) {
    if (gamePlayers.containsKey(uuid)) {
      gamePlayers.remove(uuid);
      Bukkit.getServer().getPluginManager().callEvent(new GamePlayerRemoveEvent(new GamePlayer(uuid)));
    }

    if (gamePlayers.size() < 1) {
      if (GameHandler.getGame().getState() != GameState.RESTARTING) {
        GameHandler.getGame().end();
      }
    }
  }

  public static GamePlayer getGamePlayer(UUID uuid) {
    if (gamePlayers.containsKey(uuid)) {
      return gamePlayers.get(uuid);
    }

    return null;
  }

  public static List<GamePlayer> getGamePlayerByMode(GamePlayer.Mode mode) {
    List<GamePlayer> gamePlayerList = new ArrayList<>();

    for (Map.Entry<UUID, GamePlayer> gamePlayerEntry : gamePlayers.entrySet()) {
      if (gamePlayerEntry.getValue().getMode() == mode) {
        gamePlayerList.add(gamePlayerEntry.getValue());
      }
    }

    return gamePlayerList;
  }
}
