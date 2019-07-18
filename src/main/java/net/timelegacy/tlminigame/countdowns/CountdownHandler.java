package net.timelegacy.tlminigame.countdowns;

import java.util.ArrayList;
import java.util.List;

public class CountdownHandler {

  static List<CountdownRunnable> runnables = new ArrayList<CountdownRunnable>();

  /**
   * Method to start a countdown
   */
  public void start(Countdown countdown, int seconds) {
    runnables.add(new CountdownRunnable(countdown).start(seconds));
  }

  /**
   * Method to cancel a countdown
   */
  public void cancel(Countdown countdown) {
    for (CountdownRunnable runnable : runnables) {
      if (runnable.getCountdown() == countdown) {
        runnable.cancel();
        runnables.remove(runnable);
      }
    }
  }

  /**
   * Cancel all countdowns
   */
  public void cancelAll() {
    for (CountdownRunnable runnable : runnables) {
      runnable.cancel();
    }
  }

  /**
   * Adds seconds to a countdown
   */
  public void addTime(Countdown countdown, int seconds) {
    for (CountdownRunnable runnable : runnables) {
      if (runnable.getCountdown() == countdown) {
        runnable.setSecondsLeft(runnable.getSecondsLeft() + seconds);
        break;
      }
    }
  }
}
