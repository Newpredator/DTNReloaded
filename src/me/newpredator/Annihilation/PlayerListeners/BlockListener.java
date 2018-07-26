package me.newpredator.Annihilation.PlayerListeners;

import java.util.Random;
import me.newpredator.Annihilation.Annihilation;
import me.newpredator.Annihilation.Util;
import me.newpredator.Annihilation.api.NexusDamageEvent;
import me.newpredator.Annihilation.api.NexusDestroyEvent;
import me.newpredator.Annihilation.chat.ChatUtil;
import me.newpredator.Annihilation.manager.PhaseManager;
import me.newpredator.Annihilation.object.BlockObject;
import me.newpredator.Annihilation.object.GameTeam;
import me.newpredator.Annihilation.object.PlayerMeta;
import me.newpredator.Annihilation.stats.StatsType;
import me.newpredator.Annihilation.stats.StatsUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;

import static me.newpredator.Annihilation.manager.SoundManager.playSoundForPlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;

public class BlockListener implements Listener {

    private Annihilation plugin;

    public BlockListener(Annihilation pl) {
        plugin = pl;
    }

    @EventHandler
    public void onPM(BlockPistonExtendEvent e) {
        for (Block b : e.getBlocks()) {
            if (Util.tooClose(b.getLocation(), plugin)) {
                e.setCancelled(true);
                b.setType(Material.AIR);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (plugin.getPhase() > 0) {
            if (Util.isEmptyColumn(e.getBlock().getLocation())) {
                e.setCancelled(true);
            }
            if (Util.tooClose(e.getBlock().getLocation(), plugin)
                    && !e.getPlayer().hasPermission("hero.admin") && PhaseManager.getPhase() == 0) {
                e.getPlayer().sendMessage(
                        ChatColor.RED
                        + "No puedes construir cerca del spawn!");
                e.setCancelled(true);
            }
            }
        }
    

    @EventHandler
    public void onSignPlace(SignChangeEvent e) {
        if (e.getPlayer().hasPermission("hero.admin")) {
            if (e.getLine(0).toLowerCase().contains("[Shop]".toLowerCase())) {
                e.setLine(0, ChatColor.DARK_PURPLE + "[Shop]");
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockBreakEvent e) {
        Block b = e.getBlock();
        if (Util.hasSignAttached(b)) {
            if (Util.isShopSignAttached(b)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBreakIII(BlockBreakEvent e) {
        if (plugin.getPhase() > 0) {
            for (GameTeam t : GameTeam.teams()) {
                if (t.getNexus().getLocation()
                        .equals(e.getBlock().getLocation())) {
                    e.setCancelled(true);
                    if (t.getNexus().isAlive()) {
                        breakNexus(t, e.getPlayer());
                    }
                    return;
                }
            }

            if (Util.tooClose(e.getBlock().getLocation(), plugin)
                    && !e.getPlayer().hasPermission("hero.admin")
                    && e.getBlock().getType() != Material.ENDER_STONE &&
                    e.getBlock().getType() != Material.MELON_BLOCK) {
                e.getPlayer().sendMessage(
                        ChatColor.RED
                        + "No puedes contruir cerca del spawn!");
                e.setCancelled(true);
            }
        } else {
            if (!e.getPlayer().hasPermission("hero.admin")) {
                e.setCancelled(true);
            }
        }
    }

	@SuppressWarnings("unlikely-arg-type")
	@EventHandler
    public void onBreakII(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Block b = e.getBlock();
        PlayerMeta meta = PlayerMeta.getMeta(player);
        BlockObject bbo = null;
        for (BlockObject bo : plugin.crafting.values()) {
            if (BlockObject.isBlock(b, bo) && Util.getTeam(meta.getTeam())) {
                e.setCancelled(true);
                bbo = bo;

            }
        }
        if (bbo != null) {
            plugin.crafting.remove(bbo);
        }
    }

    
        @SuppressWarnings({ "deprecation", "static-access" })
		public void breakNexus(final GameTeam victim, Player breaker) {
        final GameTeam attacker = PlayerMeta.getMeta(breaker).getTeam();
    
		if (victim == attacker)
			breaker.sendMessage(ChatColor.DARK_PURPLE + "No puedes romper tu propio nexus");
		else if (plugin.getPhase() == 1)
			breaker.sendMessage(ChatColor.DARK_PURPLE + "El nexus es invencible en la fase 1");
		else {
			plugin.getScoreboardHandler().sb.getTeam(victim.name() + "SB").setPrefix(ChatColor.RESET.toString());
			victim.getNexus().damage(plugin.getPhase() == 5 ? 2 : 1);

			StatsUtil.addStat(StatsType.DTNBREAKS, breaker.getUniqueId().toString(), plugin.getPhase() == 5 ? 2 : 1);

			String msg = ChatUtil.nexusBreakMessage(breaker, attacker, victim);
			for (Player p : attacker.getPlayers())
                                p.sendMessage(msg);
                        
			plugin.getScoreboardHandler().score.get(victim.name()).setScore(victim.getNexus().getHealth());
			Bukkit.getServer().getPluginManager().callEvent(new NexusDamageEvent(breaker, victim, victim.getNexus().getHealth()));
                                                

			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				public void run() {
					plugin.getScoreboardHandler().sb.getTeam(victim.name() + "SB").setPrefix(victim.color().toString());
				}
			}, 2L);

			Random r = new Random();
			float pitch = 0.5F + r.nextFloat() * 0.5F;
			victim.getNexus().getLocation().getWorld().playSound(victim.getNexus().getLocation(), Sound.BLOCK_ANVIL_LAND, 3F, pitch);
                        for (Player p : victim.getPlayers())
                              if (PlayerMeta.getMeta(p).getTeam() == victim)
                                 playSoundForPlayer(p, Sound.ENTITY_BAT_TAKEOFF, 1, 1, 1);

			Location nexus = victim.getNexus().getLocation().clone();
			nexus.add(0.5, 0, 0.5);
			Util.ParticleEffects.sendToLocation(Util.ParticleEffects.LAVA_SPARK, nexus, 1F, 1F, 1F, 0, 20);
			Util.ParticleEffects.sendToLocation(Util.ParticleEffects.LARGE_SMOKE, nexus, 1F, 1F, 1F, 2, 20);
			Util.ParticleEffects.sendToLocation(Util.ParticleEffects.FIREWORK_SPARK, nexus, 0.1F, 0.1F, 0.1F, 2, 140);
                        Util.ParticleEffects.sendToLocation(Util.ParticleEffects.DRIP_LAVA, nexus, 2F, 2F, 2F, 1, 20);
                        Util.ParticleEffects.sendToLocation(Util.ParticleEffects.ANGRY_VILLAGER, nexus, 3F, 3F, 3F, 2, 25);
                     
                        for (Player xp : attacker.getPlayers()){
                            if(xp.hasPermission("vip.guardian")){
	StatsUtil.addStat(StatsType.GEMAS, xp.getUniqueId().toString(), 2);
		xp.sendMessage("§a+2 Gemas");
                            }else{
                            	StatsUtil.addStat(StatsType.GEMAS, xp.getUniqueId().toString(), 1);
		xp.sendMessage("§a+1 Gema");
                            }
                        }

			if (victim.getNexus().getHealth() == 0) {
				plugin.getScoreboardHandler().sb.resetScores(plugin.getScoreboardHandler().score.remove(victim.name()).getPlayer());
				Bukkit.getServer().getPluginManager().callEvent(new NexusDestroyEvent(breaker, victim));
				ChatUtil.nexusDestroyed(attacker, victim, breaker);

				plugin.checkWin();
                                

				for (Player p : victim.getPlayers()) {
					StatsUtil.addStat(StatsType.DTNLOSSES, p.getUniqueId().toString(), 1);
				}
                                
                               
				for (Player player : Bukkit.getOnlinePlayers()) {
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_DEATH, 20F, pitch);
				}

				for (Player player : Bukkit.getOnlinePlayers()) {
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 3F, pitch);
				}
                                
				Util.ParticleEffects.sendToLocation(Util.ParticleEffects.HUGE_EXPLODE, nexus, 2F, 2F, 2F, 2, 15);
                                Util.ParticleEffects.sendToLocation(Util.ParticleEffects.FIREWORK_SPARK, nexus, 15F, 15F, 15F, 2, 500);
                                Util.ParticleEffects.sendToLocation(Util.ParticleEffects.DRIP_LAVA, nexus, 5F, 5F, 5F, 0, 200);
                                
                                for (Player p : victim.getPlayers()) {
                                    if(p.hasPermission("vip.guardian")){
                                    	StatsUtil.addStat(StatsType.GEMAS, p.getUniqueId().toString(), 60);
		p.sendMessage("§a+60 Gemas");
                                }else{
                                	StatsUtil.addStat(StatsType.GEMAS, p.getUniqueId().toString(), 30);
		p.sendMessage("§a+30 Gemas");
				}
                                }

				Bukkit.getScheduler().runTask(plugin, new Runnable() {
					public void run() {
						Location nexus = victim.getNexus().getLocation().clone();
						boolean found = false;
						int y = 0;

						while (!found) {
							y++;

							Block b = nexus.add(0, 1, 0).getBlock();

							if (b != null && b.getType() == Material.BEACON)
								b.setType(Material.AIR);

							if (y > 10)
								found = true;
						}
					}
				});
			}

			plugin.getSignHandler().updateSigns(victim);
		}
	}



        
        }
