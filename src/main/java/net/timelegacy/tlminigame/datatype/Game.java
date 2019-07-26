package net.timelegacy.tlminigame.datatype;

import net.timelegacy.tlcore.datatype.MinigameServer.State;
import net.timelegacy.tlminigame.TLMinigame;
import net.timelegacy.tlminigame.countdowns.CountdownHandler;
import net.timelegacy.tlminigame.countdowns.RestartCountdown;
import net.timelegacy.tlminigame.enums.GameState;
import net.timelegacy.tlminigame.events.GameEndEvent;
import net.timelegacy.tlminigame.events.GameStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Game {

  private String gameName;
  private int minPlayers;
  private int maxPlayers;
  private GameState state;
  private Location waitingLobby;

  public Game(String gameName) {
    state = GameState.WAITING;
    waitingLobby = new Location(Bukkit.getWorld("world"), 1402.5, 15, 21.5);
    minPlayers = 2;
    maxPlayers = 50;

    TLMinigame.minigameServer.setGame(gameName);
  }

  // CHANGE DEPENDING ON THE GAME
  public void start() {
    setState(GameState.INGAME);
    Bukkit.getServer().getPluginManager().callEvent(new GameStartEvent());
  }

  // CHANGE DEPENDING ON THE GAME
  public void end() {
    setState(GameState.RESTARTING);
    Bukkit.getServer().getPluginManager().callEvent(new GameEndEvent());
    CountdownHandler.start(new RestartCountdown(), 15);
  }

  public String getGameName() {
    return gameName;
  }

  public void setGameName(String gameName) {
    this.gameName = gameName;
  }

  public int getMinPlayers() {
    return minPlayers;
  }

  public void setMinPlayers(int minPlayers) {
    this.minPlayers = minPlayers;
  }

  public int getMaxPlayers() {
    return maxPlayers;
  }

  public void setMaxPlayers(int maxPlayers) {
    this.maxPlayers = maxPlayers;
  }

  public GameState getState() {
    return state;
  }

  public void setState(GameState state) {
    this.state = state;
    if (state != GameState.STARTING) {
      TLMinigame.minigameServer.setState(State.valueOf(state.name()));
    }
  }

  public Location getWaitingLobby() {
    return waitingLobby;
  }

  public void setWaitingLobby(Location waitingLobby) {
    this.waitingLobby = waitingLobby;
  }
}
