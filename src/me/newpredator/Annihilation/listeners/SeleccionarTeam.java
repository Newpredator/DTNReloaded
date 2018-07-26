package me.newpredator.Annihilation.listeners;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;
import org.bukkit.plugin.Plugin;
 
public class SeleccionarTeam implements Listener {
 
        private Inventory inv;
        private ItemStack ROJO, VERDE, AMARILLO, AZUL, RANDOM;
       
        public SeleccionarTeam(Plugin p) {
                inv = Bukkit.getServer().createInventory(null, 9, ChatColor.BOLD + "§lSelecciona Equipo");
               
//                ESPECTADOR = createItem(DyeColor.MAGENTA, ChatColor.DARK_PURPLE + "Modo Espectador");
                RANDOM = createItem(DyeColor.WHITE, ChatColor.WHITE + "Equipo Aleatorio");
                
                ROJO = createItem(DyeColor.RED, ChatColor.RED + "Equipo Rojo");
                
                VERDE = createItem(DyeColor.GREEN, ChatColor.GREEN + "Equipo Verde");
                
                AMARILLO = createItem(DyeColor.YELLOW, ChatColor.YELLOW + "Equipo Amarillo");
                
                AZUL = createItem(DyeColor.BLUE, ChatColor.BLUE + "Equipo Azul");
               
//                inv.setItem(8, ESPECTADOR);
                inv.setItem(4, RANDOM);
                inv.setItem(0, ROJO);
                inv.setItem(1, VERDE);
                inv.setItem(2, AMARILLO);
                inv.setItem(3, AZUL);
                
                Bukkit.getServer().getPluginManager().registerEvents(this, p);
        }
       
        private ItemStack createItem(DyeColor dc, String name) {
                ItemStack i = new Wool(dc).toItemStack(1);
                ItemMeta im = i.getItemMeta();
                im.setDisplayName(name);
                im.setLore(Arrays.asList("Elige el " + name.toLowerCase()));
                i.setItemMeta(im);
                return i;
        }
       
        public void show(Player p) {
                p.openInventory(inv);
        }
       
        @EventHandler
        public void onInventoryClick(InventoryClickEvent event) {
                 Player player = (Player)event.getWhoClicked();
                if (!event.getInventory().getName().equalsIgnoreCase(inv.getName())) 
                     return;
                if (event.getCurrentItem().getItemMeta() == null) 
                    return;
                //Equipo Rojo
                if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Rojo")) {
                        event.setCancelled(true);
                        player.performCommand("red");
                        event.getWhoClicked().closeInventory();
                }
                //Equipo Verde
                if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Verde")) {
                        event.setCancelled(true);
                        player.performCommand("green");
                        event.getWhoClicked().closeInventory();
                }
                //Equipo Amarillo
                if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Amarillo")) {
                        event.setCancelled(true);
                  player.performCommand("yellow");
                        event.getWhoClicked().closeInventory();
                }
                //Equipo Azul
                if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Azul")) {
                        event.setCancelled(true);       
                  player.performCommand("blue");
                        event.getWhoClicked().closeInventory();
                }
                
              // Equipo Random
                if(event.getCurrentItem().getItemMeta().getDisplayName().contains("Aleatorio")){
                      event.setCancelled(true);
                   
                  
                Random random = new Random();
                    List<String> randomArgs = Arrays.asList("red", "blue", "yellow", "green");
                   int index = random.nextInt(randomArgs.size());
                   String command = randomArgs.get(index);
                    player.performCommand(command);   
                    event.getWhoClicked().closeInventory();
    }
        }
}

//    if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Espectador")) {
//                        event.setCancelled(true);
//                      player.sendMessage(ChatColor.YELLOW + "Bienvenido al Modo Espectador!");
//                      player.getInventory().removeItem();
//                      player.getInventory().addItem(ESPECTADOR());
//                      player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 1));
//                      player.setFlying(true);
//                      player.setFlySpeed(2);
//                               event.getWhoClicked().closeInventory();
//                         }
//
//                        event.getWhoClicked().closeInventory();
//                        
//                }
//   public static ItemStack ESPECTADOR() {
//            
//            ItemStack item = new ItemStack(Material.ANVIL, 1);
//            ItemMeta im = item.getItemMeta();
//            
//            im.setDisplayName(ChatColor.RED + "Salir Modo Espectador");
//            List<String> lore = new ArrayList<String>();  
//            im.setLore(lore);
//            item.setItemMeta(im);
//        return item;
//
//    }
//   
//   
//            //espectador modo
//           @EventHandler
//public void onPlayerInteract(PlayerInteractEvent event) {
//    if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
//       Player player = event.getPlayer();
//      
//        if(player.getItemInHand() != null) {
//            ItemStack iteminhand = player.getItemInHand();
//            if(iteminhand.getItemMeta().hasDisplayName() && iteminhand.getItemMeta().hasLore()) {
//                if(iteminhand.equals(ESPECTADOR())) {
//                  player.updateInventory();
//                  player.setFlying(false);
//                  player.setFlySpeed(0);
//                  player.removePotionEffect(PotionEffectType.SLOW);
//                World w = Bukkit.getWorld("lobby");
//                 if (w == null) {   
//
//                     return;
//                 } else {
//
//                 }                           
//                     Location loc = new Location(player.getServer().getWorld("lobby"), 
//                            player.getServer().getWorld("lobby").getSpawnLocation().getX(), 
//                            player.getServer().getWorld("lobby").getSpawnLocation().getY(), 
//                            player.getServer().getWorld("lobby").getSpawnLocation().getZ());
//                    player.teleport(loc);    
//                }
//            }
//        }
//    }
//           }
//           }