package me.newpredator.Annihilation.api;

import me.newpredator.Annihilation.maps.GameMap;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameStartEvent extends Event {
    
    private static final HandlerList handlers = new HandlerList();

    private GameMap m;
    
    public GameStartEvent(GameMap m) {
        this.m = m;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public GameMap getMap() {
        return m;
    }
}
