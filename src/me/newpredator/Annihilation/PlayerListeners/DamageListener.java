package me.newpredator.Annihilation.PlayerListeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.newpredator.Annihilation.Annihilation;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class DamageListener implements Listener {

    private Annihilation plugin;

    public DamageListener(Annihilation pl) {
        plugin = pl;
    }
    
    @SuppressWarnings("static-access")
	@EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (e.getEntity().getWorld().getName().equals("lobby")) {
                  e.setCancelled(true);
                if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
                    e.getEntity().teleport(plugin.getMapManager().getAnnihilationSpawnPoint());
                }
            }
           /* Player player = (Player) e.getEntity();
            PlayerMeta meta = PlayerMeta.getMeta(player);
            int callDMG = (int) ((e.getDamage() / 20) * 100);
            if(meta.health <= callDMG){
                Bukkit.getServer().getPluginManager().callEvent(new PlayerDeathEvent(player, dropItem(player), 0, 0, 0, 0, ""));
            }
            meta.addHealth(-callDMG);
            meta.update(false);*/
        }
    }
        public static List<ItemStack> dropItem(Player player) {
        ItemStack[] i;
        ItemStack[] a;

        a = player.getInventory().getContents();
        i = player.getInventory().getArmorContents();
        List<ItemStack> items = new ArrayList<ItemStack>();
        items.addAll(Arrays.asList(a));
        items.addAll(Arrays.asList(i));

        return items;
    }
}
