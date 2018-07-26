package me.newpredator.Annihilation.listeners;

import me.newpredator.Annihilation.Annihilation;
import me.newpredator.Annihilation.Util;
import me.newpredator.Annihilation.chat.ChatUtil;
import me.newpredator.Annihilation.object.Boss;
import me.newpredator.Annihilation.object.PlayerMeta;
import me.newpredator.Annihilation.stats.StatsType;
import me.newpredator.Annihilation.stats.StatsUtil;

import org.bukkit.Bukkit;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

public class BossListener implements Listener {

    private Annihilation plugin;

    public BossListener(Annihilation instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onHit(EntityDamageEvent event) {
        if (event.getEntity() instanceof IronGolem) {
            final IronGolem g = (IronGolem) event.getEntity();
            if (g.getCustomName() == null)
                return;

            final Boss b = plugin.getBossManager().bossNames.get(g
                    .getCustomName());
            if (b == null)
                return;

            if (event.getCause() == DamageCause.VOID) {
                event.getEntity().remove();

                Bukkit.getScheduler().runTask(plugin, new Runnable() {
            
                    
                    @Override
                    public void run() {
                        Boss n = plugin.getBossManager().newBoss(b);
                        plugin.getBossManager().spawn(n);
                    }
                });
            }
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof IronGolem) {
            if (!(event.getDamager() instanceof Player))
                event.setCancelled(true);

            final IronGolem g = (IronGolem) event.getEntity();
            if (g.getCustomName() == null)
                return;

            final Boss b = plugin.getBossManager().bossNames.get(g
                    .getCustomName());
            if (b == null)
                return;


            Bukkit.getScheduler().runTask(plugin, new Runnable() {
                @Override
                public void run() {
                    plugin.getBossManager().update(b, g);
                }
            });
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof IronGolem) {
            IronGolem g = (IronGolem) event.getEntity();
            if (g.getCustomName() == null)
                return;

            Boss b = plugin.getBossManager().bossNames.get(g.getCustomName());
            if (b == null)
                return;

            event.getDrops().clear();

            b.spawnLootChest();

            if (g.getKiller() != null) {
                Player killer = g.getKiller();
                ChatUtil.bossDeath(b, killer, PlayerMeta.getMeta(killer)
                        .getTeam());
                respawn(b);
                if(killer.hasPermission("vip.guardian")){
                	StatsUtil.addStat(StatsType.GEMAS, killer.getUniqueId().toString(), plugin.getConfigManager().getConfig("config.yml").getInt("Money-boss-kill") * 2);
                    killer.sendMessage("§a+" + plugin.getConfigManager().getConfig("config.yml").getInt("Money-boss-kill") * 2 + " Gemas");
                                            }else{
                         StatsUtil.addStat(StatsType.GEMAS, killer.getUniqueId().toString(), plugin.getConfigManager().getConfig("config.yml").getInt("Money-boss-kill"));
                         killer.sendMessage("§a+" + plugin.getConfigManager().getConfig("config.yml").getInt("Money-boss-kill") + " Gemas");
                                            }
                killer.giveExp(plugin.getConfigManager().getConfig("config.yml").getInt("Exp-boss-kill"));
                Util.spawnFirework(event.getEntity().getLocation(), PlayerMeta.getMeta(killer).getTeam().getColor(PlayerMeta.getMeta(killer).getTeam()), PlayerMeta.getMeta(killer).getTeam().getColor(PlayerMeta.getMeta(killer).getTeam()));
            } else {
                g.teleport(b.getSpawn());
            }
        }
    }

    private void respawn(final Boss b) {
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                Boss n = plugin.getBossManager().newBoss(b);
                ChatUtil.bossRespawn(b);
                plugin.getBossManager().spawn(n);
                	
                
            }
        }, 20 * plugin.respawn * 60);
    }
    
}
