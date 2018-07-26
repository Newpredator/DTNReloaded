package me.newpredator.Annihilation.listeners;

import java.util.HashMap;
import java.util.Map;

import me.newpredator.Annihilation.Annihilation;
import me.newpredator.Annihilation.Util;
import me.newpredator.Annihilation.object.GameTeam;
import me.newpredator.Annihilation.object.Kit;
import me.newpredator.Annihilation.object.PlayerMeta;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class ClassAbilityListener
  implements Listener
{
private final HashMap<String, Location> blockLocations = new HashMap<String, Location>();
private final HashMap<String, Long> cooldowns = new HashMap<String, Long>();
  private final Annihilation plugin;
private Map<String, Long> playerDelays = new HashMap<String, Long>();
  private int castDelay1 = 10;
  
  public ClassAbilityListener(Annihilation plugin)
  {
    this.plugin = plugin;
    Bukkit.getScheduler().runTaskTimer(plugin, new Runnable()
    {
      public void run()
      {
        ClassAbilityListener.this.update();
      }
    }, 20L, 20L);
  }
  
  @EventHandler
  public void onSpecialBlockBreak(BlockBreakEvent e)
  {
    Block b = e.getBlock();
    for (Map.Entry<String, Location> entry : this.blockLocations.entrySet()) {
      if (((Location)entry.getValue()).equals(b.getLocation()))
      {
        PlayerMeta meta = PlayerMeta.getMeta((String)entry.getKey());
        GameTeam ownerTeam = meta.getTeam();
        if (PlayerMeta.getMeta(e.getPlayer()).getTeam() == ownerTeam)
        {
          e.setCancelled(true);
          break;
        }
      }
    }
  }
  
  @EventHandler
  public void onScoutGrapple(PlayerFishEvent e) {
    Player player = e.getPlayer();
    player.getInventory().getItemInMainHand().setDurability((short)-10);
    if (PlayerMeta.getMeta(player).getKit() != Kit.EXPLORADOR) {
      return;
    }
    if (!player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Gancho")) {
      return;
    }
    Location hookLoc = e.getHook().getLocation();
    Location playerLoc = player.getLocation();
    double hookX = (int)hookLoc.getX();
    double hookY = (int)hookLoc.getY();
    double hookZ = (int)hookLoc.getZ();
    World hookW = Bukkit.getWorld(hookLoc.getWorld().getName());
    Location hookLoc2 = new Location(hookW, hookX, hookY, hookZ);
    hookLoc2.add(0.0D, -1.0D, 0.0D);
    Material inType = hookLoc.getWorld().getBlockAt(hookLoc2).getType();
    if (inType == Material.AIR) {
      return;
    }
    Location l1 = playerLoc.clone();
    l1.add(0.0D, 0.5D, 0.0D);
    player.teleport(l1);
    
    Vector diff = hookLoc.toVector().subtract(playerLoc.toVector());
    Vector vel = new Vector();
    double d = hookLoc.distance(playerLoc);
    vel.setX((1.0D + 0.07000000000000001D * d) * diff.getX() / d);
    vel.setY((1.0D + 0.03D * d) * diff.getY() / d + 0.04D * d);
    vel.setZ((1.0D + 0.07000000000000001D * d) * diff.getZ() / d);
    player.setVelocity(vel);
  }
  
  @EventHandler
  public void onFallDamage(EntityDamageEvent e) {
    if (!(e.getEntity() instanceof Player)) {
      return;
    }
    Player player = (Player)e.getEntity();
    PlayerMeta meta = PlayerMeta.getMeta(player);
    if ((meta.getKit() == Kit.EXPLORADOR) && (e.getCause() == EntityDamageEvent.DamageCause.FALL) && 
      (player.getInventory().getItemInMainHand() != null) && 
      (player.getInventory().getItemInMainHand().hasItemMeta()) && 
      (player.getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) && 
      (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("Gancho"))) {
      e.setDamage(e.getDamage() / 2.0D);
    }
  }

  private void update() {
    for (Map.Entry<String, Long> entry : this.cooldowns.entrySet())
    {
      long cooldown = ((Long)entry.getValue()).longValue();
      if (cooldown > 0L)
      {
        cooldown -= 1L;
        entry.setValue(Long.valueOf(cooldown));
        



        String name = (String)entry.getKey();
        Player player = Bukkit.getPlayer(name);
        if ((player == null) || 
        

          (!player.isOnline())) {}
      }
    }
  }
  
  @SuppressWarnings("unused")
@EventHandler
  public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
    Player player = event.getPlayer();
    String playerName = player.getName();
    PlayerMeta meta = PlayerMeta.getMeta(player);
    Kit kit = meta.getKit();
    if (kit == Kit.ACROBATA) {
      if (player.getGameMode() == GameMode.CREATIVE) {
        return;
      }
      event.setCancelled(true);
      boolean castLightning = true;
      if (this.playerDelays.containsKey(event.getPlayer().getName()))
      {
        long timeRemaining = System.currentTimeMillis() - ((Long)this.playerDelays.get(event.getPlayer().getName())).longValue();
        if (timeRemaining >= 1000 * this.castDelay1) {
          this.playerDelays.remove(event.getPlayer().getName());
        }
        else {
          int secondsRemaining = this.castDelay1 - (int)(timeRemaining / 1000L);
          event.getPlayer().sendMessage("§7§oTienes que esperar §5" + secondsRemaining + "" + (secondsRemaining != 1 ? " §7segundos" : " §7segundo") + " §7§opara volverlo a usar");
          event.setCancelled(true);
          castLightning = false;
          player.playSound(player.getLocation(), Sound.ENTITY_WOLF_HOWL, 2.0F, 1.0F);
        }
      }
      if (castLightning)
      {
        player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SHOOT, 2.0F, 1.0F);
        Util.ParticleEffects.sendToLocation(Util.ParticleEffects.LARGE_SMOKE, player.getLocation(), 1.0F, 1.0F, 1.0F, 0.0F, 50);
        Util.ParticleEffects.sendToLocation(Util.ParticleEffects.CLOUD, player.getLocation(), 1.0F, 1.0F, 1.0F, 0.0F, 80);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setVelocity(player.getLocation().getDirection()
          .multiply(1.5D).setY(1.5D));
        this.playerDelays.put(event.getPlayer().getName(), Long.valueOf(System.currentTimeMillis()));
      }
    }
  }
  
  @SuppressWarnings("unused")
@EventHandler
  public void onPlayerMove(PlayerMoveEvent event) {
    Player player = event.getPlayer();
    String playerName = player.getName();
    PlayerMeta meta = PlayerMeta.getMeta(player);
    Kit kit = meta.getKit();
    if (!player.getWorld().getName().equals("lobby")) {
      if ((kit == Kit.ACROBATA) && 
        (player.getGameMode() != GameMode.CREATIVE)) {
        if (player.getLocation().subtract(0.0D, 5.0D, 0.0D).getBlock().getType() != Material.AIR) {
          if (!player.isFlying()) {
            player.setAllowFlight(true);
          }
        }
      }
    }
  }

  
@EventHandler(priority=EventPriority.HIGHEST)
  public void onAtt(EntityDamageByEntityEvent event) {
    if (((event.getEntity() instanceof Player)) && ((event.getDamager() instanceof Player))) {
      final Player att = (Player)event.getDamager();
      final Player def = (Player)event.getEntity();
      if (def.getWorld().getName().equals("lobby")) {
        Location loca = att.getLocation();
        Location locd = def.getLocation();
        att.hidePlayer(def);
        event.setCancelled(true);
        Util.ParticleEffects.sendToLocation(Util.ParticleEffects.HEART, locd, 0.5F, 1.5F, 0.5F, 0.0F, 10);
        Util.ParticleEffects.sendToLocation(Util.ParticleEffects.LAVA_SPARK, locd, 0.5F, 1.5F, 0.5F, 0.0F, 50);
        Util.ParticleEffects.sendToLocation(Util.ParticleEffects.LARGE_SMOKE, locd, 0.5F, 1.5F, 0.5F, 0.0F, 50);
        att.playSound(loca, Sound.ENTITY_CHICKEN_EGG, 0.75F, 0.75F);
        att.sendMessage("§7§oEl jugador §b" + def.getDisplayName() + " §7§oha desaparecido.");
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
          public void run() {
            att.showPlayer(def);
            def.showPlayer(att);
          }
        }, 200L);
        

        return;
      }
     if (PlayerMeta.getMeta(att).getKit() == Kit.VAMPIRO) {
        Location loca = att.getLocation();
        Location locd = def.getLocation();
        PotionEffect heal = new PotionEffect(PotionEffectType.REGENERATION, 1, 10);
        att.addPotionEffect(heal);
        Util.ParticleEffects.sendToLocation(Util.ParticleEffects.HEART, loca, 0.7F, 0.7F, 0.7F, 1.0F, 6);
        att.playSound(loca, Sound.ENTITY_ENDERMEN_TELEPORT, 2.0F, 1.0F);
        def.playSound(locd, Sound.ENTITY_ENDERMEN_TELEPORT, 2.0F, 1.0F);
      }
        
      if (PlayerMeta.getMeta(att).getKit() == Kit.WITHER) {
        PotionEffect wither = new PotionEffect(PotionEffectType.WITHER, 20, 50);
        def.addPotionEffect(wither);
        Location loca = att.getLocation();
        Location locd = def.getLocation();
        Util.ParticleEffects.sendToLocation(Util.ParticleEffects.ANGRY_VILLAGER, loca, 0.7F, 0.7F, 0.7F, 1.0F, 6);
        att.playSound(loca, Sound.ENTITY_WITHER_SHOOT, 1.0F, 1.0F);
        def.playSound(locd, Sound.ENTITY_WITHER_SHOOT, 1.0F, 1.0F);
      }
      if (PlayerMeta.getMeta(att).getKit() == Kit.PIROMANO)
      {
        def.setFireTicks(40);
        Location loca = att.getLocation();
        Util.ParticleEffects.sendToLocation(Util.ParticleEffects.FIRE, loca, 0.7F, 0.7F, 0.7F, 1.0F, 6);
      }
      if (getDistance(def, att) > 3) {
        event.setCancelled(true);
        return;
      }
      if (att.hasPotionEffect(PotionEffectType.INVISIBILITY))
      {
        att.removePotionEffect(PotionEffectType.INVISIBILITY);
        def.removePotionEffect(PotionEffectType.INVISIBILITY);
      }
      if (def.hasPotionEffect(PotionEffectType.INVISIBILITY))
      {
        att.removePotionEffect(PotionEffectType.INVISIBILITY);
        def.removePotionEffect(PotionEffectType.INVISIBILITY);
      }
    }
  }
  
  @EventHandler
  public void onFallDamagePlayer(EntityDamageEvent e)
  {
    if (!(e.getEntity() instanceof Player)) {
      return;
    }
    Player player = (Player)e.getEntity();
    if ((e.getCause() == EntityDamageEvent.DamageCause.FALL) && 
      (player.hasPotionEffect(PotionEffectType.INVISIBILITY))) {
      player.removePotionEffect(PotionEffectType.INVISIBILITY);
    }
  }
  
  @EventHandler
  public void onShoot(EntityShootBowEvent event) {
    if (event.getBow() == null) {
      return;
    }
    Arrow a = (Arrow)event.getProjectile();
    Location loc = a.getLocation();
    Player player = (Player)a.getShooter();
    Util.ParticleEffects.sendToLocation(Util.ParticleEffects.BLUE_SPARKLE, loc, 0.2F, 0.2F, 0.2F, 0.0F, 30);
    a.setVelocity(player.getLocation().getDirection().multiply(3));
  }
  
  public int getDistance(Player player, Player pl){
    Player playersender = pl;
    
    int playerx = (int)player.getLocation().getX();
    int playery = (int)player.getLocation().getY();
    
    int senderx = (int)playersender.getLocation().getX();
    int sendery = (int)playersender.getLocation().getY();
    
    int finalx = playerx - senderx;
    int finaly = playery - sendery;
    if (finalx <= 0) {
      finalx = -finalx;
    }
    if (finaly <= 0) {
      finaly = -finaly;
    }
    int c = (int)Math.sqrt(Math.pow(finaly, 2.0D) + Math.pow(finalx, 2.0D));
    return c;
  }
  
  public double getDistanceDouble(Player player, Player pl) {
    Player playersender = pl;
    
    double playerx = player.getLocation().getX();
    double playery = player.getLocation().getY();
    
    double senderx = playersender.getLocation().getX();
    double sendery = playersender.getLocation().getY();
    
    double finalx = playerx - senderx;
    double finaly = playery - sendery;
    if (finalx <= 0.0D) {
      finalx = -finalx;
    }
    if (finaly <= 0.0D) {
      finaly = -finaly;
    }
    double c = Math.sqrt(Math.pow(finaly, 2.0D) + Math.pow(finalx, 2.0D));
    return c;
  }
  @EventHandler
  public void onTakeDamage(EntityDamageByEntityEvent ev)
  {
    if (((ev.getDamager() instanceof Player)) && ((ev.getEntity() instanceof Player))) {
      Player def = (Player)ev.getEntity();
      PlayerMeta defMeta = PlayerMeta.getMeta(def);
      if (defMeta.getKit() == Kit.DEFENSOR)
        ev.setDamage(ev.getDamage() / 1.5D);
    }
  }
    @EventHandler
    public void onTakeDamage2(EntityDamageByEntityEvent ev)
    {
      if (((ev.getDamager() instanceof Player)) && ((ev.getEntity() instanceof Player))) {
    	  Player attacker = (Player) ev.getDamager();
        PlayerMeta attMeta = PlayerMeta.getMeta(attacker);
        if (attMeta.getKit() == Kit.GUERRERO)
          ev.setDamage(ev.getDamage() * 1.5D);
      }
  }
    public final void onSneak(PlayerToggleSneakEvent event)
    {
      Player player = event.getPlayer();
      ItemStack[] Armadura = player.getInventory().getArmorContents();
      if (PlayerMeta.getMeta(player).getKit() == Kit.ESPIA) {
        if (player.isSneaking())
        {
          player.getInventory().removeItem(Armadura);
          player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 18000, 0));
          player.sendMessage(ChatColor.GREEN + "eres un espia no dejes de agacharte");
        }
        else
        {
          player.getInventory().setArmorContents(Armadura);
          player.removePotionEffect(PotionEffectType.INVISIBILITY);
          player.sendMessage(ChatColor.GOLD + "i see you run ! Run!");
        }
      }
    }

}