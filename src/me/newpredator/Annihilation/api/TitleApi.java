package me.newpredator.Annihilation.api;

import java.lang.reflect.Field;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_9_R1.IChatBaseComponent;
import net.minecraft.server.v1_9_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_9_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_9_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_9_R1.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_9_R1.PlayerConnection;

public class TitleApi
  extends JavaPlugin
  implements Listener
{
  public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String message)
  {
    sendTitle(player, fadeIn, stay, fadeOut, message, null);
  }
  
  public static void sendSubtitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String message)
  {
    sendTitle(player, fadeIn, stay, fadeOut, null, message);
  }
  
  public static void sendFullTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle)
  {
    sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
  }
  
  public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle)
  {
    PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
    
    PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(EnumTitleAction.TIMES, null, fadeIn.intValue(), stay.intValue(), fadeOut.intValue());
    connection.sendPacket(packetPlayOutTimes);
    if (subtitle != null)
    {
      subtitle = subtitle.replace("%player%", player.getDisplayName());
      subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
      IChatBaseComponent titleSub = ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
      PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, titleSub);
      connection.sendPacket(packetPlayOutSubTitle);
    }
    if (title != null)
    {
      title = title.replace("%player%", player.getDisplayName());
      title = ChatColor.translateAlternateColorCodes('&', title);
      IChatBaseComponent titleMain = ChatSerializer.a("{\"text\": \"" + title + "\"}");
      PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(EnumTitleAction.TITLE, titleMain);
      connection.sendPacket(packetPlayOutTitle);
    }
  }
  
  public static void sendTabTitle(Player player, String header, String footer)
  {
    if (header == null) {
      header = "";
    }
    header = ChatColor.translateAlternateColorCodes('&', header);
    if (footer == null) {
      footer = "";
    }
    footer = ChatColor.translateAlternateColorCodes('&', footer);
    
    header = header.replace("%player%", player.getDisplayName());
    footer = footer.replace("%player%", player.getDisplayName());
    
    PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
    IChatBaseComponent tabFoot = ChatSerializer.a("{\"text\": \"" + footer + "\"}");
    PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter();
    try
    {
      Field field = headerPacket.getClass().getDeclaredField("b");
      field.setAccessible(true);
      field.set(headerPacket, tabFoot);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      connection.sendPacket(headerPacket);
    }
  }
  
  
  boolean isInteger(String s)
  {
    try
    {
      Integer.parseInt(s);
    }
    catch (NumberFormatException e)
    {
      return false;
    }
    return true;
  }
  
}