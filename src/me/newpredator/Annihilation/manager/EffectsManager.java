package me.newpredator.Annihilation.manager;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;

public class EffectsManager {

    public static void playEffectLoc(String worldName, Effect e, Location l) {
        World w = Bukkit.getWorld(worldName);
        w.playEffect(l, e, 1);
    }

    public static void playEffectLoc(World w, Effect e, Location l) {
        w.playEffect(l, e, 1);
    }
}
