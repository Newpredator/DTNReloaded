package me.newpredator.Annihilation.PlayerListeners;

import java.io.File;
import me.newpredator.Annihilation.Annihilation;
import me.newpredator.Annihilation.Util;
import me.newpredator.Annihilation.api.ActionApi;
import me.newpredator.Annihilation.api.HeaderAndFooter;
import me.newpredator.Annihilation.manager.PhaseManager;
import me.newpredator.Annihilation.manager.PlayerSerializer;
import me.newpredator.Annihilation.object.GameTeam;
import me.newpredator.Annihilation.object.Kit;
import me.newpredator.Annihilation.object.PlayerMeta;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;


public class JoinListener
  implements Listener
{
  private Annihilation plugin;
  
  public JoinListener(Annihilation pl)
  {
    this.plugin = pl;
  }
  
  @SuppressWarnings({ "static-access" })
@EventHandler
  public void onPlayerJoin(PlayerJoinEvent e)
  {
  
    e.setJoinMessage("");
    final Player player = e.getPlayer();
    String uuid = player.getName();
    player.setGameMode(GameMode.ADVENTURE);
    HeaderAndFooter.sendPlayerListTab(player, "&a&lRoyalHero &a&lNetwork &8|| &d&lDTN", "&7RoyalHero.Com");

    if (this.plugin.getNpcPlayers().containsKey(uuid))
    {
      player.kickPlayer(ChatColor.RED + "Tu NCP aun sigue VIVO");
      return;
    } else {
    	this.plugin.getNpcPlayers().remove(uuid);
    }
    if (!this.plugin.getJoiningPlayers().containsKey(player)) {
      this.plugin.getJoiningPlayers().put(player, uuid);
    }

    if ((this.plugin.getPhase() == 0) && (this.plugin.getVotingManager().isRunning()))
    {
     ActionApi.sendActionBar(player, "§5Esperando §8| §7Bienvenido §b" + player.getDisplayName(), 50);
    }
    PlayerMeta meta = PlayerMeta.getMeta(player);
    player.teleport(this.plugin.getMapManager().getAnnihilationSpawnPoint());
    PlayerInventory inv = player.getInventory();
    inv.setHelmet(null);
    inv.setChestplate(null);
    inv.setLeggings(null);
    inv.setBoots(null);
    player.getInventory().clear();   
    PermissionUser user = PermissionsEx.getUser(player.getName());
    String rank = user.getPrefix();
    String ranks = "";
    if (!rank.equals("")) {
        ranks = rank + " &r";
    }
    
    player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', ranks  + player.getName()));
    
    for (PotionEffect effect : player.getActivePotionEffects()) {
      player.removePotionEffect(effect.getType());
    }
    player.setLevel(0);
    player.setExp(0.0F);
    player.setHealth(player.getMaxHealth());
    player.setSaturation(20.0F);
    Util.giveClassSelector(player);
    Util.giveMapSelector(player);
    Util.giveShopSelector(player);
    Util.giveleaveItem(player);
    player.updateInventory();
    this.plugin.getSignHandler().updateSigns(meta.getTeam());
    this.plugin.getScoreboardHandler().update();
    Annihilation.getInstance().sb.update();
    Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
    {
      public void run()
      {
        if (player.isOnline()) {
          JoinListener.this.reJoinPlayer(player);
          player.updateInventory();
        }
      }
    }, 10L);
  }
  
@SuppressWarnings({ "static-access", "deprecation", "unlikely-arg-type" })
public void reJoinPlayer(Player p)
  {
    PlayerMeta meta = PlayerMeta.getMeta(p);
    if (p == null) {
      return;
    }
    if (!p.isOnline()) {
      return;
    }
    String playerName = p.getName();
    if (!Util.playerPlayed(p))
    {
      if (this.plugin.getPhase() > this.plugin.lastJoinPhase)
      {
        if ((!p.isOp()) || (!p.getName().equals("Newpredator")))
        {
          if ((p != null) && (p.isOnline()))
          {
            p.kickPlayer(ChatColor.RED + "No puedes entrar durante esta fase");
            return;
          }
          return;
        }
        return;
      }
      if (this.plugin.getJoiningPlayers().containsKey(p)) {
        this.plugin.getJoiningPlayers().remove(p);
      }
      p.updateInventory();
      return;
    }
    if(PhaseManager.getPhase() > 0){
    if (!meta.getTeam().getNexus().isAlive()) {
      if (meta.getTeam() != GameTeam.NONE) {
        p.kickPlayer("§cTu alma se ha desconectado del nexo, no puedes volver a entrar.");
      }
    }
    }
    File playerdataFile = new File("plugins/DTN/users/" + playerName + ".yml");
    FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerdataFile);
    p.getInventory().clear();
    PlayerSerializer.ConfigToPlayer(p, playerConfig);
    if (PlayerSerializer.isKilled(playerName))
    {
      if (this.plugin.kitsToGive.containsKey(p.getName()))
      {
        meta.setKit((Kit)this.plugin.kitsToGive.get(p.getName()));
        this.plugin.kitsToGive.remove(p.getName());
      }
      meta.getKit().give(p, meta.getTeam());
      meta.setAlive(true);
      p.setCompassTarget(meta.getTeam().getNexus().getLocation());
      p.setGameMode(GameMode.SURVIVAL);
      p.setHealth(p.getMaxHealth());
      p.setFoodLevel(20);
      p.setSaturation(20.0F);
      p.updateInventory();
      p.teleport(meta.getTeam().getRandomSpawn());
      p.sendMessage("NPC MUERTO ENTRA");
      p.updateInventory();
      if (this.plugin.getJoiningPlayers().containsKey(p)) {
        this.plugin.getJoiningPlayers().remove(p);
      }
      return;
    }
    PlayerSerializer.RetorePlayer(p);
    p.updateInventory();
    meta = PlayerMeta.getMeta(p);
    if (meta == null)
    {
      p.kickPlayer(ChatColor.RED + "Error el jugador no existe");
      return;
    }
    if (meta.getTeam() == GameTeam.NONE)
    {
      p.kickPlayer(ChatColor.RED + "El equipo esta destruido o esta lleno!");
      return;
    }
    p.teleport(meta.getTeam().getRandomSpawn());
    p.sendMessage("Se devuelven todas las cosas");
    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
    this.plugin.getSignHandler().updateSigns(meta.getTeam());
    this.plugin.getScoreboardHandler().update();
    p.setGameMode(GameMode.SURVIVAL);
    p.updateInventory();

    Annihilation.getScoreboardHandler().sb.getTeam(meta.getTeam().toString().toUpperCase()).addPlayer(p);
    
    PermissionUser user = PermissionsEx.getUser(p.getName());
    String rank = user.getPrefix();
    String ranks = "";
    if (!rank.equals(""))
        ranks = rank + "&r";
    
    p.setPlayerListName(ChatColor.translateAlternateColorCodes('&', ranks  + Annihilation.getScoreboardHandler().sb.getPlayerTeam(p).getPrefix() + " " +  p.getName()));
 for(Entity en : p.getWorld().getEntities()) {
	 if(en.getName() == p.getName() && en.equals(EntityType.ZOMBIE)) {
		 en.remove();
	 }
 }
    if (this.plugin.getJoiningPlayers().containsKey(p)) {
      this.plugin.getJoiningPlayers().remove(p);
    }
  }
}
