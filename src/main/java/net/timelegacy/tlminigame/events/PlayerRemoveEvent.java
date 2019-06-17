package net.timelegacy.tlminigame.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerRemoveEvent extends Event {

    private Player player;

    public PlayerRemoveEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}