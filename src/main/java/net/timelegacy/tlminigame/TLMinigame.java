package net.timelegacy.tlminigame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.timelegacy.tlcore.datatype.MinigameServer;
import net.timelegacy.tlcore.handler.ServerHandler;
import net.timelegacy.tlminigame.listeners.ConnectionListener;
import net.timelegacy.tlminigame.listeners.LobbyListener;
import net.timelegacy.tlminigame.playerstate.PlayersTeam;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author MasterEjay & Nyarrth
 */
public class TLMinigame extends JavaPlugin {

    private static TLMinigame plugin = null;

  public static MinigameServer minigameServer;

  public static void loadLobby() {
    List<Location> loc = new ArrayList<Location>();
    loc.add(new Location(Bukkit.getWorld("world"), 1402.5, 15, 21.5));

    Random r = new Random();
    int lr = r.nextInt(loc.size());

    lobbyLocation = ((Location) loc.get(lr));
  }

  private static Location lobbyLocation;

    @SuppressWarnings("deprecation")
    @Override
    public void onEnable() {
        plugin = this;

        getServer().getPluginManager().registerEvents(new ConnectionListener(), this);
        getServer().getPluginManager().registerEvents(new LobbyListener(), this);

        loadLobby();

      minigameServer = new MinigameServer(ServerHandler.getServerUUID());

        for(Player p : Bukkit.getOnlinePlayers()){
            PlayersTeam.addPlayer(p);
        }
    }

    public static Location getLobby() {
        return lobbyLocation;
    }

    public static TLMinigame getPlugin() {
        return plugin;
    }

}
