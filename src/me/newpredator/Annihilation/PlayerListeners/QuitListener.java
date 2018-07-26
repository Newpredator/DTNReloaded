package me.newpredator.Annihilation.PlayerListeners;

import me.newpredator.Annihilation.Annihilation;
import me.newpredator.Annihilation.Util;
import me.newpredator.Annihilation.manager.PlayerSerializer;
import me.newpredator.Annihilation.object.GameTeam;
import me.newpredator.Annihilation.object.PlayerMeta;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class QuitListener implements Listener {

    private Annihilation plugin;

    public QuitListener(Annihilation pl) {
        plugin = pl;
    }

	@SuppressWarnings("unlikely-arg-type")
	@EventHandler
    public void onQuit(PlayerQuitEvent e) {
        final Player player = e.getPlayer();
        if (player == null) {
            return;
        }
        final PlayerMeta meta = PlayerMeta.getMeta(player);
        final String playerName = player.getName();
        ItemStack[] items = player.getInventory().getContents();
        Double health = player.getHealth();
        ItemStack[] armor = player.getInventory().getArmorContents();
        Float satur = player.getSaturation();
        int level = player.getLevel();
        @SuppressWarnings("deprecation")
		int gm = player.getGameMode().getValue();
        int food = player.getFoodLevel();
        float exauth = player.getExhaustion();
        float exp = player.getExp();
        boolean bol = true;
        final GameTeam team = meta.getTeam();
        e.setQuitMessage(null);
        if (plugin.getPhase() == 0 && !GameTeam.isNull(team)) {
            meta.setTeam(GameTeam.NONE);
            return;
        }
        if (meta.getTeam() == GameTeam.NONE) {
            PlayerSerializer.delete(playerName);
            return;
        }
        if (plugin.getJoiningPlayers().containsKey(player)) {
            plugin.getJoiningPlayers().remove(player);
            return;
        }
        if (plugin.getNpcPlayers().containsKey(playerName)) {
            return;
        }
        if (player.getWorld().getName().equalsIgnoreCase("lobby")) {
            return;
        }
        if(player.getLocation().getY() <= 0 || isFallingToVoid(player)){
            PlayerSerializer.removeItems(playerName);
            return;
        }
        String w = player.getWorld().getName();
        savePlayer(playerName, items, armor, health, satur, level, gm, food, exauth, exp, team, bol, w);
        World world = Bukkit.getWorld(player.getWorld().getName());
        final Zombie z = world.spawn(player.getLocation(), Zombie.class);
        if (!plugin.getNpcPlayers().containsKey(playerName)) {
            plugin.getNpcPlayers().put(playerName, playerName);
        }
        z.setBaby(false);
        z.setHealth(player.getHealth());
        z.setCanPickupItems(false);
        z.setCustomName(meta.getTeam().getChatColor(team) + playerName);
        ItemStack hand = player.getInventory().getItemInMainHand();
        ItemStack[] armors = player.getInventory().getArmorContents();
        z.getEquipment().setItemInMainHand(hand);
        z.getEquipment().setArmorContents(armors);
        if (!plugin.getZombies().containsValue(z.getCustomName())) {
            plugin.getZombies().put(z.getCustomName(), z);
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    if (plugin.getNpcPlayers().containsKey(playerName)) {
                        plugin.getNpcPlayers().remove(playerName);
                    }
                    if (plugin.getZombies().containsKey(z.getCustomName())) {
                        plugin.getZombies().remove(z);
                        z.remove();
                    } else {
                        if (z.getCustomName() == null) {
                            return;
                        }
                        String zName = Util.replaceTeamColor(z.getCustomName());
                        PlayerSerializer.removeItems(zName);
                        if (z != null) {
                            z.remove();
                        }
                    }
                }
            }, 20 * 20L);
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        if (plugin.getJoiningPlayers().containsKey(e.getPlayer())) {
            plugin.getJoiningPlayers().remove(e.getPlayer());
        }
        if (e.getReason()
                .equals(ChatColor.RED + "ANNIHILATION-TRIGGER-KICK-01")) {
            e.setReason(ChatColor.RED
                    +// Localizer.getLanguage(e.getPlayer()) + ".ERROR_GAME_PHASE"); 
                    "No puedes entrar en esta fase");
            e.setLeaveMessage(null);
        }
    }

    public void savePlayer(String playerName, ItemStack[] items, ItemStack[] armor, double health, float saturation, int level, int gm, int food, float exhaut, float exp, GameTeam team, boolean bol, String w) {
        PlayerSerializer.PlayerToConfig(playerName, items, armor, health, saturation, level, gm, food, exhaut, exp, team, bol, w);
    }
    
    public boolean isFallingToVoid(Player p){
        Location loc = p.getLocation();
        int a = p.getLocation().getBlockY();
        for(int i = a; i >= 0; i--){
            loc.add(0 , -1, 0);
            if(Bukkit.getWorld(loc.getWorld().getName()).getBlockAt(loc).getType() != Material.AIR){
                return false;
            }
        }
        return true;
    }
}
