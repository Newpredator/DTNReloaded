package me.newpredator.Annihilation.commands;

import me.newpredator.Annihilation.Annihilation;
import me.newpredator.Annihilation.object.GameTeam;
import me.newpredator.Annihilation.object.PlayerMeta;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DistanceCommand implements CommandExecutor {
    private Annihilation plugin;

    public DistanceCommand(Annihilation instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label,
            String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (plugin.getPhase() == 0) {
                p.sendMessage(ChatColor.RED + "El juego no ha empezado");
                return false;
            }

            if (PlayerMeta.getMeta(p).getTeam() == GameTeam.NONE) {
                p.sendMessage(ChatColor.RED + "No estas en ningun equipo");
                return false;
            }

            p.sendMessage(ChatColor.GRAY + "=========[ " + ChatColor.DARK_AQUA.toString() + "Distancia"
                + ChatColor.GRAY + " ]=========");

            for (GameTeam t : GameTeam.values()) {
                if (t != GameTeam.NONE) {
                    showTeam(p, t);
                }
            }

            p.sendMessage(ChatColor.GRAY + "============================");
        } else {
            sender.sendMessage(ChatColor.RED + "No se puede enseñar la distancia");
        }

        return true;
    }

    private void showTeam(Player p, GameTeam t) {
        try {
            if (t.getNexus() != null && t.getNexus().getHealth() > 0)
                p.sendMessage(t.coloredName() + ChatColor.GRAY + " Distancia del Nexo: " + ChatColor.WHITE + ((int) p.getLocation().distance(t.getNexus().getLocation())) + ChatColor.GRAY + " Bloques");
        } catch (IllegalArgumentException ex) {

        }
    }
}
