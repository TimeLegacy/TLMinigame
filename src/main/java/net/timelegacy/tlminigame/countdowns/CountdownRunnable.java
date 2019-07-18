package net.timelegacy.tlminigame.countdowns;

import net.timelegacy.tlminigame.TLMinigame;
import org.bukkit.Bukkit;

public class CountdownRunnable implements Runnable {

  Countdown countdown;

  int secondsLeft;
  int taskId;

  /**
   * Constructor for countdown runnables, sets primary variables
   */
  public CountdownRunnable(Countdown countdown) {
    this.countdown = countdown;

  }

  /**
   * Starts a countdown with the length of the seconds parameter
   */
  public CountdownRunnable start(int seconds) {
    this.secondsLeft = seconds;
    this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(TLMinigame.getPlugin(), this, 0L, 20L);
    this.countdown.onStart(seconds);
    return this;
  }

  /**
   * Stops the runnable
   */
  public void stop() {
    Bukkit.getScheduler().cancelTask(taskId);
  }

  /**
   * Cancels the runnable
   */
  public void cancel() {
    countdown.onCancel();
    stop();
  }

  /**
   * Main method, runs the actual timer and calls correct events based on the current time
   */
  @Override
  public void run() {
    if (secondsLeft > 0) {
      countdown.tick(secondsLeft);
      secondsLeft -= 1;
    } else {
      countdown.onEnd();
      stop();
    }

  }

  public Countdown getCountdown() {
    return countdown;
  }

  public int getSecondsLeft() {
    return secondsLeft;
  }

  public void setSecondsLeft(int secondsLeft) {
    this.secondsLeft = secondsLeft;
  }
}
