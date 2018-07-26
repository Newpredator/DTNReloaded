package me.newpredator.Annihilation.listeners;

import java.util.HashMap;
import java.util.Map;

import me.newpredator.Annihilation.Annihilation;
import me.newpredator.Annihilation.object.Kit;
import me.newpredator.Annihilation.object.PlayerMeta;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ClassAbilityListenerIIIII implements Listener {
      @SuppressWarnings("unused")
	private final Annihilation plugin;
    

    public ClassAbilityListenerIIIII(Annihilation plugin) {
        this.plugin = plugin;
    }
    
   private Map<String, Long> playerDelays = new HashMap<String, Long>();
    private int castDelay = 20; // 120 Es El Tiempo De Delay
 
	@EventHandler(priority = EventPriority.LOW)
    public void onClick(PlayerInteractEvent event) {
        
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getItem() != null) {
                Player p = event.getPlayer();
                PlayerMeta meta = PlayerMeta.getMeta(p);
                Kit kit = meta.getKit();
                
                    if (event.getItem().getType() == Material.SUGAR) {
                      if(kit == Kit.RAPIDO){
                   
                    boolean castLightning = true;
                    if (this.playerDelays.containsKey(event.getPlayer().getName())) {
                        long timeRemaining = System.currentTimeMillis() - this.playerDelays.get(event.getPlayer().getName());
                        if (timeRemaining >= 1000 * this.castDelay) {
                            this.playerDelays.remove(event.getPlayer().getName());
                        } else {
                            // You can optionally send them a message. Though, you must make sure to set castLightning to false.
                          int secondsRemaining = this.castDelay - (int) (timeRemaining / 1000);
                            event.getPlayer().sendMessage("§c§oPara Usar Esta Hablidad Tienes Que Esperar " + secondsRemaining + "" + (secondsRemaining != 1 ? "s" : "") + "!");
                            castLightning = false;
                        }
                    }
                     if (castLightning) {
                        Location targetBlock = event.getPlayer().getLocation();
                        if (targetBlock != null) {
                            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 2),true);
                            this.playerDelays.put(event.getPlayer().getName(), System.currentTimeMillis());
                        }
                    }
                }
            }
        }
        }
    }
 
    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if (this.playerDelays.containsKey(event.getPlayer().getName()))
            this.playerDelays.remove(event.getPlayer().getName());
    }
 
        }