package net.timelegacy.tlminigame.countdowns;

import java.util.ArrayList;
import java.util.List;

public class CountdownHandler {
  static List<CountdownRunnable> runnables = new ArrayList<CountdownRunnable>();

  /**
   * Method to start a countdown
   *
   * @param countdown
   * @param seconds
   */
  public static void start(Countdown countdown, int seconds) {
    runnables.add(new CountdownRunnable(countdown).start(seconds));
  }

  /**
   * Method to cancel a countdown
   *
   * @param countdown
   */
  public static void cancel(Countdown countdown) {
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
  public static void cancelAll() {
    for (CountdownRunnable runnable : runnables) {
      runnable.cancel();
    }
  }

  /**
   * Adds seconds to a countdown
   *
   * @param countdown
   * @param seconds
   */
  public static void addTime(Countdown countdown, int seconds) {
    for (CountdownRunnable runnable : runnables) {
      if (runnable.getCountdown() == countdown) {
        runnable.setSecondsLeft(runnable.getSecondsLeft() + seconds);
        break;
      }
    }
  }

  /**
   * Get seconds left in countdown
   */

  public static Integer getTime(Countdown countdown) {
    for (CountdownRunnable runnable : runnables) {
      if (runnable.getCountdown() == countdown) {
        return runnable.getSecondsLeft();
      }
    }

    return 0;
  }
}
