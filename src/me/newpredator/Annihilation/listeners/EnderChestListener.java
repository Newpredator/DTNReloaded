package me.newpredator.Annihilation.listeners;

import java.util.HashMap;
import me.newpredator.Annihilation.Annihilation;
import me.newpredator.Annihilation.object.GameTeam;
import me.newpredator.Annihilation.object.PlayerMeta;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;

public class EnderChestListener
  implements Listener
{
private HashMap<GameTeam, Location> chests = new HashMap<GameTeam, Location>();
private HashMap<String, Inventory> inventories = new HashMap<String, Inventory>();

@EventHandler
public void onChestOpen(PlayerInteractEvent e)
{
	if(e.getHand() != EquipmentSlot.HAND) {return;}
  if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
    return;
  }
  Block b = e.getClickedBlock();
  if (b.getType() != Material.ENDER_CHEST) {
    return;
  }
  Player player = e.getPlayer();
  GameTeam team = PlayerMeta.getMeta(player).getTeam();
  if ((team == GameTeam.NONE)) {
    return;
  }
    openEnderChest(player);
    e.setCancelled(true);
  }

  private void openEnderChest(Player player) {
    String name = player.getName();
    if (!this.inventories.containsKey(name)) {
        Inventory inv = Bukkit.createInventory(null, 9, "Cofre de " + name);
      this.inventories.put(name, inv);
    }
    player.sendMessage(Annihilation.getInstance().getConfig().getString("prefix").replace("&", "§") + " §7Has entrado en el §3enderchest");
    player.openInventory(this.inventories.get(name));
  }

  @EventHandler
  public void onFurnaceBreak(BlockBreakEvent e) {
    if (this.chests.values().contains(e.getBlock().getLocation()))
      e.setCancelled(true);
  }
  @EventHandler
  public void onEcCraft(CraftItemEvent e)
  {
    Player player = (Player)e.getWhoClicked();
    if (e.getCurrentItem().getType().equals(Material.ENDER_CHEST))
      {
        e.setCancelled(true);
        player.sendMessage(Annihilation.getInstance().getConfig().getString("prefix").replace("&", "§") + "§cNo puedes craftear un enderchest.");
      }
    }
  }


 