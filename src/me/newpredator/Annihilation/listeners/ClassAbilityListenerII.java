package me.newpredator.Annihilation.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import me.newpredator.Annihilation.Annihilation;
import me.newpredator.Annihilation.Util;
import me.newpredator.Annihilation.object.Bloc;
import me.newpredator.Annihilation.object.Kit;
import me.newpredator.Annihilation.object.PlayerMeta;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ClassAbilityListenerII implements Listener {

    Annihilation plugin;
    private final List<String> tele1;
    private final List<String> tele2;
    private final HashMap<String, Block> prepareTele;
    private final List<Bloc> teles;
    private final List<String> colodown;

	public ClassAbilityListenerII(Annihilation plugin) {
        this.plugin = plugin;
        tele2 = new ArrayList<>();
        tele1 = new ArrayList<>();
        prepareTele = new HashMap<>();
        colodown = new ArrayList<String>();
        teles = new ArrayList<Bloc>();
    }

    public void deleteTele(Bloc c) {
        teles.remove(c);
    }

    public void particles() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                for (Bloc b : teles) {
                    if (!teles.isEmpty()) {
                        if (b.isOnline(b)) {
                            Location bloc = b.location.clone();
                            addY(bloc, 1);
                            Location bloc2 = b.location2.clone();
                            addY(bloc2, 1);
                            Util.ParticleEffects.sendToLocation(Util.ParticleEffects.EXPLODE, bloc, 0.2F, 0.0F, 0.2F, 0, 1);
                        }
                    }
                    
                }
            }
        }, 20L * 2);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        final Player p = e.getEntity();
        String playerName = p.getName();
        PlayerMeta meta = PlayerMeta.getMeta(p);
        Kit kit = meta.getKit();
        if (kit == Kit.TRANSPORTADOR) {
            for (Bloc b : teles) {
                if (!teles.isEmpty()) {
                    if (b.name.equalsIgnoreCase(playerName)) {
                        if (b.isOnline(b)) {
                            Bloc.delete(b);
                            p.sendMessage(ChatColor.RED + "Has muerto, tus portales han sido destruidos");
                            p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_TWINKLE, 1, 2);
                            tele1.remove(playerName);
                            tele2.remove(playerName);
                        }
                    }
                }
            }
        }
    }

	@EventHandler
    public void onAction(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        String playerName = p.getName();
        Location fl = addY(p.getLocation(), -1);
        Block target = fl.getBlock();
        PlayerMeta meta = PlayerMeta.getMeta(p);
        Kit kit = meta.getKit();
        ItemStack hand = p.getInventory().getItemInMainHand();
        if (kit == Kit.TRANSPORTADOR) {
            if (hand == null) {
                return;
            }
            if (hand.getItemMeta() == null) {
                return;
            }
            if (hand.getItemMeta().getDisplayName() == null) {
                return;
            }
            if (!hand.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_PURPLE + "Portal")) {
                return;
            }
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                //start creating tele
                if (!tele1.contains(playerName)) {
                    //create T1
                    if (isAllowedMaterial(target)) {
                        if (!Util.tooClose(fl, plugin)) {
                            for (Bloc bl : teles) {
                                if ((compareLocation(target.getLocation(), bl.location) || compareLocation(target.getLocation(), bl.location2)) && bl.isOnline(bl)) {
                                    p.sendMessage(ChatColor.RED + "Estas encima del portal!");
                                    return;
                                }
                            }
                            for (Entry<String, Block> en : prepareTele.entrySet()) {
                                if (isPrepareBlock(fl, en.getValue().getLocation())) {
                                    p.sendMessage(ChatColor.RED + "Alguien ya preparo un portal aqui!");
                                    return;
                                }
                            }
                            tele1.add(playerName);
                            //prepare block
                            prepareTele.put(playerName, target);
                            p.sendMessage(ChatColor.DARK_PURPLE + "Portal 1 marcado correctamente");
                            p.sendMessage(ChatColor.GRAY + "Si marcas el segundo punto saldran los Portales");
                            p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_HURT, 1, 2);
                        } else {
                            p.sendMessage(ChatColor.RED + "No puedes poner un portal cerca del Nexus");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "No puedes poner el portal en este material");
                    }
                } else {
                    //check T2
                    if (!tele2.contains(playerName)) {
                        //create T2 
                        if (isAllowedMaterial(target)) {
                            if (!Util.tooClose(fl, plugin)) {
                                for (Bloc bl : teles) {
                                    if ((compareLocation(target.getLocation(), bl.location) || compareLocation(target.getLocation(), bl.location2)) && bl.isOnline(bl)) {
                                        p.sendMessage(ChatColor.RED + "Estas encima del portal");
                                        return;
                                    }
                                }
                                for (Entry<String, Block> en : prepareTele.entrySet()) {
                                    if (isPrepareBlock(fl, en.getValue().getLocation())) {
                                        p.sendMessage(ChatColor.RED + "Alguien ya preparo un portal aqui!");
                                        return;
                                    }
                                }
                                tele2.add(playerName);
                                teles.add(new Bloc(prepareTele.get(playerName).getLocation(), target.getLocation(), playerName, true, plugin));
                                prepareTele.remove(playerName);
                                particles();
                                p.sendMessage(ChatColor.GREEN + "Portales Creados");
                                p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_HURT, 1, 2);
                                p.playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 1, 1);
                            } else {
                                p.sendMessage(ChatColor.RED + "El portal esta muy cerca del Nexus");
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "No puedes poner el portal en este material");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "Ya tienes el Portal! " + ChatColor.GREEN + "Para eliminarlo click izquirdo al aire");
                    }
                }
            } else if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
       
                for (Bloc c : teles) {
                    if (c.name.equalsIgnoreCase(playerName) && c.isOnline(c)) {
                        Bloc.delete(c);
                        tele1.remove(playerName);
                        tele2.remove(playerName);
                    }
                }
            }
        }
    }
 

	@SuppressWarnings("unlikely-arg-type")
	@EventHandler
    public void onMove(PlayerMoveEvent e) {
        final Player p = e.getPlayer();
        final String playerName = p.getName();
        Location fl = addY(p.getLocation(), -1);
        Block target = fl.getBlock();
        PlayerMeta meta = PlayerMeta.getMeta(p);
        Kit kit = meta.getKit();
        if(p.getLocation().getBlock().getType() == Material.PORTAL) {
        if (kit == Kit.TRANSPORTADOR) {
            for (Bloc b : teles) {
                if (!teles.isEmpty()) {
                    if (b.name.equalsIgnoreCase(playerName)) {
                        if (b.isOnline(b)) {
                            Bloc.delete(b);
                            p.sendMessage(ChatColor.RED + "Te has cambiado de kit. Los portales desaparecieron.");
                            p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_TWINKLE, 1, 2);
                            tele1.remove(playerName);
                            tele2.remove(playerName);
    		}
                    }
                }
            }
    	}
        }
    
        if (!p.isSneaking()) {
            return;
        }
        if (colodown.contains(playerName)) {
            return;
        }
        if (target.getType() == Material.QUARTZ_ORE) {
            for (Bloc bl : teles) {
                if ((compareLocation(fl, bl.location) || compareLocation(fl, bl.location2)) && bl.isOnline(bl)) {
                    fl = bl.getTeleLoc(bl, fl);
                    if (!p.getActivePotionEffects().contains(PotionEffectType.INVISIBILITY)) {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1, 1));  
                    }
                    PlayerMeta pmeta = PlayerMeta.getMeta(p);
                    PlayerMeta blMeta = PlayerMeta.getMeta(bl.name);
                    if (pmeta.getTeam() != blMeta.getTeam() && bl.isOnline(bl)) {
                        p.sendMessage(ChatColor.RED + "Este portal pertenece al equipo: " + blMeta.getTeam().getChatColor(blMeta.getTeam()) + " " + blMeta.getTeam().name());
                        return;
                    }
                    Location fixedLoc = fl.clone();
                    fixedLoc = addY(fixedLoc, 1);
                    p.teleport(fixedLoc);
                    colodown.add(playerName);
                    p.sendMessage(ChatColor.GREEN + "Transportado!");
                    Location bloc = bl.location.clone();
                    addY(bloc, 2);
                    Location bloc2 = bl.location2.clone();
                    addY(bloc2, 2);
                    Bukkit.getWorld(bloc2.getWorld().getName()).playSound(bloc2, Sound.ENTITY_ENDERMEN_TELEPORT, 1, 1);
                    Bukkit.getWorld(bloc.getWorld().getName()).playSound(bloc, Sound.ENTITY_ENDERMEN_TELEPORT, 1, 1);
                    Bukkit.getWorld(bloc.getWorld().getName()).playEffect(bloc, Effect.MOBSPAWNER_FLAMES, 2);
                    Bukkit.getWorld(bloc2.getWorld().getName()).playEffect(bloc2, Effect.MOBSPAWNER_FLAMES, 2);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            colodown.remove(playerName);
                        }
                    }, 20L * 3);
                }
            }
        }

    }
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();
        Location floc = b.getLocation();
        if (b.getType() == Material.QUARTZ_ORE) {
            for (Bloc bl : teles) {
                if ((compareLocation(floc, bl.location) || compareLocation(floc, bl.location2)) && bl.isOnline(bl)) {
                    PlayerMeta pmeta = PlayerMeta.getMeta(p);
                    if (pmeta.getTeam() == PlayerMeta.getMeta(bl.name).getTeam()) {
                        p.sendMessage(ChatColor.RED + "No puedes romper el portal del equipo.");
                        e.setCancelled(true);
                        break;
                    } else {
                        e.setCancelled(true);
                        String playerName = Bloc.getName(bl);
                        Bloc.delete(bl);
                        tele1.remove(playerName);
                        tele2.remove(playerName);
                        p.sendMessage(ChatColor.GREEN + "Portales destruidos!");
                    }
                }
                e.getBlock().getDrops().clear();
            }
        }

    }

    public Location addY(Location loc, double y) {
        Location location = loc;
        location.add(0, y, 0);
        return location;
    }

    public boolean isAllowedMaterial(Block b) {
        Material m = b.getType();
        ResourceListener r = plugin.resources;
        if (m == Material.QUARTZ_ORE || m == Material.SIGN_POST
                || m == Material.WALL_SIGN || m == Material.ENCHANTMENT_TABLE
                || r.resources.containsKey(m)) {
            return false;
        }
        return true;
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
        if (!loc.getWorld().getName().equals(secondLoc.getWorld().getName())) {
            return false;
        }
        return true;
    }

    public boolean isPrepareBlock(Location l, Location l2) {
        return compareLocation(l, l2);
    }
}
