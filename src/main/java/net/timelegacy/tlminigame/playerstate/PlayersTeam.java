package net.timelegacy.tlminigame.playerstate;

import net.timelegacy.tlminigame.countdowns.CountdownHandler;
import net.timelegacy.tlminigame.countdowns.StartCountdown;
import net.timelegacy.tlminigame.events.PlayerRemoveEvent;
import net.timelegacy.tlminigame.match.GameHandler;
import net.timelegacy.tlminigame.match.GameState;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayersTeam {

    private static List<Player> players = new ArrayList<Player>();

    public static void addPlayer(Player player) {
        if (players.contains(player)) {
            return;
        }
        players.add(player);
        player.setGameMode(GameMode.ADVENTURE);

        if (players.size() >= GameHandler
            .getMinPlayers() && GameHandler.getState() != GameState.STARTING) {
            GameHandler.setState(GameState.STARTING);
            CountdownHandler.start(new StartCountdown(), 15);
        }
    }

    public static List<Player> getPlayers() {
        return players;
    }

    public static void removePlayer(Player player) {

        Bukkit.getServer().getPluginManager().callEvent(new PlayerRemoveEvent(player));
        if (players.contains(player)) {
            players.remove(player);

            if (players.size() == 1) {
                if (GameHandler.getState() == GameState.INGAME) {
                    GameHandler.finish(players.get(0).getName());
                }
            }else if (GameHandler.getState() == GameState.INGAME && Bukkit.getOnlinePlayers().size() < 1) {
                    GameHandler.finish(null);
            }
        }
    }

    public static boolean isPlaying(Player player) {
        if (players.contains(player)) {
            return true;
        } else {
            return false;
        }
    }

}
