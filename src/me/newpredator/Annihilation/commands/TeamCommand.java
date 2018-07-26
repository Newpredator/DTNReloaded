package me.newpredator.Annihilation.commands;

import me.newpredator.Annihilation.Annihilation;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamCommand implements CommandExecutor {
    private final Annihilation plugin;

    public TeamCommand(Annihilation plugin) {
        this.plugin = plugin;
    }

	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label,
            String[] args) {
        if (args.length == 0) {
        	plugin.ListTeams((Player) sender);
        } else {
        	
            if (!(sender instanceof Player)) {
            	plugin.ListTeams((Player) sender);
            } else {
                plugin.joinTeam((Player) sender, args[0]);
            }
        }
        return true;
    }

}
