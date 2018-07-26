package me.newpredator.Annihilation.commands;

import me.newpredator.Annihilation.Annihilation;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class LeaveCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 0) {
			Player p = (Player)sender;
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF("AnniLobby");
            p.sendPluginMessage(Annihilation.getInstance(), "BungeeCord", out.toByteArray());
		}
		return true;
	}

}
