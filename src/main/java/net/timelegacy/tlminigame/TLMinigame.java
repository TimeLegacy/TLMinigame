package net.timelegacy.tlminigame;

import net.timelegacy.tlcore.datatype.MinigameServer;
import net.timelegacy.tlcore.handler.ServerHandler;
import net.timelegacy.tlminigame.listeners.ConnectionListener;
import net.timelegacy.tlminigame.listeners.GameListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author MasterEjay & Nyarrth
 */
public class TLMinigame extends JavaPlugin {

  public static MinigameServer minigameServer;
  private static TLMinigame plugin = null;

  public static TLMinigame getPlugin() {
    return plugin;
  }

  @SuppressWarnings("deprecation")
  @Override
  public void onEnable() {
    plugin = this;

    getServer().getPluginManager().registerEvents(new ConnectionListener(), this);
    getServer().getPluginManager().registerEvents(new GameListener(), this);

    minigameServer = new MinigameServer(ServerHandler.getServerUUID());

    Bukkit.getWorld("world").setPVP(false);
  }
}
