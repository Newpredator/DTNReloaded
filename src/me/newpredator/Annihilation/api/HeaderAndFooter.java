package me.newpredator.Annihilation.api;

import java.lang.reflect.Field;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_9_R1.IChatBaseComponent;
import net.minecraft.server.v1_9_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_9_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_9_R1.PlayerConnection;

public class HeaderAndFooter {
	
	public static void sendPlayerListTab(Player player, String header, String footer)
	  {
	    CraftPlayer craftplayer = (CraftPlayer)player;
	    PlayerConnection connection = 
	      craftplayer.getHandle().playerConnection;
	      IChatBaseComponent hj = ChatSerializer.a(ChatColor.translateAlternateColorCodes('&', "{\"text\": \"" + header + "\"}"));
	      IChatBaseComponent fj = ChatSerializer.a(ChatColor.translateAlternateColorCodes('&', "{\"text\": \"" + footer + "\"}"));
	    PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
	    try
	    {
	      Field headerField = packet.getClass().getDeclaredField("a");
	      headerField.setAccessible(true);
	      headerField.set(packet, hj);
	      headerField.setAccessible(!headerField.isAccessible());
	      
	      Field footerField = packet.getClass().getDeclaredField("b");
	      footerField.setAccessible(true);
	      footerField.set(packet, fj);
	      footerField.setAccessible(!footerField.isAccessible());
	    }
	    catch (Exception localException) {}
	    connection.sendPacket(packet);
	  }
}
    

