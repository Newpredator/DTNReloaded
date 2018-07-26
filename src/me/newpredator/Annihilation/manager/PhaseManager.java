package me.newpredator.Annihilation.manager;

import me.newpredator.Annihilation.Annihilation;
import me.newpredator.Annihilation.api.ActionApi;
import me.newpredator.Annihilation.object.GameTeam;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitScheduler;

public class PhaseManager {
    private long time;
    private long startTime;
    private long phaseTime;
    private static int phase;
    private boolean isRunning;

    private final Annihilation plugin;

    private int taskID;

    public PhaseManager(Annihilation plugin, int start, int period) {
        this.plugin = plugin;
        startTime = start;
        phaseTime = period;
        phase = 0;
   
    }

    public void start() {
        if (!isRunning) {
            BukkitScheduler scheduler = plugin.getServer().getScheduler();
            taskID = scheduler.scheduleSyncRepeatingTask(plugin,
                    new Runnable() {
                        public void run() {
                            onSecond();
                        }
                    }, 20L, 20L);
            isRunning = true;
        }

        time = -startTime;
        
        	ActionApi.sendActionBarToAllPlayers("§aEstá empezando");


        plugin.getSignHandler().updateSigns(GameTeam.RED);
        plugin.getSignHandler().updateSigns(GameTeam.BLUE);
        plugin.getSignHandler().updateSigns(GameTeam.GREEN);
        plugin.getSignHandler().updateSigns(GameTeam.YELLOW);
        
    }

    public void stop() {
        if (isRunning) {
            isRunning = false;
            Bukkit.getServer().getScheduler().cancelTask(taskID);
        }
    }

    public void reset() {
        stop();
        time = -startTime;
        phase = 0;
    }

    public long getTime() {
        return time;
    }

    public long getRemainingPhaseTime() {
        if (phase == 5) {
            return phaseTime;
        }
        if (phase >= 1) {
            return time % phaseTime;
        }
        return -time;
    }

    public static int getPhase() {
        return phase;
    }

    public boolean isRunning() {
        return isRunning;
    }

    private void onSecond() {
        time++;

        if (getRemainingPhaseTime() == 0) {
            phase++;
            plugin.advancePhase();
            plugin.getSignHandler().updateSigns(GameTeam.RED);
            plugin.getSignHandler().updateSigns(GameTeam.BLUE);
            plugin.getSignHandler().updateSigns(GameTeam.GREEN);
            plugin.getSignHandler().updateSigns(GameTeam.YELLOW);
        }

        String text = null;

        if (phase == 0) {
            text = ChatColor.DARK_PURPLE + "Empezando" + ChatColor.DARK_GRAY + " | " + ChatColor.GRAY + "Empieza en " + ChatColor.DARK_PURPLE +
                    + -time;
        } else {
            if (phase == 5) {
            } if(phase != 5 || phase != 0) {
           text = "§a§nFase " + (phase) + "§8 - §eTiempo§e (§7" + timeString(time) + "§e)";
            }
            
        }
        	ActionApi.sendActionBarToAllPlayers(text);
        
        plugin.onSecond();
    
    }

    public static String timeString(long time) {
        long hours = time / 3600L;
        long minutes = (time - hours * 3600L) / 60L;
        long seconds = time - hours * 3600L - minutes * 60L;
        return String.format(ChatColor.LIGHT_PURPLE + "%02d" + ChatColor.GRAY + ":"
                + ChatColor.LIGHT_PURPLE + "%02d" + ChatColor.GRAY + ":"
                + ChatColor.LIGHT_PURPLE + "%02d", hours, minutes, seconds).replace("-", "");
    }
   
}