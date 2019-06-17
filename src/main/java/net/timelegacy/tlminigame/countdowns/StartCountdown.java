package net.timelegacy.tlminigame.countdowns;

import net.timelegacy.tlcore.utils.MessageUtils;
import net.timelegacy.tlminigame.match.GameHandler;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class StartCountdown extends Countdown {

    @Override
    public void onEnd() {
        for (Player p : Bukkit.getOnlinePlayers()) {

            p.sendMessage(MessageUtils.colorize(MessageUtils.SECOND_COLOR + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-"));
            p.sendMessage(MessageUtils.colorize("&7"));
            MessageUtils
                .sendMessage(p, MessageUtils.MAIN_COLOR + "&l" + GameHandler.getGameName(), false);
            p.sendMessage(MessageUtils.colorize("&7"));
            MessageUtils.sendMessage(p, MessageUtils.MAIN_COLOR + "&7&lMap: " + MessageUtils.SECOND_COLOR + "&o" + GameHandler.currentMap.getName() + " &7&lby " + MessageUtils.SECOND_COLOR + "&o" + GameHandler.currentMap.getAuthor(), false);
            p.sendMessage(MessageUtils.colorize("&7"));
            p.sendMessage(MessageUtils.colorize(MessageUtils.SECOND_COLOR + "-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-"));
        }
        GameHandler.start();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void tick(int secs) {
        if ((secs % 5 == 0) || (secs < 5)) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F);

                p.sendTitle(secs + "", "", 20, 20, 20);
                MessageUtils.sendMessage(p,"&aGame starting in &o&2" + secs + "&a " + (secs > 1 ? "seconds!" : "second!"), true);
            }
        }
    }

    @Override
    public void onCancel() {
        Bukkit.broadcastMessage(MessageUtils.colorize("&cNot enough players to start the game..."));
    }
}
