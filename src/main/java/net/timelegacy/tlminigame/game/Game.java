package net.timelegacy.tlminigame.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import net.md_5.bungee.api.ChatColor;
import net.timelegacy.tlcore.datatype.MinigameServer;
import net.timelegacy.tlcore.datatype.MinigameServer.State;
import net.timelegacy.tlcore.handler.ServerHandler;
import net.timelegacy.tlcore.utils.MessageUtils;
import net.timelegacy.tlminigame.TLMinigame;
import net.timelegacy.tlminigame.enums.GamePlayerType;
import net.timelegacy.tlminigame.enums.GameStatus;
import net.timelegacy.tlminigame.enums.TeamSpreadType;
import net.timelegacy.tlminigame.event.AutoTeamCompensationEvent;
import net.timelegacy.tlminigame.event.GameEndEvent;
import net.timelegacy.tlminigame.event.GameStartEvent;
import net.timelegacy.tlminigame.event.PlayerJoinGameEvent;
import net.timelegacy.tlminigame.event.PlayerLeaveGameEvent;
import net.timelegacy.tlminigame.manager.PlayerManager;
import net.timelegacy.tlminigame.manager.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

public class Game {

  private String id;
  private HashMap<GamePlayer, GamePlayerType> playerModes = new HashMap<GamePlayer, GamePlayerType>();
  private GameStatus status;
  private Arena arena;
  private GameSettings settings;
  private TeamManager teamManager;
  private PlayerManager playerManager;
  private String messagePrefix;
  private Plugin plugin;
  private List<Area> areas;
  private List<GamePlayer> players;
  private HashMap<GamePlayer, Integer> kills = new HashMap<GamePlayer, Integer>();
  private List<Location> spawns;
  private boolean countingDown;
  private int currentCountdown;
  private Runnable loop;
  private MinigameServer minigameServer;

  public Game(String id, Arena arena, GameStatus status, Plugin plugin) {
    this.id = id;
    this.arena = arena;
    this.plugin = plugin;
    this.teamManager = new TeamManager();
    this.playerManager = new PlayerManager();
    this.messagePrefix = "";
    this.status = status;
    this.areas = new ArrayList<Area>();
    this.players = new ArrayList<GamePlayer>();
    this.settings = new GameSettings();
    this.currentCountdown = 0;
    this.spawns = new ArrayList<Location>();

    this.minigameServer = new MinigameServer(ServerHandler.getServerUUID());

    this.minigameServer.setGame(id.replace(" ", "").toUpperCase());
    this.minigameServer.setMap(arena.getName());

    this.loop = new Runnable() {

      @Override
      public void run() {

      }
    };
    final Game game = this;
    Bukkit.getScheduler().scheduleSyncRepeatingTask(TLMinigame.getPlugin(), new Runnable() {
      @Override
      public void run() {
        if (game.getGameSettings().getAutomaticCountdown() && !game.isCountingDown() && game.getGameStatus()
            .equals(GameStatus.WAITING)) {
          if (game.isMinimumPlayersFilled()) {
            game.setCountingDown(true);
            game.increaseCountdown();
            game.sendMessage(
                ChatColor.GREEN + "Game starts in " + ChatColor.DARK_GREEN + game.getGameSettings().getCountdownTime()
                    + ChatColor.GREEN + " seconds!");
          }
        }
        if (game.isCountingDown()) {
          if (game.getCurrentCountdown() < game.getGameSettings().getCountdownTime()) {
            TLMinigame.sendDebugMessage(game.getCurrentCountdown() % 10d + "", TLMinigame.getPlugin());
            if (game.getCurrentCountdown() % 10 == 0) {
              game.sendMessage(ChatColor.GREEN + "Game starts in " + ChatColor.DARK_GREEN + (
                  game.getGameSettings().getCountdownTime() - game.getCurrentCountdown()) + ChatColor.GREEN
                  + " seconds!");

            } else if (game.getGameSettings().getCountdownTime() - game.getCurrentCountdown() <= 5) {
              game.sendMessage(ChatColor.GREEN + "Game starts in " + ChatColor.DARK_GREEN + (
                  game.getGameSettings().getCountdownTime() - game.getCurrentCountdown()) + ChatColor.GREEN
                  + " seconds!");

              for (GamePlayer player : game.getPlayers()) {
                player.getOnlinePlayer().sendTitle(MessageUtils.colorize("&aGame starts in"),
                    MessageUtils.colorize("&2" + (
                        game.getGameSettings().getCountdownTime() - game.getCurrentCountdown())), 20, 20, 20);
              }
            }

            game.increaseCountdown();
          } else {
            if (game.isMinimumPlayersFilled()) {
              game.startGame();
              game.resetCurrentCountdown();
              game.setCountingDown(false);
            } else {
              game.sendMessage(ChatColor.RED + "Not enough players to start game.");

              for (GamePlayer player : game.getPlayers()) {
                player.getOnlinePlayer()
                    .sendTitle(MessageUtils.colorize("&cNot enough players to start game."), "", 20, 20, 20);
              }

              game.resetCurrentCountdown();
              game.setCountingDown(false);
            }
          }
        }
        game.getRunnable().run();
      }

    }, 0L, 20L);
  }

  public int getKills(GamePlayer p) {
    return this.kills.get(p);
  }

  public void increaseKills(GamePlayer p, int amount) {
    this.kills.put(p, this.getKills(p) + amount);
  }

  public boolean isMinimumPlayersFilled() {
    return this.getPlayers().size() >= this.getGameSettings().getMinimumPlayers();
  }

  public void setPlayerMode(GamePlayerType type, GamePlayer player) {
    switch (type) {
      case PLAYER:
        if (player.isOnline()) {
          player.getOnlinePlayer().setGameMode(this.getGameSettings().getMode());
        }
        break;
      case SPECTATOR:
        if (player.isOnline()) {
          player.getOnlinePlayer().setGameMode(this.getGameSettings().getSpectatorMode());
        }
        break;
      default:
        break;

    }
    this.playerModes.put(player, type);
  }

  public GamePlayerType getGamePlayerType(GamePlayer player) {
    return this.playerModes.get(player);
  }

  public List<GamePlayer> getGamePlayerByMode(GamePlayerType type) {
    List<GamePlayer> players = new ArrayList<GamePlayer>();
    for (GamePlayer player : this.getPlayers()) {
      if (this.getGamePlayerType(player).equals(type)) {
        players.add(player);
      }
    }
    return players;
  }

  public boolean isCountingDown() {
    return countingDown;
  }


  public void setCountingDown(boolean countingDown) {
    this.countingDown = countingDown;
  }


  /**
   * Add a game spawn. These are only used when teams are disabled, for use in place of team spawns.
   */
  public void addSpawn(Location location) {
    this.spawns.add(location);
  }

  /**
   * Get all game spawns. These are only used when teams are disabled, for use in place of team spawns.
   */
  public List<Location> getSpawns() {
    return this.spawns;
  }

  /**
   * Clear all normal spawns. These are only used when teams are disabled, for use in place of team spawns.
   */
  public void clearSpawns() {
    this.spawns.clear();
  }


  /**
   * Utility variable. Get the current countdown, can be used to start off the game when there are enough players.
   * Starts at 0 and increases, subtract from GameSettings.getCountdownTime().
   *
   * @return Current countdown time variable.
   */
  public int getCurrentCountdown() {
    return this.currentCountdown;
  }

  /**
   * Utility variable. Get the current countdown, can be used to start off the game when there are enough players.
   * Starts at 0 and increases, subtract from GameSettings.getCountdownTime(). Resets to 0.
   *
   * @return Current countdown time variable.
   */
  public int resetCurrentCountdown() {
    this.currentCountdown = 0;
    return this.currentCountdown;
  }

  /**
   * Utility variable. Get the current countdown, can be used to start off the game when there are enough players.
   * Starts at 0 and increases, subtract from GameSettings.getCountdownTime(). Increases countdown by 1.
   *
   * @return Current countdown time variable.
   */
  public int increaseCountdown() {
    this.currentCountdown++;
    return this.currentCountdown;
  }

  /**
   * Add the area to the game.
   */
  public void registerArea(Area area) {
    this.areas.add(area);
  }

  public void registerAreas(Area... areas) {
    for (Area area : areas) {
      this.registerArea(area);
    }
  }

  public Area getArea(String name) {
    for (Area area : this.areas) {
      if (area.getName().equals(name)) {
        return area;
      }
    }
    return null;
  }

  /**
   * Get the plugin that registered this minigame.
   *
   * @return Bukkit Plugin
   */
  public Plugin getRegisteringPlugin() {
    return this.plugin;
  }

  /**
   * Start the game. Doesn't do much other then teleporting team members to their spawn and setting the game status.
   */
  public void startGame() {
    this.setGameStatus(GameStatus.INGAME);
    GameStartEvent ev = new GameStartEvent(this);
    if (this.getGameSettings().shouldTeleportPlayersOnGameStart()) {
      if (this.getGameSettings().shouldUseTeams()) {
        for (Team team : this.getTeamManager().getTeams()) {
          for (GamePlayer player : team.getPlayers()) {
            if (player.isOnline()) {
              Random rand = new Random();
              player.getOnlinePlayer().teleport(team.getTeamSpawns().get(rand.nextInt(team.getTeamSpawns().size())));
            }
          }
        }
      } else {
        for (GamePlayer player : this.getPlayers()) {
          if (player.isOnline()) {
            player.getOnlinePlayer().teleport(this.getSpawns().get(new Random().nextInt(this.getSpawns().size())));
          }
        }
      }
    }
    Bukkit.getPluginManager().callEvent(ev);
  }

  /**
   * End the game, kicking all players and resetting the arena.
   */
  public void endGame() {
    this.setGameStatus(GameStatus.RESTARTING);
    GameEndEvent ev = new GameEndEvent(this);
    Bukkit.getPluginManager().callEvent(ev);
    List<GamePlayer> players = new CopyOnWriteArrayList<GamePlayer>(this.getPlayers());
    this.playerModes = new HashMap<GamePlayer, GamePlayerType>();
    for (GamePlayer pl : players) {
      this.removePlayer(pl);
    }
    if (this.getGameSettings().shouldResetWorlds()) {
      final Game game = this;
      Bukkit.getScheduler().scheduleSyncDelayedTask(TLMinigame.getPlugin(), new Runnable() {

        @Override
        public void run() {
          game.arena.resetWorld();
        }

      }, 5L);
    }

    this.setGameStatus(GameStatus.WAITING);
  }

  /**
   * Add a player to a randomly selected team. Team selection mode is defined by GameSettings.getTeamSpreadType().
   *
   * @return If teams are disabled, all teams are full, or there aren't any registered teams, returns null. Otherwise,
   * the Team that the player was added to.
   */
  public Team addToTeam(GamePlayer player) {
    List<Team> teams = this.getTeamManager().getTeams();
    if (!this.getGameSettings().shouldUseTeams()) {
      return null;
    }
    if (teams.size() == 0) {
      return null;
    }
    switch (this.getGameSettings().getTeamSpreadType()) {
      case EVEN:
        int lowest = this.getGameSettings().getMaximumTeamSize();
        Team lowestTeam = null;
        for (Team team : teams) {
          if (team.getPlayers().size() <= lowest) {
            lowest = team.getPlayers().size();
            lowestTeam = team;
          }
        }
        lowestTeam.addPlayer(player);
        return lowestTeam;
      case FIRST_AVAILABLE:
        for (Team team : teams) {
          if (team.getPlayers().size() < this.getGameSettings().getMaximumTeamSize()) {
            team.addPlayer(player);
            return team;
          }
        }
        return null;
      default:
        return null;

    }
  }

  public List<GamePlayer> getPlayersSortedByKills() {
    List<GamePlayer> pls = this.players;
    Collections.sort(pls, (p1, p2) -> this.getKills(p1) > this.getKills(p2) ? 0 : 1);
    return pls;
  }

  /**
   * Add a player to the game. You must handle teleporting. Teams are not set here, use addToTeam(GamePlayer, [Team]).
   * Player will not be added if already part of the game. The GameMode set in GameSettings.getGameMode() will be
   * applied here.
   */
  public void addPlayer(GamePlayer player) {
    if (!this.players.contains(player)) {
      this.kills.put(player, 0);
      PlayerJoinGameEvent ev = new PlayerJoinGameEvent(player, this);
      this.players.add(player);
      player.setGame(this);
      if (player.isOnline()) {
        player.getOnlinePlayer().setGameMode(this.getGameSettings().getMode());
        player.getOnlinePlayer().setFoodLevel(this.getGameSettings().getFoodLevel());
        player.getOnlinePlayer().setHealth(this.getGameSettings().getHealthLevel());
        if (this.getArena().getLobbySpawn() != null) {
          player.getOnlinePlayer().teleport(this.getArena().getLobbySpawn());
        }
      }
      Bukkit.getPluginManager().callEvent(ev);
      this.setPlayerMode(ev.getGamePlayerType(), player);

    }
  }

  /**
   * Removes the player from the game, and sends them to the lobby set in GameSettings.
   */
  public void removePlayer(GamePlayer player) {
    if (player.isOnline()) {
      this.players.remove(player);
      player.getOnlinePlayer().kickPlayer("");
    }
    if (player.getTeam() != null && this.getGameSettings().shouldUseTeams()) {
      Team team = player.getTeam();
      player.getTeam().removePlayer(player);
      if (this.getTeamManager().getTeams().size() > 1 &&
          this.getGameSettings().getAutomaticTeamSizeCompensationEnabled() &&
          this.getGameSettings().getTeamSpreadType().equals(TeamSpreadType.EVEN)) {
        Team highestTeam = null;
        for (Team t : this.getTeamManager().getTeams()) {
          if (team.getPlayers().size() - 1 < t.getPlayers().size() && t.getPlayers().size() > 0) {
            if (highestTeam != null) {
              if (t.getPlayers().size() > highestTeam.getPlayers().size()) {
                highestTeam = t;
                ;
              }
            } else {
              highestTeam = t;
            }
          }
        }
        if (highestTeam != null) {
          AutoTeamCompensationEvent ev = new AutoTeamCompensationEvent(highestTeam, team,
              highestTeam.getPlayers().get(0), this);
          Bukkit.getPluginManager().callEvent(ev);
          if (!ev.isCancelled()) {
            highestTeam.getPlayers().get(0).setTeam(team);
          }

        }
      }
    }

    player.setGame(null);
    PlayerLeaveGameEvent ev = new PlayerLeaveGameEvent(player, this);
    Bukkit.getPluginManager().callEvent(ev);

  }

  /**
   * Set what happens every second in the game.
   *
   * @param runnable Runnable object.
   */
  public void setLoopRunnable(Runnable runnable) {
    this.loop = runnable;
  }

  /**
   * Get what happens every second in the game.
   *
   * @return Runnable
   */
  public Runnable getRunnable() {
    return this.loop;
  }

  /**
   * Send a game message. Messages are prefixed with the set message prefix (set/getMessagePrefix()) If you want to send
   * without a prefix, use sendMessage(message, boolean usePrefix)
   *
   * @param message Message to be sent.
   */
  public void sendMessage(String message) {
    this.sendMessage(message, true, false);
  }

  /**
   * Send a game message. Messages are prefixed with the set message prefix (set/getMessagePrefix()) If you want to send
   * without a prefix, use sendMessage(message, boolean usePrefix)
   *
   * @param message Message to be sent.
   */
  public void sendMessage(String message, boolean usePrefix, boolean centered) {
    for (GamePlayer player : this.getPlayers()) {
      if (player.isOnline()) {
        if (!centered) {
          MessageUtils.sendMessage(
              player.getOnlinePlayer(), message, usePrefix ? messagePrefix : "");
        } else {
          MessageUtils.sendCenteredMessage(player.getOnlinePlayer(), usePrefix ? messagePrefix : "" + message);
        }
      }
    }
  }

  /**
   * Get the message prefix.
   *
   * @return String message prefix.
   */
  public String getMessagePrefix() {
    return this.messagePrefix;
  }

  /**
   * Set the message prefix. For example, "[Survival Games]"
   *
   * @param prefix Prefix to set.
   */
  public void setMessagePrefix(String prefix) {
    this.messagePrefix = prefix;
  }

  /**
   * Get the unique ID for this game.
   *
   * @return String name
   */
  public String getID() {
    return id;
  }

  /**
   * Get the current game's status
   *
   * @return GameStatus
   */
  public GameStatus getGameStatus() {
    return this.status;
  }

  /**
   * Set the current game status.
   */
  public void setGameStatus(GameStatus status) {
    this.status = status;

    this.minigameServer.setState(State.valueOf(status.toString()));
  }

  /**
   * Get the game settings, used for things like minimum and maximum player count.
   */
  public GameSettings getGameSettings() {
    return this.settings;
  }

  /**
   * Get the Team Manager for this Game, used for creating/managing teams.
   *
   * @return Team Manager
   */
  public TeamManager getTeamManager() {
    return this.teamManager;
  }

  /**
   * Get the Player Manager for this Game.
   *
   * @return Player Manager
   */
  public PlayerManager getPlayerManager() {
    return this.playerManager;
  }

  /**
   * Get all the GamePlayers in this game.
   *
   * @return List<GamePlayer>
   */
  public List<GamePlayer> getPlayers() {
    return this.players;
  }

  /**
   * Get all the areas registered to this Game.
   *
   * @return List<Area>
   */
  public List<Area> getAreas() {
    return areas;
  }

  /**
   * Get the global arena object for this game.
   *
   * @return Arena
   */
  public Arena getArena() {
    return this.arena;
  }

}
