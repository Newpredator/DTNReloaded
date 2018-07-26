package me.newpredator.Annihilation.PlayerListeners;

import java.util.ArrayList;
import java.util.List;

import me.newpredator.Annihilation.Annihilation;
import me.newpredator.Annihilation.Util;
import me.newpredator.Annihilation.object.BlockObject;
import me.newpredator.Annihilation.object.GameTeam;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class InteractListener implements Listener {

    private Annihilation plugin;
    private final List<Player> waiting;

	public InteractListener(Annihilation pl) {
        plugin = pl;
        waiting = new ArrayList<Player>();
    }

    @EventHandler
    public void onInteractI(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Action a = e.getAction();
        if (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) {
            ItemStack handItem = player.getInventory().getItemInMainHand();
            if (handItem != null) {
                if (handItem.getType() == Material.FEATHER) {
                    if (handItem.getItemMeta().hasDisplayName()) {
                        if (handItem.getItemMeta().getDisplayName().contains("§aSelecciona Clase §7(Click Derecho)")) {
                            Util.showClassSelector(e.getPlayer());
                            return;
                        }
                    }
                }
            
   
                    if (handItem != null) {
                        if (handItem.getType() == Material.FIREBALL) {
                            if (handItem.getItemMeta().hasDisplayName()) {
                                if (handItem.getItemMeta().getDisplayName().contains("§eSal §7(Click Derecho)")) {
                    	            ByteArrayDataOutput out = ByteStreams.newDataOutput();
                    	            out.writeUTF("Connect");
                    	            out.writeUTF("AnniAnnihilation1");
                    	            player.sendPluginMessage(Annihilation.getInstance(), "BungeeCord", out.toByteArray());
                                    return;
                                }
                            }
                        }
                    
                if (handItem.getType() == Material.COMPASS) {
                    boolean setCompass = false;
                    boolean setToNext = false;
                    while (!setCompass) {
                        for (GameTeam team : GameTeam.teams()) {
                            if (setToNext) {
                                ItemMeta meta = handItem.getItemMeta();
                                meta.setDisplayName(team.color()
                                        + "Apuntando " + team.toString()
                                        + " Nexus");
                                handItem.setItemMeta(meta);
                                player.setCompassTarget(team.getNexus()
                                        .getLocation());
                                setCompass = true;
                                break;
                            }
                            if (handItem.getItemMeta().getDisplayName()
                                    .contains(team.toString())) {
                                setToNext = true;
                            }
                        }
                    }
                }
            }
        }

        if (e.getClickedBlock() != null) {
            Material clickedType = e.getClickedBlock().getType();
            if (clickedType == Material.SIGN_POST
                    || clickedType == Material.WALL_SIGN) {
                Sign s = (Sign) e.getClickedBlock().getState();
                if (s.getLine(1).contains(" ")) {
                    String teamName = ChatColor.stripColor(s.getLine(0));
                    GameTeam team = GameTeam.valueOf(teamName.toUpperCase());
                    if (team != null) {
                            plugin.joinTeam(e.getPlayer(), teamName);
                    } else {
                        player.sendMessage(ChatColor.RED + "No puedes entrar en este equipo por que esta destruido.");
                    }
                }
            }
        }
            }
        }
    

    @EventHandler
    public void onInteractII(PlayerInteractEvent e) {
        HumanEntity p = e.getPlayer();
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        Player player = (Player) p;
        Block b = e.getClickedBlock();
        if (e.isCancelled()) {
            return;
        }
        if((b.getType() == Material.WORKBENCH || b.getType() == Material.ANVIL) && b.getType().isBlock()) {
            if (!plugin.crafting.containsKey(p)) {
                BlockObject bo = new BlockObject(b);
                plugin.crafting.put(player, bo);
            }
        }
    }

    @EventHandler
    public void onInteractIII(PlayerInteractEvent e) {
        final Player player = e.getPlayer();
        Action act = e.getAction();
        if (act == Action.LEFT_CLICK_AIR) {
            if (e.getClickedBlock() != null) {
                return;
            }
            if (waiting.contains(player)) {
                e.setCancelled(true);
                return;
            }
            waiting.add(player);
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    if (waiting.contains(player)) {
                        waiting.remove(player);
                    }
                }
            }, 30L * 1);
        }
    }

    @EventHandler
    public void onSwing(PlayerInteractEntityEvent event) {
        final Player player = event.getPlayer();
        for (Entity e : player.getNearbyEntities(4, 4, 4)) {
            if (e instanceof Player || e instanceof LivingEntity) {
                if (Util.getTargetEntity(player, 4) != event.getRightClicked() || waiting.contains(player)) {
                    event.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onVote(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Action a = e.getAction();
        if (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) {
            ItemStack handItem = player.getInventory().getItemInMainHand();
            if (handItem != null) {
                if (handItem.getType() == Material.BOOK) {
                    if (handItem.getItemMeta().hasDisplayName()) {
                        if (handItem.getItemMeta().getDisplayName().contains("§cVota Mapa §7(Click Derecho)")) {
                            Util.showMapSelector(player);
                            return;
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void onShop(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Action a = e.getAction();
        if (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) {
            ItemStack handItem = player.getInventory().getItemInMainHand();
            if (handItem != null) {
                if (handItem.getType() == Material.GOLD_INGOT) {
                    if (handItem.getItemMeta().hasDisplayName()) {
                        if (handItem.getItemMeta().getDisplayName().contains("§dCompra kits §7(Click Derecho)")) {
                            Util.showShopSelector(player);
                            return;
                        }
                    }
                }
            }
        }
    }
}
    

