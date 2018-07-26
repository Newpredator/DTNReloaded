package me.newpredator.Annihilation.object;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import me.newpredator.Annihilation.Annihilation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public enum GameTeam {

    RED, YELLOW, GREEN, BLUE, NONE;

    private final ChatColor color;
    private List<Location> spawns;
    private Nexus nexus;
    private Annihilation plugin;
    GameTeam() {
        this.plugin = Annihilation.getInstance();
        if (name().equals("NONE")) {
            color = ChatColor.WHITE;
        } else {
            color = ChatColor.valueOf(name());
        }

        spawns = new ArrayList<Location>();
    }

    @Override
    public String toString() {
        return name().substring(0, 1) + name().substring(1).toLowerCase();
    }

    public String coloredName() {
        return color().toString() + toString();
    }

    public ChatColor color() {
        return color;
    }

    public Nexus getNexus() {
        if (this != NONE) {
            return nexus;
        } else {
            return null;
        }
    }

    public void loadNexus(Location loc, int health) {
        if (this != NONE) {
            nexus = new Nexus(this, loc, health);
        }
    }

    public void addSpawn(Location loc) {
        if (this != NONE) {
            spawns.add(loc);
        }
    }

  @SuppressWarnings("static-access")
public Location getRandomSpawn() {
        if (!spawns.isEmpty() && this != NONE) {
            return spawns.get(new Random().nextInt(spawns.size()));
        }
        Annihilation.getInstance().log(spawns.size() +  " - " + this.toString(), Level.INFO);
        return plugin.getMapManager().getAnnihilationSpawnPoint();
    }

    public List<Location> getSpawns() {
        return spawns;
    }

    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<Player>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (PlayerMeta.getMeta(p).getTeam() == this && this != NONE) {
                players.add(p);
            }
        }
        return players;
    }

    public static GameTeam[] teams() {
        return new GameTeam[]{RED, YELLOW, GREEN, BLUE};
    }

    public Color getColor(GameTeam gt) {
        if (gt == GameTeam.RED) {
            return Color.RED;
        }
        if (gt == GameTeam.BLUE) {
            return Color.BLUE;
        }
        if (gt == GameTeam.GREEN) {
            return Color.LIME;
        }
        if (gt == GameTeam.YELLOW) {
            return Color.YELLOW;
        }

        return null;
    }

    public ChatColor getChatColor(GameTeam gt) {
        if (gt == GameTeam.RED) {
            return ChatColor.RED;
        }
        if (gt == GameTeam.BLUE) {
            return ChatColor.BLUE;
        }
        if (gt == GameTeam.GREEN) {
            return ChatColor.GREEN;
        }
        if (gt == GameTeam.YELLOW) {
            return ChatColor.YELLOW;
        }
        return null;
    }
    
    public static String getName(GameTeam gt) {
        
        return gt.toString().toLowerCase();
        
       /* if (gt == GameTeam.RED) {
            return "red";
        }
        if (gt == GameTeam.BLUE) {
            return "blue";
        }
        if (gt == GameTeam.GREEN) {
            return "green";
        }
        if (gt == GameTeam.YELLOW) {
            return "yellow";
        }
        return "none";*/
    }

    public static GameTeam getTeam(String s) {
        
        return GameTeam.valueOf(s.toUpperCase());
        
        /*if (s.equals("red")) {
            return GameTeam.RED;
        }
        if (s.equals("blue")) {
            return GameTeam.BLUE;
        }
        if (s.equals("green")) {
            return GameTeam.GREEN;
        }
        if (s.equals("yellow")) {
            return GameTeam.YELLOW;
        }
        return GameTeam.NONE;*/
    }

    public static String getNameChar(GameTeam gt) {
        if (gt == GameTeam.RED) {
            return "§c";
        }
        if (gt == GameTeam.BLUE) {
            return "§9";
        }
        if (gt == GameTeam.GREEN) {
            return "§a";
        }
        if (gt == GameTeam.YELLOW) {
            return "§e";
        }
        return "§fOla";
    }

    public static GameTeam getTeamChar(String s) {
        if(s == null){
            return GameTeam.NONE;
        }
        if (s.contains("§c")) {
            return GameTeam.RED;
        }
        if (s.contains("§9")) {
            return GameTeam.BLUE;
        }
        if (s.contains("§e")) {
            return GameTeam.YELLOW;
        }
        if (s.contains("§a")) {
            return GameTeam.GREEN;
        }
        return GameTeam.NONE;
    }
    
    public static boolean isNull(GameTeam t){
        if(t == null || t == GameTeam.NONE){
           return true; 
        }
        return false;
    }
}
