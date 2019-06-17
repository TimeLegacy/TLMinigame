package net.timelegacy.tlminigame.listeners;

import net.timelegacy.tlcore.utils.MessageUtils;
import net.timelegacy.tlminigame.TLMinigame;
import net.timelegacy.tlminigame.countdowns.CountdownHandler;
import net.timelegacy.tlminigame.match.GameHandler;
import net.timelegacy.tlminigame.match.GameState;
import net.timelegacy.tlminigame.playerstate.PlayersTeam;
import net.timelegacy.tlminigame.playerstate.SpectatorsTeam;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ConnectionListener implements Listener {

    List<Location> loc = new ArrayList<Location>();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        PlayersTeam
            .addPlayer(p);

        Location lobbyLocation = TLMinigame.getLobby();
        Location location = lobbyLocation;
        Location lookLocation = new Location(TLMinigame.getLobby().getWorld(), TLMinigame.getLobby().getX() + 10, TLMinigame
            .getLobby().getY(), TLMinigame.getLobby().getZ());
        Vector dirBetweenLocations = lookLocation.toVector().subtract(lobbyLocation.toVector());
        location.setDirection(dirBetweenLocations);

        p.teleport(location);

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

        Bukkit.broadcastMessage(MessageUtils.colorize(MessageUtils.MAIN_COLOR + " has joined the game! &f(&7" + Bukkit.getOnlinePlayers().size()
                + "&f/&7" +  "0"/* GET MAX PLAYERS*/) + "&f)");
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (PlayersTeam.isPlaying(p)) {
            PlayersTeam.removePlayer(p);

            if (GameHandler.getState() == GameState.STARTING && PlayersTeam.getPlayers().size() < 2) {
                CountdownHandler.cancelAll();
                GameHandler.setState(GameState.WAITING);
            }
        } else if (SpectatorsTeam.isSpectating(p)) {
            SpectatorsTeam.removeSpectator(p);
        }
    }

}
