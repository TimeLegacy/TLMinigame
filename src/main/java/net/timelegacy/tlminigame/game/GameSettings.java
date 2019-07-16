package net.timelegacy.tlminigame.game;

import net.timelegacy.tlminigame.enums.PlayerJoinLimitAction;
import net.timelegacy.tlminigame.enums.TeamSpreadType;
import org.bukkit.GameMode;
import org.bukkit.Location;

public class GameSettings {

  private int minPlayers;
  private int maxPlayers;
  private int countdownTimer;
  private boolean enableBungee;
  private Location lobbySpawn;
  private PlayerJoinLimitAction limitAction;
  private boolean shouldLeavePlayerOnDisconnect;
  private boolean useTeams;
  private TeamSpreadType spreadType;
  private GameMode mode;
  private GameMode spectatorMode;
  private boolean teleportPlayersOnGameStart;
  private boolean automaticCountdown;
  private int foodLevel;
  private double healthLevel;
  private int minTeamSize;
  private int maxTeamSize;
  private boolean autoTeamCompensation;
  private boolean displayVanillaDeathMessages;
  private boolean resetWorlds;

  public GameSettings() {
    loadDefaults();
  }

  /**
   * Reset defaults.
   */
  public void loadDefaults() {
    this.minPlayers = 2;
    this.maxPlayers = 8;
    this.countdownTimer = 60;
    this.limitAction = PlayerJoinLimitAction.DISALLOW;
    this.shouldLeavePlayerOnDisconnect = true;
    this.useTeams = false;
    this.spreadType = TeamSpreadType.EVEN;
    this.mode = GameMode.SURVIVAL;
    this.spectatorMode = GameMode.SPECTATOR;
    this.setTeleportPlayersOnGameStart(true);
    this.setAutomaticCountdown(true);
    this.foodLevel = 20;
    this.healthLevel = 20.0d;
    this.minTeamSize = 1;
    this.maxTeamSize = 4;
    this.autoTeamCompensation = false;
    this.enableBungee = false;
    this.displayVanillaDeathMessages = true;
    this.resetWorlds = false;
  }

  public boolean shouldResetWorlds() {
    return this.resetWorlds;
  }

  public void setResetWorlds(boolean reset) {
    this.resetWorlds = reset;
  }

  public boolean shouldDisableVanillaDeathMessages() {
    return this.displayVanillaDeathMessages;
  }

  public void setDisableVanillaDeathMessages(boolean display) {
    this.displayVanillaDeathMessages = display;
  }

  /**
   * Get whether or not teams should automatically be re-arranged if the team spread type is set to EVEN. Auto
   * compensation occurs when during the PlayerLeaveGameEvent. Default: false
   */
  public boolean getAutomaticTeamSizeCompensationEnabled() {
    return this.autoTeamCompensation;
  }

  /**
   * Set whether or not teams should automatically be re-arranged if the team spread type is set to EVEN. Auto
   * compensation occurs when during the PlayerLeaveGameEvent. Default: false
   */
  public void setAutomaticTeamSizeCompensationEnabled(boolean enable) {
    this.autoTeamCompensation = enable;
  }

  /**
   * Get the minimum team size. Only used if teams are enabled.
   *
   * @return Minimum team size.
   */
  public int getMinimumTeamSize() {
    return this.minTeamSize;
  }

  /**
   * Set the minimum team size. Only used if teams are enabled.
   *
   * @param minSize Minimum team size.
   */
  public void setMinimumTeamSize(int minSize) {
    this.minTeamSize = minSize;
  }

  /**
   * Get the maximum team size. Only used if teams are enabled.
   *
   * @return Maximum team size.
   */
  public int getMaximumTeamSize() {
    return this.maxTeamSize;
  }

  /**
   * Set the maximum team size. Only used if teams are enabled.
   *
   * @param maxSize Maximum team size.
   */
  public void setMaximumTeamSize(int maxSize) {
    this.maxTeamSize = maxSize;
  }

  /**
   * Get whether or not the countdown should start when the minimum players required are filled.
   */
  public boolean getAutomaticCountdown() {
    return automaticCountdown;
  }

  /**
   * Set whether or not the countdown should start when the minimum players required are filled.
   */
  public void setAutomaticCountdown(boolean automaticCountdown) {
    this.automaticCountdown = automaticCountdown;
  }

  /**
   * Whether or not players should be teleported to team spawns or game spawns on game start.
   */
  public boolean shouldTeleportPlayersOnGameStart() {
    return teleportPlayersOnGameStart;
  }

  /**
   * Set whether or not players should be teleported to team spawns or game spawns on game start.
   */
  public void setTeleportPlayersOnGameStart(boolean teleportPlayersOnGameStart) {
    this.teleportPlayersOnGameStart = teleportPlayersOnGameStart;
  }

  /**
   * Get the Minecraft GameMode that players should be in while playing the game.
   */
  public GameMode getMode() {
    return mode;
  }


  /**
   * Set the Minecraft GameMode that players should be in while playing the game.
   */
  public void setMode(GameMode mode) {
    this.mode = mode;
  }

  /**
   * Get the Minecraft GameMode that spectators should be in while playing the game.
   */
  public GameMode getSpectatorMode() {
    return spectatorMode;
  }

  /**
   * Set the Minecraft GameMode that spectators should be in while playing the game.
   */
  public void setSpectatorMode(GameMode spectatorMode) {
    this.spectatorMode = spectatorMode;
  }

  /**
   * Get the team spread type. See TeamSpreadType.EVEN and TeamSpreadType.FIRST_AVAILABLE for more info.
   *
   * @return Team spread type.
   */
  public TeamSpreadType getTeamSpreadType() {
    return this.spreadType;
  }

  /**
   * Set the team spread type. See TeamSpreadType.EVEN and TeamSpreadType.FIRST_AVAILABLE for more info.
   */
  public void setTeamSpreadType(TeamSpreadType type) {
    this.spreadType = type;
  }

  /**
   * Whether or not teams are enabled. If enabled, players will automatically be added to an available team, sorted via
   * the set TeamSpreadType.
   *
   * @return boolean
   */
  public boolean shouldUseTeams() {
    return this.useTeams;
  }

  /**
   * Set whether or not teams are enabled. If enabled, players will automatically be added to an available team, sorted
   * via the set TeamSpreadType.
   */
  public void shouldUseTeams(boolean should) {
    this.useTeams = should;
  }

  /**
   * Whether or not the player should be removed from the game when they disconnect from the server.
   */
  public boolean shouldLeavePlayerOnDisconnect() {
    return this.shouldLeavePlayerOnDisconnect;
  }

  /**
   * Set whether or not the player should be removed from the game when they disconnect from the server.
   */
  public void shouldLeavePlayerOnDisconnect(boolean should) {
    this.shouldLeavePlayerOnDisconnect = should;
  }

  /**
   * Get whether or not the game uses Bungee servers.
   *
   * @return Bungee enabled
   */
  public boolean usesBungee() {
    return this.enableBungee;
  }

  /**
   * Set whether or not the game should use Bungee servers.
   *
   * @param bungee boolean.
   */
  public void setUsesBungee(boolean bungee) {
    this.enableBungee = bungee;
  }

  public Location getLobbyLocation() {
    return this.lobbySpawn;
  }

  /**
   * Set the lobby location. Only used if bungee mode is disabled.
   */
  public void setLobbyLocation(Location location) {
    this.lobbySpawn = location;
  }

  /**
   * Get the minimum players required the start this game.
   *
   * @return int number of players
   */
  public int getMinimumPlayers() {
    return this.minPlayers;
  }

  /**
   * Get the minimum players required the start this game.
   *
   * @param min minimum amount of players.
   */
  public void setMinimumPlayers(int min) {
    this.minPlayers = min;
  }

  /**
   * Get the maximum amount of players that can join a game. You can define what happens when a player attempts to join
   * when the limit is filled with GameSettings.getLimitedPlayerJoinAction().
   *
   * @return int max players.
   */
  public int getMaximumPlayers() {
    return maxPlayers;
  }

  /**
   * Get the maximum amount of players that can join a game. You can define what happens when a player attempts to join
   * when the limit is filled with GameSettings.setLimitedPlayerJoinAction().
   */
  public void setMaximumPlayers(int max) {
    this.maxPlayers = max;
  }

  public PlayerJoinLimitAction getLimitedPlayerJoinAction() {
    return this.limitAction;
  }

  /**
   * When the minimum player count is met, it will start the countdown timer.
   *
   * @return countdown time in seconds.
   */
  public int getCountdownTime() {
    return countdownTimer;
  }

  /**
   * When the minimum player count is met, it will start the countdown timer.
   */
  public void setCountdownTime(int seconds) {
    this.countdownTimer = seconds;
  }

  public int getFoodLevel() {
    return foodLevel;
  }

  public void setFoodLevel(int foodLevel) {
    this.foodLevel = foodLevel;
  }

  public double getHealthLevel() {
    return healthLevel;
  }

  public void setHealthLevel(double healthLevel) {
    this.healthLevel = healthLevel;
  }

}
