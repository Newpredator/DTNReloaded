package me.newpredator.Annihilation.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamShortcutCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label,
            String[] args) {
        
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Solo los jugadores pueden utilizar ese commnado!");
            return true;
        }
        
        Player p = (Player) sender;
        if (label.equalsIgnoreCase("red")) p.performCommand("team red");
        if (label.equalsIgnoreCase("blue")) p.performCommand("team blue");
        if (label.equalsIgnoreCase("green")) p.performCommand("team green");
        if (label.equalsIgnoreCase("yellow")) p.performCommand("team yellow");
        
        return false;
    }

}
