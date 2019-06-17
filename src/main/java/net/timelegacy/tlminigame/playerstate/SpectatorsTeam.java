package net.timelegacy.tlminigame.playerstate;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SpectatorsTeam {

    private static List<Player> spectators = new ArrayList<Player>();

    public static void addSpectator(Player player) {
        if (spectators.contains(player)) {
            return;
        }
        spectators.add(player);
        player.setGameMode(GameMode.SPECTATOR);
        player.teleport(new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() + 10, player.getLocation().getZ()));
    }

    public static List<Player> getSpectators() {
        return spectators;
    }

    public static void removeSpectator(Player player) {
        if (spectators.contains(player)) {
            spectators.remove(player);
        }
    }

    public static boolean isSpectating(Player player) {
        if (spectators.contains(player)) {
            return true;
        } else {
            return false;
        }
    }

}
