package me.newpredator.Annihilation.manager;

import java.util.Iterator;
import me.newpredator.Annihilation.Annihilation;
import me.newpredator.Annihilation.Util;
import me.newpredator.Annihilation.api.ActionApi;
import me.newpredator.Annihilation.object.GameTeam;
import me.newpredator.Annihilation.stats.StatsType;
import me.newpredator.Annihilation.stats.StatsUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class RestartHandler {
    private final Annihilation plugin;
    private long time;
    private long delay;
    private int taskID;
    private int fwID;

    public RestartHandler(Annihilation plugin, final long delay) {
        this.plugin = plugin;
        this.delay = delay;
    }

    @SuppressWarnings("static-access")
	public void start(final long gameTime, final Color c) {
        for (Iterator<Entity> iterator = plugin.getMapManager().getCurrentMap().getWorld().getEntities().iterator(); iterator.hasNext();) {
            Entity entity = iterator.next();
            if (entity.getType() == EntityType.IRON_GOLEM) {
                entity.remove();
            }
        }

        time = delay;
        final String totalTime = PhaseManager.timeString(gameTime);
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin,
                new Runnable() {
                    public void run() {
                        if (time <= 0) {
                            stop();
                            return;
                        }
                        String message = ChatColor.GRAY + "Tiempo: "
                                + ChatColor.AQUA + totalTime + ChatColor.DARK_GRAY + " «" + ChatColor.AQUA + "o" + ChatColor.DARK_GRAY + "» "
                                + ChatColor.GRAY + "Reinicio en " + ChatColor.DARK_PURPLE
                                + time;
                        	ActionApi.sendActionBarToAllPlayers(message);
                        time --;
                    }
                }, 0L, 20L);

        fwID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {
                    for (GameTeam gt : GameTeam.values()) {
                        if (gt != GameTeam.NONE) {
                            for (Location l : gt.getSpawns()) {
                                Util.spawnFirework(l, c, c);
                            }
                        }
                    }
                }
            }, 0L, 40L);
    }
private void tp() {
    for (Player p : Bukkit.getOnlinePlayers()) {
    	if(p.hasPermission("vip.guardian")){
    		StatsUtil.addStat(StatsType.GEMAS, p.getUniqueId().toString(), 30);
    			p.sendMessage("§a+30 Gema - §7§oPor quedarte hasta el final.");
    	                            }else{
    	                            	StatsUtil.addStat(StatsType.GEMAS, p.getUniqueId().toString(), 15);
    			p.sendMessage("§a+15 Gemas - §7§oPor quedarte hasta el final.");
    	                            }
    	                        
      p.sendMessage("§aConectando con lobby...");
    }
  }
    private void stop() {
        Bukkit.getScheduler().cancelTask(taskID);
        Bukkit.getScheduler().cancelTask(fwID);
tp();
        	for(Player p : Bukkit.getOnlinePlayers()) {
            	ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF("AnniAnnihilation1");
            p.sendPluginMessage(Annihilation.getInstance(), "BungeeCord", out.toByteArray());
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");

        }
    }
}
