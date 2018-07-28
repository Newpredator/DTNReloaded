package me.newpredator.Annihilation.listeners;

import me.newpredator.Annihilation.Annihilation;
import me.newpredator.Annihilation.manager.PlayerSerializer;
import me.newpredator.Annihilation.object.GameTeam;
import me.newpredator.Annihilation.object.PlayerMeta;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;

public class ZombieListener implements Listener {

    private Annihilation plugin;

    public ZombieListener(Annihilation pl) {
        plugin = pl;
    }

	@EventHandler
    public void onTakeDamage(EntityDamageByEntityEvent ev) {
        Entity ent = ev.getEntity();
        EntityType entt = ent.getType();
        if (entt != EntityType.ZOMBIE) {
            return;
        }
        if (entt == null) {
            return;
        }
        Zombie z = (Zombie) ev.getEntity();
        if (ev.getDamager() instanceof Snowball) {
            if (ev.getDamager() == null) {
                return;
            }
            Snowball s = (Snowball) ev.getDamager();
            if (s == null) {
                return;
            }
            if (s.getShooter() == null) {
                ev.setCancelled(true);
                return;
            }
            if (s.getShooter() instanceof Player) {
                Player p = (Player) s.getShooter();
                PlayerMeta meta = PlayerMeta.getMeta(p);
                if (meta == null) {
                    return;
                }
                if (z.getCustomName() == null) {
                    z.remove();
                    return;
                }
                try {
                    if (GameTeam.getTeamChar(z.getCustomName()).equals(meta.getTeam().getNexus().getTeam())) {
                        p.sendMessage(ChatColor.RED + "No puedes golpear al NPC de tu equipo.");
                        ev.setCancelled(true);
                    }
                } catch (Exception e) {
                }
            }
        }
        if (ev.getDamager() instanceof Arrow) {
            if (ev.getDamager() == null) {
                return;
            }
            Arrow a = (Arrow) ev.getDamager();
            if (a == null) {
                return;
            }
            if (a.getShooter() == null) {
                ev.setCancelled(true);
                return;
            }
            if (a.getShooter() instanceof Player) {
                Player p = (Player) a.getShooter();
                PlayerMeta meta = PlayerMeta.getMeta(p);
                if (meta == null) {
                    return;
                }
                if (z.getCustomName() == null) {
                    z.remove();
                    return;
                }
                try {
                    if (GameTeam.getTeamChar(z.getCustomName()).equals(meta.getTeam().getNexus().getTeam())) {
                        p.sendMessage(ChatColor.RED + "No puedes golpear al NPC de tu equipo.");
                        ev.setCancelled(true);
                    }
                } catch (Exception e) {
                }
            }
        }
        if (ev.getDamager() instanceof Player) {
            if (ev.getDamager() == null) {
                return;
            }
            Player p = (Player) ev.getDamager();
            if (p == null) {
                return;
            }
            PlayerMeta meta = PlayerMeta.getMeta(p);
            if (meta == null) {
                return;
            }
            if (z.getCustomName() == null) {
                z.remove();
                return;
            }
            try {
                if (GameTeam.getTeamChar(z.getCustomName()).equals(meta.getTeam().getNexus().getTeam())) {
                    p.sendMessage(ChatColor.RED + "No puedes golpear al NPC de tu equipo.");
                    ev.setCancelled(true);
                }
            } catch (Exception e) {
            }
        }
    }

	@EventHandler
    public void onDeathEntity(EntityDeathEvent e) {
        if (e.getEntity() instanceof Zombie) {
            e.getDrops().clear();
            Zombie z = (Zombie) e.getEntity();
            if (z.getCustomName() == null) {
                return;
            }
            String zName = (z.getCustomName());
            e.getDrops().addAll(PlayerSerializer.dropItem(zName));
            if (plugin.getZombies().containsKey(z.getCustomName())) {
                plugin.getZombies().remove(zName);
                z.remove();
                PlayerSerializer.removeItems(zName);
            }
        }
    }

	@EventHandler
    public void onDamageEntity(EntityDamageEvent e) {
        if (e.getEntity() instanceof Zombie) {
            Zombie z = (Zombie) e.getEntity();
            if (z.getCustomName() == null) {
                return;
            }
            if (e.getDamage() >= z.getHealth() || e.getEntity().getLocation().getY() <= 0) {
                String zName = z.getCustomName();
                if (plugin.getZombies().containsKey(z.getCustomName())) {
                    plugin.getZombies().remove(zName);
                    z.remove();
                    z.setHealth(0);
                    PlayerSerializer.removeItems(zName);
                }
            }
        }
    }

    @EventHandler
    public void onTarget(EntityTargetEvent e) {
        if (e.getEntity() instanceof Zombie) {
            Zombie z = (Zombie) e.getEntity();
            if (e.getTarget() instanceof Player) {
                Player p = (Player) e.getTarget();
                PlayerMeta meta = PlayerMeta.getMeta(p);
                if (z.getCustomName() == null) {
                    z.remove();
                    return;
                }
                if (GameTeam.getTeamChar(z.getCustomName()).equals(meta.getTeam().getNexus().getTeam())) {
                    e.setCancelled(true);
                    e.setTarget(null);
                }
            }
        }
    }
}
