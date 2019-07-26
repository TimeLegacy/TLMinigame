package net.timelegacy.tlminigame.handler;

import net.timelegacy.tlminigame.datatype.Game;

public class GameHandler {

  private static Game game;

  public static Game getGame() {
    return game;
  }

  public static void setGame(Game newGame) {
    game = newGame;
  }

}
