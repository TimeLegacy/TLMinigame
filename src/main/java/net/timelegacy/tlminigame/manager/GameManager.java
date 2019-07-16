package net.timelegacy.tlminigame.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.timelegacy.tlminigame.TLMinigame;
import net.timelegacy.tlminigame.game.Game;

public class GameManager {

  private static HashMap<String, Game> games = new HashMap<String, Game>();

  public static void registerGame(Game game) {
    games.put(game.getID(), game);
    TLMinigame.logInfo(game.getRegisteringPlugin().getName() + " registered game " + game.getID() + ".");

  }

  public static List<Game> getGames() {
    List<Game> games = new ArrayList<Game>();
    Iterator<Game> gIt = GameManager.games.values().iterator();
    while (gIt.hasNext()) {
      games.add(gIt.next());
    }
    return games;
  }

  public static Game getGame(String ID) {
    return games.get(ID);
  }

}
