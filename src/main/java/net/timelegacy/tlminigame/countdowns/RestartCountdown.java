package net.timelegacy.tlminigame.countdowns;

import net.timelegacy.tlcore.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RestartCountdown extends Countdown {

    @Override
    public void onEnd() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.kickPlayer(MessageUtils.colorize("&cServer restarting!"));
        }
    }

    public void tick(int secs) {
        if ((secs % 5 == 0) || (secs < 5)) {
            for(Player player : Bukkit.getOnlinePlayers()) {
                    MessageUtils.sendMessage(player,"&cRestarting server in &4&o" + secs + "&c " + (secs > 1 ? "seconds!" : "second!"), true);
            }
        }
    }
}
