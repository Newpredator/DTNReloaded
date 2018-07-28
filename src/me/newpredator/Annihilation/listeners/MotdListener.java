package me.newpredator.Annihilation.listeners;

import me.newpredator.Annihilation.Annihilation;
import me.newpredator.Annihilation.manager.MapManager;
import me.newpredator.Annihilation.manager.PhaseManager;
import me.newpredator.Annihilation.manager.VotingManager;
import me.newpredator.Annihilation.maps.GameMap;
import me.newpredator.Annihilation.object.GameTeam;
import me.newpredator.Annihilation.object.PlayerMeta;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class MotdListener implements Listener {

    private Annihilation plugin;
    @SuppressWarnings("unused")
	private VotingManager voting;
    @SuppressWarnings("unused")
	private MapManager maps;
    @SuppressWarnings("unused")
	private GameMap map;
    @SuppressWarnings("unused")
	private String winner;

    public MotdListener(Annihilation pl) {
        plugin = pl;
    }

    @EventHandler
    public void onMOTDPing(ServerListPingEvent e) {
        if (plugin.motd) {
            String motd = plugin.getConfig().getString("motd");
            String motdlobby = plugin.getConfig().getString("motd-lobby");
            String motdstart = plugin.getConfig().getString("motd-start");
            try {
                motd = motd.replace("%PHASE%",
                        String.valueOf(plugin.getPhase()));
                motd = motd.replace("%TIME%",
                        PhaseManager.timeString(plugin.getPhaseManager().getTime()));
                motd = motd.replace("%PLAYERCOUNT%",
                        String.valueOf(Bukkit.getOnlinePlayers().size()));
                motd = motd.replace("%MAXPLAYERS%",
                        String.valueOf(Bukkit.getMaxPlayers()));
                motd = motd.replace("%GREENNEXUS%",
                        String.valueOf(getNexus(GameTeam.GREEN)));
                motd = motd.replace("%GREENCOUNT%",
                        String.valueOf(getPlayers(GameTeam.GREEN)));
                motd = motd.replace("%REDNEXUS%",
                        String.valueOf(getNexus(GameTeam.RED)));
                motd = motd.replace("%REDCOUNT%",
                        String.valueOf(getPlayers(GameTeam.GREEN)));
                motd = motd.replace("%BLUENEXUS%",
                        String.valueOf(getNexus(GameTeam.BLUE)));
                motd = motd.replace("%BLUECOUNT%",
                        String.valueOf(getPlayers(GameTeam.GREEN)));
                motd = motd.replace("%YELLOWNEXUS%",
                        String.valueOf(getNexus(GameTeam.YELLOW)));
                motd = motd.replace("%YELLOWCOUNT%",
                        String.valueOf(getPlayers(GameTeam.GREEN)));
                if (plugin.getPhase() == 0) {
                    e.setMotd(ChatColor.translateAlternateColorCodes('&', motdlobby));
                    return;
                } else if (plugin.getPhase() < plugin.lastJoinPhase + 1 && plugin.getPhase() != 0) {
                    motdstart = motdstart.replace("%PHASE%", String.valueOf(plugin.getPhase()));
                    e.setMotd(ChatColor.translateAlternateColorCodes('&', motdstart));
                    return;
                }
                e.setMotd(ChatColor.translateAlternateColorCodes('&', motd));
            } catch (Exception ex) {
            }
        }
    }

    private int getNexus(GameTeam t) {
        int health = 0;
        if (t.getNexus() != null) {
            health = t.getNexus().getHealth();
        }
        return health;
    }

    private int getPlayers(GameTeam t) {
        int size = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerMeta meta = PlayerMeta.getMeta(p);
            if (meta.getTeam() == t) {
                size++;
            }
        }
        return size;
    }
}
