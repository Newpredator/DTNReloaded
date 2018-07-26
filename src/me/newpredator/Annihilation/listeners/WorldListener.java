package me.newpredator.Annihilation.listeners;

import java.util.HashSet;
import java.util.Set;

import me.newpredator.Annihilation.Annihilation;
import me.newpredator.Annihilation.Util;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class WorldListener implements Listener {
    Annihilation plugin;
    private static final Set<EntityType> hostileEntityTypes = new HashSet<EntityType>() {
        private static final long serialVersionUID = 42L;
        {
            add(EntityType.SKELETON);
            add(EntityType.CREEPER);
            add(EntityType.SPIDER);
            add(EntityType.BAT);
            add(EntityType.ENDERMAN);
            add(EntityType.SLIME);
            add(EntityType.WITCH);
            add(EntityType.ZOMBIE);
        }
    };

    @EventHandler
    public void onWaterFlow(BlockFromToEvent e) {
        if (Util.isEmptyColumn(e.getToBlock().getLocation()))
            e.setCancelled(true);
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        if (isHostile(e.getEntityType())){
            if(e.getSpawnReason() == SpawnReason.CUSTOM){
                return;
            }
            e.setCancelled(true);
        }
    }

    private boolean isHostile(EntityType entityType) {
        return hostileEntityTypes.contains(entityType);
    }
}
