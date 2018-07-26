package me.newpredator.Annihilation.commands;

import me.newpredator.Annihilation.manager.VotingManager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VoteCommand implements CommandExecutor {
    private final VotingManager manager;

    public VoteCommand(VotingManager manager) {
        this.manager = manager;
    }

    @SuppressWarnings("unused")
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label,
            String[] args) {
        Player player = (Player)sender;
        if (!manager.isRunning())
            sender.sendMessage(ChatColor.RED + "Ya ha terminado la votacion");
        else if (args.length == 0)
            listMaps(sender);
        else if (!manager.vote(sender, args[0])) {
            sender.sendMessage(ChatColor.RED + "Commando no valido");
            listMaps(sender);
        }
        return true;
    }

    @SuppressWarnings("unused")
	private void listMaps(CommandSender sender) {
        Player player = (Player)sender;
        sender.sendMessage(ChatColor.GRAY + "Todos los mapas a Votar");
        int count = 0;
        for (String map : manager.getMaps().values()) {
            count ++;
            sender.sendMessage(ChatColor.DARK_GRAY + " - " + ChatColor.AQUA + "[" + count + "] " + ChatColor.GRAY + map);
        }
    }
}
