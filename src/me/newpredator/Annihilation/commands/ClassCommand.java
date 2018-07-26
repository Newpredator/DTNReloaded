package me.newpredator.Annihilation.commands;

import me.newpredator.Annihilation.Util;
import me.newpredator.Annihilation.object.Kit;
import me.newpredator.Annihilation.object.PlayerMeta;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClassCommand implements CommandExecutor {

    @Override
   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    Player player = (Player)sender;
      if (!(sender instanceof Player)) {
     sender.sendMessage(ChatColor.RED + "Necesitas ser vip para Seleccionar el kit desde el comando " + ChatColor.GRAY + "  (Usa el portal)");
      }
      else if (args.length > 0) {
        Kit k = Kit.valueOf(args[0]);
        if (player.hasPermission("dtn.kit" + k.toString().toLowerCase())) {
          PlayerMeta meta = PlayerMeta.getMeta(player);
          meta.setKit(k);
          player.sendMessage(ChatColor.GREEN + "Has selecionado el Kit");
          return false;
        } else {
        player.sendMessage(ChatColor.RED + "§oNo tienes acceso a ese KIT compra VIP para desbloquearlo: royalhero.esy.es/Tienda");
        return false;
        }
      }
      Util.showClassSelector(player);
	return false;

  }
}