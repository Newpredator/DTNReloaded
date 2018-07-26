package me.newpredator.Annihilation.api;

import me.newpredator.Annihilation.object.GameTeam;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class NexusDestroyEvent extends Event {

    private Player p;
    private GameTeam t;
    
    public NexusDestroyEvent(Player p, GameTeam t) {
        this.p = p;
        this.t = t;
    }
    
    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer() {
        return p;
    }
    
    public GameTeam getTeam() {
        return t;
    }
}
