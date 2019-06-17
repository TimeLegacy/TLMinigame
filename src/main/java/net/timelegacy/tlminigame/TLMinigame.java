package net.timelegacy.tlminigame;

import net.timelegacy.tlminigame.listeners.ConnectionListener;
import net.timelegacy.tlminigame.listeners.LobbyListener;
import net.timelegacy.tlminigame.playerstate.PlayersTeam;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author MasterEjay & Nyarrth
 */
public class TLMinigame extends JavaPlugin {

    private static TLMinigame plugin = null;

    @SuppressWarnings("deprecation")
    @Override
    public void onEnable() {
        plugin = this;

        getServer().getPluginManager().registerEvents(new ConnectionListener(), this);
        getServer().getPluginManager().registerEvents(new LobbyListener(), this);

        loadLobby();

        /*plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                core.serverHandler.setState(core.serverHandler.getServerUID(),
                        gameHandler.getState().toString());
                core.serverHandler.setOnlinePlayers(core.serverHandler.getServerUID(),
                        Bukkit.getOnlinePlayers().size());
                core.serverHandler.setType(core.serverHandler.getServerUID(), "GAME");
            }
        }, 0, 20L * 1);*/

        for(Player p : Bukkit.getOnlinePlayers()){
            PlayersTeam.addPlayer(p);
        }
    }

    private static Location lobbyLocation;

    public static void loadLobby() {
        List<Location> loc = new ArrayList<Location>();
        loc.add(new Location(Bukkit.getWorld("world"), 258.5, 55, 1610.5));

        Random r = new Random();
        int lr = r.nextInt(loc.size());

        lobbyLocation = ((Location) loc.get(lr));
    }

    public static Location getLobby() {
        return lobbyLocation;
    }

    public static TLMinigame getPlugin() {
        return plugin;
    }

}
