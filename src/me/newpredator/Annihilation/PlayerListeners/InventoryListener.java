package me.newpredator.Annihilation.PlayerListeners;

import me.newpredator.Annihilation.Annihilation;
import me.newpredator.Annihilation.object.Kit;
import me.newpredator.Annihilation.object.PlayerMeta;
import me.newpredator.Annihilation.stats.StatsType;
import me.newpredator.Annihilation.stats.StatsUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class InventoryListener implements Listener {

    private Annihilation plugin;
    public InventoryListener(Annihilation pl) {
        plugin = pl;
    }

    @EventHandler
    public void onInvClose(InventoryCloseEvent event) {
        HumanEntity player = event.getPlayer();
        Player p = (Player) player;
        if (plugin.crafting.containsKey(p)) {
            plugin.crafting.remove(p);
        }
    }

	@EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        Player player = (Player) e.getWhoClicked();
        PlayerMeta meta = PlayerMeta.getMeta(player);
        Kit kit = meta.getKit();
        try{
        if (inv.getTitle().startsWith("§5" + player.getName()+ " - " + kit)) {
            if (e.getCurrentItem().getType() == Material.AIR || e.getCurrentItem().getType() == null) {
                return;
            }
            player.closeInventory();
            e.setCancelled(true);
            String name = e.getCurrentItem().getItemMeta().getDisplayName();

            if (!Kit.valueOf(ChatColor.stripColor(name).toUpperCase())
                    .isOwnedBy(player)) {
                player.sendMessage("§oNo tienes acceso a este Kit, Compra Rango VIP o puedes Desbloquearlo usando Gemas, Tienda: royalhero.esy.es/Tienda");
                return;
            }

            meta.setKit(Kit.getKit(ChatColor.stripColor(name)));
           
            player.sendMessage("§aSe ha equipado el KIT §l>> " + "§c§n" +ChatColor.stripColor(name) + "§a a tu partida!");
            if (plugin.getPortalPlayers().containsKey(player)) {
                plugin.getPortalPlayers().remove(player);
                player.setHealth(0D);
                player.setAllowFlight(false);
            }
        }
       } catch(Exception ex){}
    }
	@EventHandler
	public void onInventoryClick1(InventoryClickEvent e) {
		 Inventory inv = e.getInventory();
	        Player player = (Player) e.getWhoClicked();
	        if (inv.getTitle().contains("Vota Mapa")) {
	        		if(e.getSlot() == 0) {
	        			player.performCommand("vote 1");
	        			e.setCancelled(true);
	        			player.closeInventory();
	        			return;
	        		}
	        		if(e.getSlot() == 1) {
	        			player.performCommand("vote 2");
	        			e.setCancelled(true);
	        			player.closeInventory();
	        			return;
	        		}
	        		if(e.getSlot() == 2) {
	        			player.performCommand("vote 3");
	        			e.setCancelled(true);
	        			player.closeInventory();
	        			return;
	        		}
	        		if(e.getSlot() == 3) {
	        			player.performCommand("vote 4");
	        			e.setCancelled(true);
	        			player.closeInventory();
	        			return;
	        		}
	        	
	        	
	        
	        }
	
	}
	@EventHandler
    public void onShopInventoryClick(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        Player player = (Player) e.getWhoClicked();
        String name = "";;
        if (inv.getTitle().startsWith("§2Compra Kits")) {
            e.setCancelled(true);
            if (e.getCurrentItem().getType() == Material.AIR || e.getCurrentItem().getType() == null) {
                return;
            }
            player.closeInventory();
            	name = e.getCurrentItem().getItemMeta().getDisplayName();
            	e.setCancelled(true);
            

            if (!Kit.valueOf(ChatColor.stripColor(name).toUpperCase())
                    .isOwnedBy(player)) {
            	if(!(StatsUtil.getStat(StatsType.GEMAS, player.getUniqueId().toString()) >= Kit.getKit(ChatColor.stripColor(name))
            			.getPrice())) {
                player.sendMessage("§cNo tienes gemas suficientes. Compra más en la Tienda: royalhero.esy.es/Tienda");
            	} else {
            		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " add anni.kit." + Kit.getKit(ChatColor.stripColor(name).toLowerCase()));
            		StatsUtil.setStat(StatsType.GEMAS, player.getUniqueId().toString(), StatsUtil.getStat(StatsType.GEMAS, player.getUniqueId().toString()) - Kit.getKit(ChatColor.stripColor(name)).getPrice());
            		player.sendMessage("§2Has comprado el Kit §a" + name);
            	
            	
            }
            } else {
            	player.sendMessage("§cYa tienes ese kit.");
           
            }

        }
}
	}


