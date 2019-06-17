package net.timelegacy.tlminigame.match;

import net.timelegacy.tlminigame.TLMinigame;
import net.timelegacy.tlminigame.countdowns.CountdownHandler;
import net.timelegacy.tlminigame.countdowns.RestartCountdown;
import net.timelegacy.tlminigame.events.GameEndEvent;
import net.timelegacy.tlminigame.events.GameStartEvent;
import net.timelegacy.tlminigame.map.Map;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameHandler {

    private static GameState state = GameState.WAITING;
    private static int minPlayers = 2;
    public static Map currentMap;
    public static List<Map> maps = new ArrayList<>();

    // CHANGE DEPENDING ON THE GAME
    public static void start() {
        setState(GameState.INGAME);
        Bukkit.getServer().getPluginManager().callEvent(new GameStartEvent());

        Bukkit.getScheduler().scheduleSyncRepeatingTask(TLMinigame.getPlugin()
            , new Runnable() {
            public void run() {
                Bukkit.getWorld(currentMap.getWorldName()).setTime(1000);
                Bukkit.getWorld(currentMap.getWorldName()).setStorm(false);
                Bukkit.getWorld(currentMap.getWorldName()).setThundering(false);
            }
        }, 0, 20 * 20); // 20 ticks = 1 second. So 5 * 20 = 100 which is 5 seconds
    }

    // CHANGE DEPENDING ON THE GAME
    public static void finish(String winner) {

        if (winner == null) {
            winner = "NULL";
        }

        setState(GameState.RESTARTING);
        Bukkit.getServer().getPluginManager().callEvent(new GameEndEvent(winner));

        CountdownHandler.start(new RestartCountdown(), 15);

        Bukkit.getScheduler().scheduleSyncDelayedTask(TLMinigame.getPlugin(), new Runnable() {
            public void run() {
                GameHandler.restart(currentMap.getWorldName());
            }
        }, 20L * 18L); // 20 ticks = 1 second. So 100 ticks = 5 seconds.

    }

    /**
     * DO NOT CHANGE UNLESS THERE ARE MULTIPLE TEAMS
     */

    public static void getRandomMap() {

        Random r = new Random();
        Map m = (Map) maps.get(r.nextInt(maps.size()));
        if ((m == currentMap && (maps.size() != 1))) {
            getRandomMap();
        }else if(maps.size() == 1){
            m = maps.get(0);
        }

        currentMap = m;


        //game.core.serverHandler.setMap(game.core.serverHandler.getServerUID(), game.core.messageUtils.friendlyify(currentMap.getName()));
    }

    /**
     * --------------------------------------- DO NOT CHANGE PAST THIS
     * ---------------------------------------
     */

    // Unloading maps, to rollback maps. Will delete all player builds until last
    // server save
    public static void unloadMap(String mapname) {
        if (Bukkit.getServer().unloadWorld(Bukkit.getServer().getWorld(mapname), false)) {
            System.out.println("Successfully unloaded " + mapname);
        } else {
            System.out.println("COULD NOT UNLOAD " + mapname);
        }
    }

    // Loading maps (MUST BE CALLED AFTER UNLOAD MAPS TO FINISH THE ROLLBACK
    // PROCESS)
    public static void loadMap(String mapname) {
        World w = Bukkit.getServer().createWorld(new WorldCreator(mapname));
        w.setAutoSave(false);
    }

    // Maprollback method, because were too lazy to type 2 lines
    public static void restart(String mapname) {
        unloadMap(mapname);
        loadMap(mapname);

        Bukkit.shutdown();
    }

    public static void setState(GameState s) {
        state = s;
    }

    public static GameState getState() {
        return state;
    }


    private static String gameName = "UNKNOWN";

    public static void setGameName(String name) {
        gameName = name;
        //game.core.serverHandler.setGame(game.core.serverHandler.getServerUID(), name);
    }

    public static String getGameName() {
        return gameName;
    }

    public static int getMinPlayers() {
        return minPlayers;
    }

    public static void setMinPlayers(int minPlayer) {
        minPlayers = minPlayer;
    }

}
