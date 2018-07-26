package me.newpredator.Annihilation.commands;

import me.newpredator.Annihilation.Annihilation;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AnnihilationCommand implements CommandExecutor {
    private Annihilation plugin;

    public AnnihilationCommand(Annihilation plugin) {
        this.plugin = plugin;
    }

	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	
    	Player p = (Player)sender;
        String red = ChatColor.RED.toString();
        String green = ChatColor.GREEN.toString();
        if (args.length == 0 && p.hasPermission("hero.admin")) {
            sender.sendMessage("§b=====================================");
            sender.sendMessage("  ");
            sender.sendMessage("§lDTN §7creado por §b§lNewpredator (RoyalHero)");
            sender.sendMessage("  ");
            sender.sendMessage("§7v0.4 - Plugin bajo Copyright (BSD) RoyalHero");
            sender.sendMessage("  ");
            sender.sendMessage("§b=====================================");
        }
        
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("start")) {
                if (sender.hasPermission("anni.command.start")) {
                    if (!plugin.startTimer()) {
                        sender.sendMessage(red + "El juego ya habia comenzado");
                    } else {
                        sender.sendMessage(green + "Has puesto el juego en marcha");
                    }
                } else sender.sendMessage(red + "No puedes utilizar este commando!");
            }
                    
                    return true;
                }
        
        return false;
    }
    
}
