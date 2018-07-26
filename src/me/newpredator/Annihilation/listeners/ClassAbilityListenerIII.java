package me.newpredator.Annihilation.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import me.newpredator.Annihilation.object.Kit;
import me.newpredator.Annihilation.object.PlayerMeta;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ClassAbilityListenerIII implements Listener {

    
    
   private Map<String, Long> playerDelays = new HashMap<String, Long>();
   private Map<String, Long> playerDelays1 = new HashMap<String, Long>();
    private int castDelay = 120; 
    private int castDelay1 = 360; 
 
	@EventHandler(priority = EventPriority.LOW)
    public void onClick(PlayerInteractEvent e) {
        
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getItem() != null) {
                Player p = e.getPlayer();
                PlayerMeta meta = PlayerMeta.getMeta(p);
                Kit kit = meta.getKit();
                
                    if (e.getItem().getType() == Material.GOLD_AXE) {
                      if(kit == Kit.THOR) {
                   
                    boolean castLightning = true;
                    if (this.playerDelays.containsKey(p.getName())) {
                        long timeRemaining = System.currentTimeMillis() - playerDelays.get(p.getName());
                        if (timeRemaining >= 1000 * castDelay) {
                            this.playerDelays.remove(p.getName());
                        } else {
                            // You can optionally send them a message. Though, you must make sure to set castLightning to false.
                          int secondsRemaining = this.castDelay - (int) (timeRemaining / 1000);
                            e.getPlayer().sendMessage("§c§oPara Usar Esta Hablidad Tienes Que Esperar " + secondsRemaining + "" + (secondsRemaining != 1 ? "s" : "") + "!");
                            castLightning = false;
                        }
                    }
                     if (castLightning) {
                    	 Location targetblock = p.getTargetBlock((Set<Material>) null, 100).getLocation();
                        if (targetblock != null) {
                          
                            e.getPlayer().getWorld().strikeLightning(targetblock);
                            playerDelays.put(p.getName(), System.currentTimeMillis());
                        }
                    }
                }
            }
        }
        }
    }
 
    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if (playerDelays.containsKey(event.getPlayer().getName()))
            playerDelays.remove(event.getPlayer().getName());
    }
    
    @EventHandler
    public void onInteract(PlayerInteractEvent e) 
    {
    	Player p = e.getPlayer();
    	Material m = p.getInventory().getItemInMainHand().getType();
    	if(m == Material.CHEST && PlayerMeta.getMeta(p).getKit().equals(Kit.CONSTRUCTOR)) {
		if(this.playerDelays1.containsKey(p.getName())){
		long timeRemaining = System.currentTimeMillis() - playerDelays1.get(p.getName());
        if (timeRemaining >= 1000 * castDelay1) {
            playerDelays1.remove(p.getName());
        } else {
            // You can optionally send them a message. Though, you must make sure to set castLightning to false.
          int secondsRemaining = castDelay1 - (int) (timeRemaining / 1000);
            e.getPlayer().sendMessage("§c§oPara Usar Esta Hablidad Tienes Que Esperar " + secondsRemaining + "" + (secondsRemaining != 1 ? "s" : "") + "!");
        }
		}
        
    	if(!playerDelays1.containsKey(p.getName())) {
    		
    		
    		
      Inventory i = Bukkit.createInventory(null, 9, "Chest");
      i.addItem(new ItemStack[] { new ItemStack(Material.OBSIDIAN, 1) });
      i.addItem(new ItemStack[] { new ItemStack(Material.WOOD, 
        (int)(30.0D + Math.random() * 20.0D)) });
      i.addItem(new ItemStack[] { new ItemStack(Material.DIRT, 
        (int)(40.0D + Math.random() * 20.0D)) });
      i.addItem(new ItemStack[] { new ItemStack(Material.STONE, (int)(15.0D + 
        Math.random() * 15.0D)) });
      i.addItem(new ItemStack[] { new ItemStack(Material.WOOL, 
        (int)(10.0D + Math.random() * 10.0D)) });
      i.addItem(new ItemStack[] { new ItemStack(Material.BRICK, (int)(20.0D + 
        Math.random() * 10.0D)) });
      p.openInventory(i);
      playerDelays1.put(p.getName(), System.currentTimeMillis());
    } else {
    	
    }
		
 
        }
    }
    
    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
    	if(e.getItemInHand().getType() == Material.CHEST && e.getItemInHand().getItemMeta().getDisplayName().contains("Materiales")) {
    		e.setCancelled(true);
    	}
    }
}