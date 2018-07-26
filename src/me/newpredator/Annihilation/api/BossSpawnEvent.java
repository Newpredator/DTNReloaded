package me.newpredator.Annihilation.api;

import me.newpredator.Annihilation.object.Boss;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BossSpawnEvent extends Event {

    private Boss b;
    
    public BossSpawnEvent(Boss b) {
        this.b = b;
    }
    
    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Boss getBoss() {
        return b;
    }
}
