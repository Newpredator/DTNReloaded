package me.newpredator.Annihilation.object;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class BlockObject {
    Block block;
    int x;
    int y;
    int z;
    World world;
    Location location;
    Material material;
    public BlockObject(Block b){
        block = b;
        location = b.getLocation();
        world = location.getWorld();
        material = block.getType();
        x = location.getBlockX();
        y = location.getBlockY();
        z = location.getBlockZ();
    }

    public Location getLocation(){
        return new Location(world, x, y, z);
    }
    
    public Material getType(){
        return material;
    }
    
    public static boolean isBlock(Block b, BlockObject bo){
        Location loc = b.getLocation();
        Location lo = bo.getLocation();
        if(loc.getBlockX() != lo.getBlockX()){
            return false;
        }
        if(loc.getBlockY() != lo.getBlockY()){
            return false;
        }
        if(loc.getBlockZ() != lo.getBlockZ()){
            return false;
        }
        if(loc.getWorld() != lo.getWorld()){
            return false;
        }
        if(b.getType() != bo.getType()){
            return false;
        }
        return true;
    }
}
