package me.newpredator.Annihilation.manager;

import java.util.Random;

import me.newpredator.Annihilation.object.GameTeam;
import me.newpredator.Annihilation.object.PlayerMeta;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundManager {
    private static Random rand = new Random();

    public static void playSound(Location loc, Sound sound, float volume,
            float minPitch, float maxPitch) {
        loc.getWorld().playSound(loc, sound, volume,
                randomPitch(minPitch, maxPitch));
    }

    public static void playSoundForPlayer(Player p, Sound sound, float volume,
            float minPitch, float maxPitch) {
        p.playSound(p.getLocation(), sound, volume,
                randomPitch(minPitch, maxPitch));
    }

    public static void playSoundForTeam(GameTeam team, Sound sound, float volume,
            float minPitch, float maxPitch) {
        for (Player p : Bukkit.getOnlinePlayers())
            if (PlayerMeta.getMeta(p).getTeam() == team)
            playSoundForPlayer(p, sound, volume, minPitch, maxPitch);
    }

    private static float randomPitch(float min, float max) {
        return min + rand.nextFloat() * (max - min);
    }
}
