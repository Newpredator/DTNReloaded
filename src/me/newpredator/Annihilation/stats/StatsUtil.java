package me.newpredator.Annihilation.stats;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.newpredator.Annihilation.Annihilation;


public class StatsUtil
{
  public static boolean playerExists(String uuid)
  {
    try
    {
      ResultSet rs = Annihilation.mysql.query("SELECT * FROM  STATS WHERE UUID = '" + uuid + "'");
      if (rs.next()) {
        return rs.getString("UUID") != null;
      }
      return false;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return false;
  }
  
  public static void createPlayer(String uuid)
  {
    if (!playerExists(uuid)) {
    	Annihilation.mysql.update("INSERT INTO STATS(UUID, DTNKILLS, DTNDEATHS, DTNBREAKS, DTNWINS, DTNLOSSES, SWKILLS, SWDEATHS, SWWINS, SWLOSSES, FFAKILLS, FFADEATHS, MJWINS, MJLOSSES, GEMAS, FFAMAXRACHA) VALUES ('" + uuid + "', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');");
    }
  }
  
  
  public static Integer getStat(StatsType type, String uuid)
  {
    Integer i = Integer.valueOf(0);
    if (playerExists(uuid))
    {
      try
      {
        ResultSet rs = Annihilation.mysql.query("SELECT * FROM STATS WHERE UUID = '" + uuid + "'");
        if ((rs.next()) && (Integer.valueOf(rs.getInt(type.name())) == null)) {}
        i = Integer.valueOf(rs.getInt(type.name()));
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      createPlayer(uuid);
      getStat(type, uuid);
    }
    return i;
  }
  
  public static void setStat(StatsType type, String uuid, Integer value)
  {
    if (playerExists(uuid))
    {
    	Annihilation.mysql.update("UPDATE STATS SET " + type.name() + "= '" + value + "' WHERE UUID= '" + uuid + "';");
    }
    else
    {
      createPlayer(uuid);
      setStat(type, uuid, value);
    }
  }
  
  public static void addStat(StatsType type, String uuid, Integer value)
  {
    if (playerExists(uuid))
    {
      setStat(type, uuid, Integer.valueOf(getStat(type, uuid).intValue() + value.intValue()));
    }
    else
    {
      createPlayer(uuid);
      addStat(type, uuid, value);
    }
  }
  
  public static void QuitarStat(StatsType type, String uuid, Integer value)
  {
    if (playerExists(uuid))
    {
      setStat(type, uuid, Integer.valueOf(getStat(type, uuid).intValue() - value.intValue()));
    }
    else
    {
      createPlayer(uuid);
      QuitarStat(type, uuid, value);
    }
  }
  public static void getGlobalStats(Player p) {
	  String uuid = p.getUniqueId().toString();
	  int DTNWINS = StatsUtil.getStat(StatsType.DTNWINS, uuid);
	  int DTNLosses = StatsUtil.getStat(StatsType.DTNLOSSES, uuid);
	  int SWWINS = StatsUtil.getStat(StatsType.SWWINS, uuid);
	  int SWLOSSES = StatsUtil.getStat(StatsType.SWLOSSES, uuid);
	  int MJWINS = StatsUtil.getStat(StatsType.MJWINS, uuid);
	  int MJLOSSES = StatsUtil.getStat(StatsType.MJWINS, uuid);
	  int SWKills = StatsUtil.getStat(StatsType.SWKILLS, uuid);
	  int SWDeaths = StatsUtil.getStat(StatsType.SWDEATHS, uuid);
	  int FFAKills = StatsUtil.getStat(StatsType.FFAKILLS, uuid);
	  int FFADeaths = StatsUtil.getStat(StatsType.FFADEATHS, uuid);
	  int DTNDEATHS = StatsUtil.getStat(StatsType.DTNDEATHS, uuid);
	  int DTNKILLS = StatsUtil.getStat(StatsType.DTNKILLS, uuid);
	  int GEMAS = StatsUtil.getStat(StatsType.GEMAS, uuid);
	  int WINS = DTNWINS + SWWINS + MJWINS;
	  int LOSSES = DTNLosses + SWLOSSES + MJLOSSES;
	  int KILLS = DTNKILLS + SWKills + FFAKills;
	  int DEATHS = DTNDEATHS + SWDeaths + FFADeaths;
	  
	  //KD
	  double KILLSD = KILLS;
	  double DEATHSD = DEATHS;
      double kdr = KILLSD / DEATHSD;
       DecimalFormat format = new DecimalFormat("#.###");     
      format.format(kdr);
      
      //WL
	  double WINSD = WINS;
	  double LOSSESD = LOSSES;
      double wlr = WINSD / LOSSESD;     
     format.format(wlr);
      
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&m-----&3&lGlobalStats&b&m----"));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lJuegos Ganados: &3" + WINS));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lJuegos Perdidos: &3" + LOSSES));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lAsesinatos: &3" + KILLS));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lMuertes: &3" + DEATHS));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lGemas: &3" + GEMAS));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lKD: &3" + kdr));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lWL: &3" + wlr));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&m-----&3&lGlobalStats&b&m----"));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsa /stats (modalidad)"));
	  
	  
  }
  public static void getDTNStats(Player p) {
	  String uuid = p.getUniqueId().toString();
	  int DTNBREAKS = StatsUtil.getStat(StatsType.DTNBREAKS, uuid);
	  int DTNWINS = StatsUtil.getStat(StatsType.DTNWINS, uuid);
	  int DTNLosses = StatsUtil.getStat(StatsType.DTNLOSSES, uuid);
	  int DTNDEATHS = StatsUtil.getStat(StatsType.DTNDEATHS, uuid);
	  int DTNKILLS = StatsUtil.getStat(StatsType.DTNKILLS, uuid);
	  int GEMAS = StatsUtil.getStat(StatsType.GEMAS, uuid);
	  
	  //KD
	  double KILLSD = DTNWINS;
	  double DEATHSD = DTNDEATHS;
      double kdr = KILLSD / DEATHSD;
      DecimalFormat format = new DecimalFormat("#.###");     
     format.format(kdr);
     
     //WL
	  double WINSD = DTNWINS;
	  double LOSSESD = DTNLosses;
     double wlr = WINSD / LOSSESD;     
    format.format(wlr);
    
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&m------&3&lDTNStats&b&m-----"));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bJuegos Ganados: &3" + DTNWINS));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bJuegos Perdidos: &3" + DTNLosses));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bNexos picados: &3" + DTNBREAKS));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bAsesinatos: &3" + DTNKILLS));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bMuertes: &3" + DTNDEATHS));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bGemas: &3" + GEMAS));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lKD: &3" + kdr));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lWL: &3" + wlr));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&m------&3&lDTNStats&b&m-----"));
  }
  public static void getSWStats(Player p) {
	  String uuid = p.getUniqueId().toString();
	  int SWWINS = StatsUtil.getStat(StatsType.SWWINS, uuid);
	  int SWLOSSES = StatsUtil.getStat(StatsType.SWLOSSES, uuid);
	  int SWKills = StatsUtil.getStat(StatsType.SWKILLS, uuid);
	  int SWDeaths = StatsUtil.getStat(StatsType.SWDEATHS, uuid);
	  int GEMAS = StatsUtil.getStat(StatsType.GEMAS, uuid);
	  
	  //KD
	  double KILLSD = SWKills;
	  double DEATHSD = SWDeaths;
      double kdr = KILLSD / DEATHSD;
      DecimalFormat format = new DecimalFormat("#.###");     
     format.format(kdr);
     
     //WL
	  double WINSD = SWWINS;
	  double LOSSESD = SWLOSSES;
     double wlr = WINSD / LOSSESD;     
    format.format(wlr);
    
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&m------&3&lSWStats&b&m-----"));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bJuegos Ganados: &3" + SWWINS));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bJuegos Perdidos: &3" + SWLOSSES));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bAsesinatos: &3" + SWKills));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bMuertes: &3" + SWDeaths));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bGemas: &3" + GEMAS));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lKD: &3" + kdr));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lWL: &3" + wlr));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&m------&3&lSWStats&b&m-----"));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
  }
  public static void getMJStats(Player p) {
	  String uuid = p.getUniqueId().toString();
	  int MJWINS = StatsUtil.getStat(StatsType.MJWINS, uuid);
	  int MJLOSSES = StatsUtil.getStat(StatsType.MJWINS, uuid);
	  int GEMAS = StatsUtil.getStat(StatsType.GEMAS, uuid);
	  
	     //WL
	  double WINSD = MJWINS;
	  double LOSSESD = MJLOSSES;
	     double wlr = WINSD / LOSSESD;  
	     DecimalFormat format = new DecimalFormat("#.###");    
	    format.format(wlr);
	    
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&m------&3&lMJStatS&b&m-----"));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bJuegos Ganados: &3" + MJWINS));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bJuegos Perdidos: &3" + MJLOSSES));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bGemas: &3" + GEMAS));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lWL: &3" + wlr));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&m------&3&lMJStats&b&m-----"));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
  }
  public static void getFFAStats(Player p) {
	  String uuid = p.getUniqueId().toString();
	  int FFAKills = StatsUtil.getStat(StatsType.FFAKILLS, uuid);
	  int FFADeaths = StatsUtil.getStat(StatsType.FFADEATHS, uuid);
	  int GEMAS = StatsUtil.getStat(StatsType.GEMAS, uuid);
	  int FFAMAXREACH = StatsUtil.getStat(StatsType.FFAMAXRACHA, uuid);
	  
	  // kd
	  double KILLSD = FFAKills;
	  double DEATHSD = FFADeaths;
      double kdr = KILLSD / DEATHSD;
      DecimalFormat format = new DecimalFormat("#.###");     
     format.format(kdr);
     
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&m-----&3&lFFAStats&b&m----"));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bAsesinatos: &3" + FFAKills));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bMuertes: &3" + FFADeaths));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bMax Racha: &3" + FFAMAXREACH));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bGemas: &3" + GEMAS));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lKD: &3" + kdr));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&m-----&3&lFFAStats&b&m----"));
	  p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
  }	  
  
}
  
