package me.newpredator.Annihilation.commands;

import me.newpredator.Annihilation.Annihilation;
import me.newpredator.Annihilation.maps.MapRollback;
import me.newpredator.Annihilation.maps.VoidGenerator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MapCommand implements CommandExecutor {
    private MapRollback loader;
    private Annihilation plugin;

    public MapCommand(Annihilation plugin, MapRollback loader) {
        this.plugin = plugin;
        this.loader = loader;
    }

    @SuppressWarnings("unused")
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String cyan = ChatColor.DARK_AQUA.toString();
        String gray = ChatColor.GRAY.toString();
        String red = ChatColor.RED.toString();
        final String green = ChatColor.GREEN.toString();

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("edit")) {
                    if (p.hasPermission("hero.admin")) {
                        loader.loadMap(args[1]);
                        WorldCreator wc = new WorldCreator(args[1]);
                        wc.generator(new VoidGenerator());
                        Bukkit.createWorld(wc);
                        sender.sendMessage(green + "Mapa " + args[1]
                                + " cargado para editar.");
                        if (sender instanceof Player) {
                            sender.sendMessage(green + "Teletransportando...");
                            World w = Bukkit.getWorld(args[1]);
                            Location loc = w.getSpawnLocation();
                            loc.setY(w.getHighestBlockYAt(loc));
                            ((Player) sender).teleport(loc);
                        }
                    } else sender.sendMessage(red + "Mapas: Costa, Canyon, Castillo, Volcan");
                    return true;
                }
                if (args[0].equalsIgnoreCase("save")) {
                    if (p.hasPermission("hero.admin")) {
                        if (Bukkit.getWorld(args[1]) != null) {
                            Bukkit.getWorld(args[1]).save();
                            final CommandSender s = sender;
                            final String mapName = args[1];
                            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                                @Override
                                public void run() {
                                    s.sendMessage(green + "Map " + mapName
                                            + " saved.");
                                    loader.saveMap(mapName);
                                }
                            }, 40L);
                        }
                    } else sender.sendMessage(red + "Mapas: Costa, Canyon, Castillo, Volcan");
                    return true;
                }
            }

            sender.sendMessage(red + "Mapas: Costa, Canyon, Castillo, Volcan");
        } else {
        	sender.sendMessage(red + "Mapas: Costa, Canyon, Castillo, Volcan");
        }
        return true;
    }


}
