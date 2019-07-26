package net.timelegacy.tlminigame.countdowns;

import net.timelegacy.tlcore.utils.MessageUtils;
import net.timelegacy.tlminigame.datatype.GamePlayer;
import net.timelegacy.tlminigame.datatype.GamePlayer.Mode;
import net.timelegacy.tlminigame.enums.GameState;
import net.timelegacy.tlminigame.handler.GameHandler;
import net.timelegacy.tlminigame.handler.PlayerHandler;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class StartCountdown extends Countdown {

  @Override
  public void onEnd() {
    if (PlayerHandler.getGamePlayerByMode(Mode.PLAYER).size() < GameHandler.getGame().getMinPlayers()) {
      Bukkit.broadcastMessage(MessageUtils.colorize("&cNot enough players to start the game..."));
      GameHandler.getGame().setState(GameState.WAITING);
    } else {
      GameHandler.getGame().start();
    }
  }

  @SuppressWarnings("deprecation")
  @Override
  public void tick(int secs) {
    if ((secs % 5 == 0) || (secs < 5)) {
      for (GamePlayer gamePlayer : PlayerHandler.getGamePlayerByMode(Mode.PLAYER)) {
        Player player = gamePlayer.getPlayer();
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F);

        player.sendTitle(MessageUtils.colorize("&aGame starts in"), MessageUtils.colorize("&2" + secs), 20, 20, 20);
      }

      Bukkit.broadcastMessage(
          MessageUtils.colorize("&aGame starting in &o&2" +
              secs + "&a " + (secs > 1 ? "seconds!" : "second!")));
    }
  }
}
