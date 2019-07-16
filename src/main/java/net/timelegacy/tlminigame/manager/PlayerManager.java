package net.timelegacy.tlminigame.manager;

import java.util.HashMap;
import java.util.UUID;
import net.timelegacy.tlminigame.game.GamePlayer;
import org.bukkit.OfflinePlayer;

public class PlayerManager {

  private static HashMap<UUID, GamePlayer> playerMap = new HashMap<UUID, GamePlayer>();

  /**
   * Get the GamePlayer given a Bukkit Player. GamePlayers are initiated once per server session, and are saved whether
   * or not a player is online.
   *
   * @return GamePlayer, should never be null
   */
  public static GamePlayer getGamePlayer(OfflinePlayer player) {
    GamePlayer pl = null;
    if (playerMap.containsKey(player.getUniqueId())) {
      pl = playerMap.get(player.getUniqueId());
    } else {
      GamePlayer gpl = new GamePlayer(player);
      playerMap.put(player.getUniqueId(), gpl);
      pl = gpl;
    }
    return pl;
  }


}
