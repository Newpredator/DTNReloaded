package me.newpredator.Annihilation.listeners;

import me.newpredator.Annihilation.Annihilation;
import me.newpredator.Annihilation.Util;
import me.newpredator.Annihilation.chat.ChatUtil;
import me.newpredator.Annihilation.object.Kit;
import me.newpredator.Annihilation.object.PlayerMeta;
import me.newpredator.Annihilation.stats.StatsType;
import me.newpredator.Annihilation.stats.StatsUtil;
import net.minecraft.server.v1_9_R1.EntityPlayer;
import net.minecraft.server.v1_9_R1.PacketPlayInClientCommand;
import net.minecraft.server.v1_9_R1.PacketPlayInClientCommand.EnumClientCommand;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerListener implements Listener {

    private final Annihilation plugin;
    

    public PlayerListener(Annihilation plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("static-access")
	@EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        PlayerMeta meta = PlayerMeta.getMeta(player);
        if (plugin.getJoiningPlayers().containsKey(player)) {
            plugin.getJoiningPlayers().remove(player);
        }
        if (meta.isAlive()) {
            if (plugin.kitsToGive.containsKey(e.getPlayer().getName())) {
                meta.setKit(plugin.kitsToGive.get(e.getPlayer().getName()));
                plugin.kitsToGive.remove(e.getPlayer().getName());
            }
            e.setRespawnLocation(meta.getTeam().getRandomSpawn());
            meta.getKit().give(player, meta.getTeam());

            return;
        }
        e.setRespawnLocation(plugin.getMapManager().getAnnihilationSpawnPoint());
			ItemStack selector = new ItemStack(Material.FEATHER);
			ItemMeta itemMeta = selector.getItemMeta();
			itemMeta.setDisplayName("§aSelecciona Clase §7(Click Derecho)");
			selector.setItemMeta(itemMeta);

			player.getInventory().setItem(0, selector);

    }
    
	@EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
		final Player p = e.getEntity();
		

		if (plugin.getPhase() > 0) {
			PlayerMeta meta = PlayerMeta.getMeta(p);
			
			if (!meta.getTeam().getNexus().isAlive()) {
				meta.setAlive(false);
			}

		}

		StatsUtil.addStat(StatsType.DTNDEATHS, p.getUniqueId().toString(), 1);

		if (p.getKiller() != null) {
			Player killer = p.getKiller();
			StatsUtil.addStat(StatsType.DTNKILLS, killer.getUniqueId().toString(), 1);
			e.setDeathMessage(ChatUtil.formatDeathMessage(p, p.getKiller(), e.getDeathMessage()));
			e.setDroppedExp(p.getTotalExperience());


            PlayerMeta killMeta = PlayerMeta.getMeta(killer);
            if (killMeta.getKit() == Kit.FRENETICO) {
                killMeta.addHeart(p);
			}
		} else {
			e.setDroppedExp(p.getTotalExperience());
		e.setDeathMessage(ChatUtil.formatDeathMessage(p, e.getDeathMessage()));
		}

		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			public void run() {
				PacketPlayInClientCommand in = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);
				EntityPlayer cPlayer = ((CraftPlayer) p).getHandle();
				cPlayer.playerConnection.a(in);
			}
        }, 1l);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                Util.giveEffect(p);
            }
        }, 10L);
    }
    


    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent e) {
        final Player player = e.getPlayer();
        PlayerMeta meta = PlayerMeta.getMeta(player);
        player.teleport(meta.getTeam().getRandomSpawn());
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                Util.showClassSelector(player);
            }
        }, 20L * 1
        );
        if (!plugin.getPortalPlayers().containsKey(player)) {
            plugin.getPortalPlayers().put(player, player.getName());
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.getEntity().getWorld().getName().equals("lobby")) {
            event.setCancelled(true);
            event.setFoodLevel(20);
        }
    }
    @EventHandler
    public void onDeathKiller(PlayerDeathEvent e) {
    	Player p = e.getEntity().getPlayer();
    	Player k = p.getKiller();
    
    
    	if(k !=null) {
        if(p.hasPermission("vip.guardian")) {
      StatsUtil.addStat(StatsType.GEMAS, k.getUniqueId().toString(), 6);
                            k.sendMessage("§a+6 Gemas");
            }else{
            	StatsUtil.addStat(StatsType.GEMAS, k.getUniqueId().toString(), 3);
                            k.sendMessage("§a+3 Gemas");
        }
    	}
    }
}
