package me.newpredator.Annihilation.object;

import java.util.HashMap;

import me.newpredator.Annihilation.Annihilation;

import org.bukkit.entity.Player;

public class PlayerMeta {
    static Annihilation plugin;


    public int health;
    public PlayerMeta(Annihilation pl){
        plugin = pl;
    }
    
    private static String username;
    private static Player playerx;
    private static HashMap<String, PlayerMeta> metaTable = new HashMap<String, PlayerMeta>();

    public static PlayerMeta getMeta(Player player) {
        username = player.getName();
        playerx = player;
        return getMeta(player.getName());
    }

    public static PlayerMeta getMeta(String username) {
        if (!metaTable.containsKey(username)) {
            metaTable.put(username, new PlayerMeta());
        }
        return metaTable.get(username);
    }

    public String getName() {
        return username;
    }

    public Player getPlayer() {
        return playerx;
    }

    public static void reset() {
        metaTable.clear();
    }

    private GameTeam team;
    private Kit kit;
    private boolean alive;

    public PlayerMeta() {
        team = GameTeam.NONE;
        kit = Kit.ALDEANO;
        alive = false;
    }

    public void addHeart(Player p){
        p.setMaxHealth(p.getMaxHealth() + 2);
    }
 
    public void setTeam(GameTeam t) {
        if (team != null) {
            team = t;
        } else {
            team = GameTeam.NONE;
        }
    }

    public GameTeam getTeam() {
        return team;
    }

    public void setKit(Kit k) {
        if (k != null) {
            kit = k;
        } else {
            kit = Kit.ALDEANO;
        }
    }
    

    public Kit getKit() {
        return kit;
    }

    public void setAlive(boolean b) {
        alive = b;
    }

    public boolean isAlive() {
        return alive;
    }



}