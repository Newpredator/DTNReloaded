package me.newpredator.Annihilation.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.newpredator.Annihilation.stats.StatsType;
import me.newpredator.Annihilation.stats.StatsUtil;

public class Gemas implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(args.length == 0) {
			String uuid = p.getUniqueId().toString();
			String nbalance;
			if(StatsUtil.getStat(StatsType.GEMAS, uuid) > 1 || StatsUtil.getStat(StatsType.GEMAS, uuid) == 0) {
				nbalance = " &3gemas";
			} else {
				nbalance = " &3gema";
			}
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bTienes: &c" + StatsUtil.getStat(StatsType.GEMAS, uuid)
			+ nbalance));
			return true;
		}
        if(args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("take")) {
        	if(p.hasPermission("hero.admin")) {
        		if(args[0].equalsIgnoreCase("give")) {
        			if(args.length == 1 ||  args.length == 2) {
        				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lUsa: &r/gemas give (player) gemas"));
        				return true;
        			}
        			if(args.length == 3) {
						int money = Integer.parseInt(args[2]);
						Player ar = Bukkit.getPlayer(args[1]);
						String uuid = ar.getUniqueId().toString();
						String nbalance;
						if(StatsUtil.getStat(StatsType.GEMAS, uuid) > 1 || StatsUtil.getStat(StatsType.GEMAS, uuid) == 0) {
							nbalance = " &3gemas";
						} else {
							nbalance = " &3gema";
						}
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3Has añadido a " + ar.getName().toString()
								+ " " + String.valueOf(money) + nbalance));
						StatsUtil.addStat(StatsType.GEMAS, uuid, money);
						return true;
        			}
        		}
        		if(args[0].equalsIgnoreCase("take")) {
        			if(args.length == 1 ||  args.length == 2) {
        				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lUsa: &r/gemas take (player) gemas"));
        				return true;
        			}
        			if(args.length == 3) {
						int money = Integer.parseInt(args[2]);
						Player ar = Bukkit.getPlayer(args[1]);
						String uuid = ar.getUniqueId().toString();
						String nbalance;
						if(StatsUtil.getStat(StatsType.GEMAS, uuid) > 1 || StatsUtil.getStat(StatsType.GEMAS, uuid) == 0) {
							nbalance = " &3gemas";
						} else {
							nbalance = " &3gema";
						}
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3Has quitado a " + ar.getName().toString()
								+ " " + String.valueOf(money) + nbalance));
						StatsUtil.QuitarStat(StatsType.GEMAS, uuid, money);
						return true;
        			}
        		}
        	} else {
        		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lError: &rNo tienes permiso."));
        		return true;
        	}
        } else {
        	p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lError: &rNo existe ese comando."));
        	return true;
        }
    	return false;
	}
}

