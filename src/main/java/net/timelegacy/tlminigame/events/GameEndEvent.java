package net.timelegacy.tlminigame.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameEndEvent extends Event {

    private String winner;

    public GameEndEvent(String winner) {
        this.winner = winner;
    }

    public String getWinner() {
        return this.winner;
    }

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
