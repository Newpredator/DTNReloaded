package me.newpredator.Annihilation.object;

import me.newpredator.Annihilation.Annihilation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Bloc {

    public int x;
    public int y;
    public int z;
    public Location location;
    private final String world;
    public final String name;
    private final Material material;
    public int x2;
    public int y2;
    public int z2;
    public Location location2;
    private final String world2;
    private final Material material2;
    private Boolean online;
    private static Annihilation plugin;

    @SuppressWarnings("static-access")
	public Bloc(Location l, Location l2, String name, Boolean b, Annihilation pl) {
        this.plugin = pl;
        this.online = b;
        this.name = name;
        this.location = l;
        this.material = l.getBlock().getType();
        this.world = l.getWorld().getName();
        this.x = l.getBlockX();
        this.y = l.getBlockY();
        this.z = l.getBlockZ();
        this.location2 = l2;
        this.material2 = l2.getBlock().getType();
        this.world2 = l2.getWorld().getName();
        this.x2 = l2.getBlockX();
        this.y2 = l2.getBlockY();
        this.z2 = l2.getBlockZ();
        Bukkit.getWorld(world).getBlockAt(l).setType(Material.QUARTZ_ORE);
        Bukkit.getWorld(world2).getBlockAt(l2).setType(Material.QUARTZ_ORE);
    }

    private int getX() {
        return x;
    }

    private int getY() {
        return y;
    }

    private int getZ() {
        return z;
    }
    
    private String getWorld(){
        return world;
    }
    
    private Material getMaterial(){
        return material;
    }
 
    private int getX2() {
        return x2;
    }

    private int getY2() {
        return y2;
    }

    private int getZ2() {
        return z2;
    }
    
    private String getWorld2(){
        return world2;
    }
    
    private Material getMaterial2(){
        return material2;
    }
    
    public static Material getMaterial(Bloc c){
        return c.getMaterial();
    }
    
     public static Material getMaterial2(Bloc c){
        return c.getMaterial2();
    }  
     
    public static String getName(Bloc c){
        return c.name;
    } 
    public static Location getLocation(Bloc c){
        Location loc = new Location(Bukkit.getWorld(c.getWorld()), c.getX(), c.getY(), c.getZ());
        return loc;
    }

    public static Location getLocation2(Bloc c){
        Location loc = new Location(Bukkit.getWorld(c.getWorld2()), c.getX2(), c.getY2(), c.getZ2());
        return loc;
    }    
    
    public static void delete(Bloc c){
        plugin.cabiII.deleteTele(c);
        Bukkit.getWorld(c.world).getBlockAt(c.location).setType(c.material);
        Bukkit.getWorld(c.world2).getBlockAt(c.location2).setType(c.material2);
        Player pl = Bukkit.getPlayer(c.name);
        if(pl.isOnline()){
            pl.sendMessage(ChatColor.RED + "Portales Destruidos");
            pl.playSound(pl.getLocation(), Sound.ENTITY_FIREWORK_TWINKLE, 1, 2);
        }
        c.online = false;
    }
    
    public Location getTeleLoc(Bloc c, Location l){
        if(compareLocation(c.location, l)){
            return c.location2;
        }
        if(compareLocation(c.location2, l)){
            return c.location;
        }
        return null;
    }
    
    public boolean isOnline(Bloc c){
        return c.online;
    }
 
    public static boolean isBlock(Bloc b, Location l) {
        if (b.getX() == l.getBlockX() && b.getY() == l.getBlockY() && b.getZ() == l.getBlockZ() ||
                b.getX2() == l.getBlockX() && b.getY2() == l.getBlockY() && b.getZ2() == l.getBlockZ()) {
            return true;
        }
        return false;
    }
        public boolean compareLocation(Location loc, Location secondLoc) {
        if (loc.getBlockX() != secondLoc.getBlockX()) {
            return false;
        }
        if (loc.getBlockY() != secondLoc.getBlockY()) {
            return false;
        }
        if (loc.getBlockZ() != secondLoc.getBlockZ()) {
            return false;
        }
        if (loc.getWorld().getName() != secondLoc.getWorld().getName()) {
            return false;
        }
        return true;
    }
}