package me.newpredator.Annihilation.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.newpredator.Annihilation.stats.StatsUtil;

public class Stats implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(args.length == 0) {
			StatsUtil.getGlobalStats(p);
			return true;
		}
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("dtn") || args[0].equalsIgnoreCase("anni") || args[0].equalsIgnoreCase("annihilation")
					|| args[0].equalsIgnoreCase("destruyeelnexo") || args[0].equalsIgnoreCase("sw") || args[0].equalsIgnoreCase("skywars")
					|| args[0].equalsIgnoreCase("mj") || args[0].equalsIgnoreCase("monkeychallenge") || args[0].equalsIgnoreCase("monkeyjump")
					|| args[0].equalsIgnoreCase("monkey") || args[0].equalsIgnoreCase("ffa")) {
				
				if(args[0].equalsIgnoreCase("dtn") || args[0].equalsIgnoreCase("anni") || args[0].equalsIgnoreCase("annihilation") || args[0].equalsIgnoreCase("destruyeelnexo")) {
					StatsUtil.getDTNStats(p);
					return true;
					
				}
				if(args[0].equalsIgnoreCase("sw") || args[0].equalsIgnoreCase("skywars")) {
					StatsUtil.getSWStats(p);
					return true;
				}
				if(args[0].equalsIgnoreCase("ffa")) {
					StatsUtil.getFFAStats(p);
					return true;
				}
				if(args[0].equalsIgnoreCase("mj") || args[0].equalsIgnoreCase("monkeychallenge") || args[0].equalsIgnoreCase("monkeyjump") || args[0].equalsIgnoreCase("monkey")) {
					StatsUtil.getMJStats(p);
					return true;
				}
			} else {
				  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
				  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
				  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
				  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
				  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
				  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
				  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
				  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
				  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
				  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
				  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
				  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
				  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
				  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
				  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
				  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&m---- &cArgumentos &b&m----"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
				  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4-> &bannihilation"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4-> &bffa"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4-> &bmonkeyjump"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4-> &bskywars"));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
				  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&m---- &cArgumentos &b&m----"));
				  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
				  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
				return true;
			}
		}
		return false;
	}

}
