package net.timelegacy.tlminigame.game;

import java.io.File;
import java.io.IOException;
import net.timelegacy.tlminigame.TLMinigame;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;

public class Arena {

  private String id;

  private ArenaSettings settings;

  private Location spectatorSpawn;

  private Location lobbySpawn;

  private World world;

  /**
   * Arena object, used in Game to define Arena world. If the world exists as a folder in the server directory but isn't
   * loaded, it will be loaded. If it doesn't exist as a folder and isn't laoded, one will be created.
   *
   * @param id Unique Arena ID. Also used for world name.
   */
  public Arena(String id) {
    this.settings = new ArenaSettings();
    this.id = id;
    if (Bukkit.getWorld(id) == null) {
      Bukkit.createWorld(new WorldCreator(id));
    }
    this.world = Bukkit.getWorld(id);
    //this.saveDefault();
  }

  public String getName() {
    return this.id;
  }

  /**
   * Get the global ArenaSettings for this arena.
   *
   * @return ArenaSettings
   */
  public ArenaSettings getArenaSettings() {
    return this.settings;
  }

  /**
   * Get the Bukkit world for this arena.
   *
   * @return World Bukkit world.
   */
  public World getWorld() {
    return world;
  }

  /**
   * Get the spectator spawnpoint.
   *
   * @return Location spawnpoint.
   */
  public Location getSpectatorSpawn() {
    return spectatorSpawn;
  }

  /**
   * Set the spawn used for spectators spawning.
   */
  public void setSpectatorSpawn(Location spectatorSpawn) {
    this.spectatorSpawn = spectatorSpawn;
  }

  /**
   * Get the lobby spawnpoint.
   *
   * @return Location lobby spawnpoint.
   */
  public Location getLobbySpawn() {
    return lobbySpawn;
  }

  /**
   * Set the spawn used when the game status is waiting.
   */
  public void setLobbySpawn(Location lobbySpawn) {
    this.lobbySpawn = lobbySpawn;
  }

  /**
   * Saves current world as the default, used when resetting the world.
   */
  public void saveDefault() {
    TLMinigame
        .sendDebugMessage("Saving default world for " + this.getWorld().getName() + "...", TLMinigame.getPlugin());
    File worldFolder = world.getWorldFolder();
    try {
      File dest = new File(TLMinigame.getGameWorldsFolder(), this.world.getName());
      if (dest.exists()) {
        TLMinigame.sendDebugMessage("Deleting existing default world " + this.getWorld().getName() + "...",
            TLMinigame.getPlugin());
        FileUtils.deleteDirectory(dest);
      }
      TLMinigame.sendDebugMessage("Copying world " + this.getWorld().getName() + " to defaults folder...",
          TLMinigame.getPlugin());
      FileUtils.copyDirectory(worldFolder, new File(TLMinigame.getGameWorldsFolder(), this.world.getName()));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Resets the world to saved default. Players should NOT be in the world. As a precaution against world corruption,
   * any players in the world are kicked from the server.
   */
  public void resetWorld() {
    TLMinigame.sendDebugMessage("Resetting arena world " + this.getWorld().getName() + "!", TLMinigame.getPlugin());
    File worldFolder = world.getWorldFolder();
    String worldName = world.getName();
    for (Player player : this.getWorld().getPlayers()) {
      player.kickPlayer("World resetting...");
    }
    TLMinigame.sendDebugMessage("Unloading world " + worldName + "...", TLMinigame.getPlugin());
    Bukkit.unloadWorld(worldName, true);
    Bukkit.getScheduler().scheduleSyncDelayedTask(TLMinigame.getPlugin(), new Runnable() {

      @Override
      public void run() {
        try {
          TLMinigame.sendDebugMessage("Deleting world " + worldName + "...", TLMinigame.getPlugin());
          FileUtils.deleteDirectory(worldFolder);
          TLMinigame
              .sendDebugMessage("Copying default world from GameWorlds/" + worldName + "...", TLMinigame.getPlugin());
          FileUtils.copyDirectory(new File(TLMinigame.getGameWorldsFolder(), worldName), new File(
              TLMinigame.getGameWorldsFolder().getParentFile(), worldName));
          TLMinigame.sendDebugMessage("Loading world " + worldName + " on server...", TLMinigame.getPlugin());
          WorldCreator creator = new WorldCreator(worldName);
          creator.generatorSettings("3;minecraft:air;127;");
          creator.type(WorldType.FLAT);
          world = Bukkit.createWorld(creator);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

    }, 20);


  }


}
