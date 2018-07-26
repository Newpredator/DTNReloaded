package me.newpredator.Annihilation;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.newpredator.Annihilation.PlayerListeners.BlockListener;
import me.newpredator.Annihilation.PlayerListeners.DamageListener;
import me.newpredator.Annihilation.PlayerListeners.InteractListener;
import me.newpredator.Annihilation.PlayerListeners.InventoryListener;
import me.newpredator.Annihilation.PlayerListeners.JoinListener;
import me.newpredator.Annihilation.PlayerListeners.QuitListener;
import me.newpredator.Annihilation.api.ActionApi;
import me.newpredator.Annihilation.api.AdventureMode;
import me.newpredator.Annihilation.api.GameStartEvent;
import me.newpredator.Annihilation.api.ItemStackBuilder;
import me.newpredator.Annihilation.api.PhaseChangeEvent;
import me.newpredator.Annihilation.chat.ChatListener;
import me.newpredator.Annihilation.chat.ChatUtil;
import me.newpredator.Annihilation.commands.AnnihilationCommand;
import me.newpredator.Annihilation.commands.ClassCommand;
import me.newpredator.Annihilation.commands.DistanceCommand;
import me.newpredator.Annihilation.commands.Gemas;
import me.newpredator.Annihilation.commands.LeaveCommand;
import me.newpredator.Annihilation.commands.MapCommand;
import me.newpredator.Annihilation.commands.Stats;
import me.newpredator.Annihilation.commands.TeamCommand;
import me.newpredator.Annihilation.commands.TeamShortcutCommand;
import me.newpredator.Annihilation.commands.VoteCommand;
import me.newpredator.Annihilation.listeners.BossListener;
import me.newpredator.Annihilation.listeners.ClassAbilityListener;
import me.newpredator.Annihilation.listeners.ClassAbilityListenerII;
import me.newpredator.Annihilation.listeners.ClassAbilityListenerIII;
import me.newpredator.Annihilation.listeners.ClassAbilityListenerIIII;
import me.newpredator.Annihilation.listeners.ClassAbilityListenerIIIII;
import me.newpredator.Annihilation.listeners.CraftingListener;
import me.newpredator.Annihilation.listeners.EnderChestListener;
import me.newpredator.Annihilation.listeners.EnderFurnaceListener;
import me.newpredator.Annihilation.listeners.MotdListener;
import me.newpredator.Annihilation.listeners.PlayerListener;
import me.newpredator.Annihilation.listeners.ResourceListener;
import me.newpredator.Annihilation.listeners.SeleccionarTeam;
import me.newpredator.Annihilation.listeners.SoulboundListener;
import me.newpredator.Annihilation.listeners.WandListener;
import me.newpredator.Annihilation.listeners.WorldListener;
import me.newpredator.Annihilation.listeners.ZombieListener;
import me.newpredator.Annihilation.manager.BossManager;
import me.newpredator.Annihilation.manager.ConfigManager;
import me.newpredator.Annihilation.manager.MapManager;
import me.newpredator.Annihilation.manager.PhaseManager;
import me.newpredator.Annihilation.manager.PlayerSerializer;
import me.newpredator.Annihilation.manager.RestartHandler;
import me.newpredator.Annihilation.manager.ScoreboardManager;
import me.newpredator.Annihilation.manager.SignManager;
import me.newpredator.Annihilation.manager.VotingManager;
import me.newpredator.Annihilation.maps.MapRollback;
import me.newpredator.Annihilation.object.BlockObject;
import me.newpredator.Annihilation.object.Boss;
import me.newpredator.Annihilation.object.GameTeam;
import me.newpredator.Annihilation.object.Kit;
import me.newpredator.Annihilation.object.PlayerMeta;
import me.newpredator.Annihilation.object.Shop;
import me.newpredator.Annihilation.stats.MySQL;
import me.newpredator.Annihilation.stats.StatsType;
import me.newpredator.Annihilation.stats.StatsUtil;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.Team;

public final class Annihilation extends JavaPlugin implements Listener {

    public final HashMap<String, Kit> kitsToGive = new HashMap<String, Kit>();
    public String prefix;
    public static MySQL mysql;
    public ConfigManager configManager;
    public static VotingManager voting;
    private static MapManager maps;
    private PhaseManager timer;
    private SeleccionarTeam menu;
    public ResourceListener resources;
    private EnderChestListener enderChests;
    private SignManager sign;
    public static ScoreboardManager sb;
    private BossManager boss;
    private HashMap<String, Entity> zombies;
    public static HashMap<String, String> servidor = new HashMap<String, String>();
    private HashMap<Player, String> logoutPlayers;
    public boolean updateAvailable = false;
    public boolean motd = true;
    public String newVersion;
    private HashMap<Player, String> portal;
    private HashMap<String, String> npcP;
    private HashMap<Player, String> joining;
    public int build = 5;
    public int lastJoinPhase = 3;
    public int respawn = 10;
    private static Annihilation anni;
    public ClassAbilityListenerII cabiII;
    public HashMap<Player, BlockObject> crafting;
    private Logger log;
    private static Plugin plugin;
	private EnderFurnaceListener enderFurnaces;

    public static Annihilation getInstance() {
        return anni;
    }

    @Override
    public void onEnable() {
    	
    	plugin = this;
          menu = new SeleccionarTeam(this);
       Bukkit.getPluginManager().registerEvents(this, this);
        anni = this;
        configManager = new ConfigManager(this);
        configManager.loadConfigFiles("config.yml", "mapas.yml", "shops.yml", "stats.yml", "servidor.yml");
        
        ActionApi.nmsver = Bukkit.getServer().getClass().getPackage().getName();
        ActionApi.nmsver = ActionApi.nmsver.substring(ActionApi.nmsver.lastIndexOf(".") + 1);

        if (ActionApi.nmsver.equalsIgnoreCase("v1_8_") && ActionApi.nmsver.equalsIgnoreCase("v1_9_") && ActionApi.nmsver.equalsIgnoreCase("v1_10_") && ActionApi.nmsver.equalsIgnoreCase("v1_11_") && ActionApi.nmsver.equalsIgnoreCase("v1_12_") || ActionApi.nmsver.startsWith("v1_7_")) {
            ActionApi.useOldMethods = true;
        }
        
        Configuration config = configManager.getConfig("config.yml");
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        log = Logger.getLogger("Minecraft");
        File mapsdata = new File(plugin.getDataFolder() + "/mapas/");
        MapRollback mapRollback = new MapRollback(plugin.getLogger(), mapsdata);
        maps = new MapManager(this, mapRollback, configManager.getConfig("mapas.yml"));
        crafting = new HashMap<Player, BlockObject>();
        Configuration shops = configManager.getConfig("shops.yml");
        new Shop(this, "Weapon", shops);
        new Shop(this, "Brewing", shops);
        resources = new ResourceListener(this);
        enderChests = new EnderChestListener();
        sign = new SignManager(this);
        timer = new PhaseManager(this, config.getInt("start-delay"),
                config.getInt("phase-period"));
        voting = new VotingManager(this);
        sb = new ScoreboardManager();
        boss = new BossManager(this);
        portal = new HashMap<Player, String>();
        zombies = new HashMap<String, Entity>();
        logoutPlayers = new HashMap<Player, String>();
        npcP = new HashMap<String, String>();
        joining = new HashMap<Player, String>();
        PluginManager pm = getServer().getPluginManager();
        new PlayerMeta(this);
        File servidorFile = new File("plugins/" + getDescription().getName() + "/servidor.yml");
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(servidorFile);
        enderFurnaces = new EnderFurnaceListener(this);
        for(String s : yml.getKeys(false)){
            servidor.put(s, yml.getString(s));
        }
        prefix = "DTN";
        sign.loadSigns();
        sb.resetScoreboard(ChatColor.GREEN + "/voto [mapa nombre] para votar");
        cabiII = new ClassAbilityListenerII(this);
        build = this.getConfig().getInt("build", 5);
        lastJoinPhase = this.getConfig().getInt("lastJoinPhase", 2);
        respawn = this.getConfig().getInt("bossRespawnDelay", 10);
        pm.registerEvents(resources, this);
        pm.registerEvents(enderChests, this);
     
        pm.registerEvents(new ClassAbilityListenerIIIII(this), this);
        pm.registerEvents(new ClassAbilityListenerIIII(), this);
        pm.registerEvents(new ClassAbilityListenerIII(), this);
        pm.registerEvents(new ChatListener(this), this);
        pm.registerEvents(new PlayerListener(this), this);
        pm.registerEvents(new WorldListener(), this);
        pm.registerEvents(new SoulboundListener(), this);
        pm.registerEvents(new WandListener(this), this);
        pm.registerEvents(new CraftingListener(), this);
        pm.registerEvents(new ClassAbilityListener(this), this);
        pm.registerEvents(new BossListener(this), this);
        pm.registerEvents(new AdventureMode(), this);
        pm.registerEvents(new ClassAbilityListenerII(this), this);
        pm.registerEvents(new MotdListener(this), this);
        pm.registerEvents(new BlockListener(this), this);
        pm.registerEvents(new DamageListener(this), this);
        pm.registerEvents(enderFurnaces, this);
        pm.registerEvents(new InteractListener(this), this);
        pm.registerEvents(new InventoryListener(this), this);
        pm.registerEvents(new JoinListener(this), this);
        pm.registerEvents(new QuitListener(this), this);
        pm.registerEvents(new ZombieListener(this), this);
        getCommand("annihilation").setExecutor(new AnnihilationCommand(this));
        getCommand("help").setExecutor(new AnnihilationCommand(this));   
        getCommand("class").setExecutor(new ClassCommand());
        getCommand("stats").setExecutor(new Stats());
        getCommand("team").setExecutor(new TeamCommand(this));
        getCommand("salir").setExecutor(new LeaveCommand());
        getCommand("gemas").setExecutor(new Gemas());
       
        getCommand("vote").setExecutor(new VoteCommand(voting));
        getCommand("red").setExecutor(new TeamShortcutCommand());
        getCommand("green").setExecutor(new TeamShortcutCommand());
        getCommand("yellow").setExecutor(new TeamShortcutCommand());
        getCommand("blue").setExecutor(new TeamShortcutCommand());
        getCommand("distance").setExecutor(new DistanceCommand(this));
        getCommand("map").setExecutor(new MapCommand(this, mapRollback));
        
Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&m-----------------------------"));
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "    &a&lDTN By &c&lRoyalHero"));
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "       &d&lVersion: " + getDescription().getVersion()));
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "      &e&lWeb: &r" + getDescription().getWebsite()));
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&m-----------------------------"));
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.kickPlayer(ChatColor.translateAlternateColorCodes('&', "&e&lRestarteando Annihilation"));
		}
		
		
            
            for(World worlds : Bukkit.getServer().getWorlds()) {
            	worlds.setAutoSave(false);
            }
        

        motd = config.getBoolean("enableMotd", true);

        String host = getConfig().getString("MySQL.host");
        String name = getConfig().getString("MySQL.name");
        String user = getConfig().getString("MySQL.user");
        String pass = getConfig().getString("MySQL.pass");
        mysql = new MySQL(host, name, user, pass);
    	mysql.update("CREATE TABLE IF NOT EXISTS STATS(UUID varchar(64), DTNKILLS int, DTNDEATHS int, "
    			+ "DTNWINS int, DTNBREAKS int, DTNLOSSES int, FFAKILLS int, FFADEATHS int, "
    			+ "SWWINS int, SWLOSSES int, SWKILLS int, SWDEATHS int, MJWINS int, MJLOSSES int, GEMAS int, FFAMAXRACHA int)");


        reset();

        ChatUtil.setRoman(getConfig().getBoolean("roman", false));
        File invFile = new File("plugins/"
        		+ "DTN/users");
        if (invFile.isDirectory()) {
            for (File f : invFile.listFiles()) {
                f.delete();
            }
        }
    }

    @Override
    public void onDisable() {
    	mysql.close();
        for (Entity e : getZombies().values()) {
        	if(e != null)
            e.remove();
        }
        getZombies().clear();
        for(World worlds : Bukkit.getServer().getWorlds()) {
        	worlds.setAutoSave(false);
            Bukkit.getServer().unloadWorld(worlds, true);
        }
    }

    public boolean startTimer() {
        if (timer.isRunning()) {
            return false;
        }

        timer.start();

        return true;
    }

    public void loadMap(final String map) {
        FileConfiguration config = configManager.getConfig("mapas.yml");
        ConfigurationSection section = config.getConfigurationSection(map);

        World w = getServer().getWorld(map);

        for (GameTeam team : GameTeam.teams()) {
            String name = team.name().toLowerCase();
            if (section.contains("spawns." + name)) {
                for (String s : section.getStringList("spawns." + name)) {
                    team.addSpawn(Util.parseLocation(getServer().getWorld(map),
                            s));
               
                }
            }
			if (section.contains("furnaces." + name)) {
				Location loc = Util.parseLocation(w, section.getString("furnaces." + name));
				enderFurnaces.setFurnaceLocation(team, loc);
			}
            if (section.contains("nexuses." + name)) {
                Location loc = Util.parseLocation(w,
                        section.getString("nexuses." + name));
                team.loadNexus(loc, 75);
            }
        
        }
	

        if (section.contains("bosses")) {
            HashMap<String, Boss> bosses = new HashMap<String, Boss>();
            ConfigurationSection sec = section
                    .getConfigurationSection("bosses");
            for (String boss : sec.getKeys(false)) {
                bosses.put(
                        boss,
                        new Boss(boss, sec.getInt(boss + ".hearts") * 2, sec
                                .getString(boss + ".name"), Util.parseLocation(
                                        w, sec.getString(boss + ".spawn")), Util
                                .parseLocation(w,
                                        sec.getString(boss + ".chest"))));
            }
            boss.loadBosses(bosses);
        }

        if (section.contains("diamonds")) {
            Set<Location> diamonds = new HashSet<Location>();
            for (String s : section.getStringList("diamonds")) {
                diamonds.add(Util.parseLocation(w, s));
            }
            resources.loadDiamonds(diamonds);
        }
    }

	@SuppressWarnings("deprecation")
	public void startGame() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            for (Player pp : Bukkit.getOnlinePlayers()) {
                p.showPlayer(pp);
                pp.showPlayer(p);
            }
        }

        Bukkit.getPluginManager().callEvent(
                new GameStartEvent(maps.getCurrentMap()));
        sb.score.clear();

        for (OfflinePlayer score : sb.sb.getPlayers()) {
            sb.sb.resetScores(score);
        }
  
        sb.obj.setDisplayName("Ї6ЇlMapa: Ї6"
                + WordUtils.capitalize(voting.getWinner()) + " Ї7(" + configManager.getConfig("config.yml").getInt("SBNLobby") + "Ї7)");
        
        

        for (GameTeam t : GameTeam.teams()) {   
        	sb.team.clear();

            sb.score.put(t.name(), sb.obj.getScore(Bukkit
                    .getOfflinePlayer(t.color() + WordUtils.capitalize(t.name()
                                    .toLowerCase() + " Nexus:"))));
            sb.score.get(t.name()).setScore(t.getNexus().getHealth());
            
            if(sb.sb.getTeam(t.name() + "SB") != null){
            Team sbt = sb.sb.getTeam(t.name() + "SB");
            
            sbt.addPlayer(Bukkit.getOfflinePlayer((t.name().toLowerCase()
                                    + " Nexus:")));
            
            sbt.setPrefix(t.color().toString());
            
            sb.score.put(ChatColor.DARK_PURPLE + "Fase Actual: ", sb.obj.getScore(Bukkit
                    .getOfflinePlayer(ChatColor.DARK_PURPLE + WordUtils.capitalize("Fase Actual: "))));
            sb.score.get(ChatColor.DARK_PURPLE + "Fase Actual: ").setScore(PhaseManager.getPhase());
            }else{
            	Team sbt = sb.sb.registerNewTeam(t.name() + "SB");
                
                sbt.addPlayer(Bukkit.getOfflinePlayer((t.name().toLowerCase()
                                        + " Nexus:")));
                
                sbt.setPrefix(t.color().toString());
                
                sb.score.put(ChatColor.DARK_PURPLE + "Fase Actual: ", sb.obj.getScore(Bukkit
                        .getOfflinePlayer(ChatColor.DARK_PURPLE + WordUtils.capitalize("Fase Actual: "))));
                sb.score.get(ChatColor.DARK_PURPLE + "Fase Actual: ").setScore(PhaseManager.getPhase());
            }
            

        }


        for (Player p : getServer().getOnlinePlayers()) {
            if (PlayerMeta.getMeta(p).getTeam() != GameTeam.NONE) {
                Util.sendPlayerToGame(p, this);
            }
        }

        sb.update();

        getServer().getScheduler().runTaskTimer(this, new Runnable() {
            @Override
            public void run() {
                for (Player p : getServer().getOnlinePlayers()) {
                    if (PlayerMeta.getMeta(p).getKit() == Kit.EXPLORADOR) {
                        PlayerMeta.getMeta(p).getKit().addScoutParticles(p);
                    }
                }
            }
        }, 0L, 1200L);

        getServer().getScheduler().runTaskTimer(this, new Runnable() {
            @Override
            public void run() {
                for (GameTeam t : GameTeam.values()) {
                    if (t != GameTeam.NONE && t.getNexus().isAlive()) {
                        Location nexus = t.getNexus().getLocation().clone();
                        nexus.add(0.5, 0, 0.5);
                        Util.ParticleEffects.sendToLocation(Util.ParticleEffects.DRIP_LAVA, nexus, 0.2F, 0.0F, 0.2F, 0, 1);
                        Util.ParticleEffects.sendToLocation(Util.ParticleEffects.ENCHANTMENT_TABLE, nexus, 1F, 1F, 1F, 0, 10);
                        Util.ParticleEffects.sendToLocation(Util.ParticleEffects.TOWN_AURA, nexus, 4F, 3F, 4F, 0, 80);
                    }
                }
            }
        }, 100L, 5L);
    }

    @SuppressWarnings("static-access")
	public void advancePhase() {
        ChatUtil.phaseMessage(timer.getPhase());

        if (timer.getPhase() == 2) {
            boss.spawnBosses();
        }

        if (timer.getPhase() == 3) {
            resources.spawnDiamonds();
        }

        Bukkit.getPluginManager().callEvent(
                new PhaseChangeEvent(timer.getPhase()));

        getSignHandler().updateSigns(GameTeam.RED);
        getSignHandler().updateSigns(GameTeam.BLUE);
        getSignHandler().updateSigns(GameTeam.GREEN);
        getSignHandler().updateSigns(GameTeam.YELLOW);
    }

    public void onSecond() {
        long time = timer.getTime();

        if (time == -5L) {
            String winner = voting.getWinner();
            maps.selectMap(winner);
            getServer().broadcastMessage(ChatColor.GREEN + "Se ha elegido el Mapa: " + ChatColor.WHITE +  
                    WordUtils.capitalize(winner));
            loadMap(winner);

            voting.end();

        }

        if (time == 0L) {
            startGame();
        }
    }

    @SuppressWarnings("static-access")
	public int getPhase() {
        return timer.getPhase();
    }

    public static MapManager getMapManager() {
        return maps;
    }

    


    public ConfigManager getConfigManager() {
        return configManager;
    }

    public int getPhaseDelay() {
        return configManager.getConfig("config.yml").getInt("phase-period");
    }

    public void log(String m, Level l) {
        log.log(l, m);
    }

    public static VotingManager getVotingManager() {
        return voting;
    }

    public static ScoreboardManager getScoreboardHandler() {
        return sb;
    }
    
	public void endGame(GameTeam winner) {
    
        if (winner == null)
            return;

        ChatUtil.winMessage(winner);
        timer.stop();

        for (Player p : getServer().getOnlinePlayers()) {
            if (PlayerMeta.getMeta(p).getTeam() == winner)
				StatsUtil.addStat(StatsType.DTNWINS, p.getUniqueId().toString(), 1);
            
           
        }
       

        long restartDelay = configManager.getConfig("config.yml").getLong(
                "restart-delay");
        RestartHandler rs = new RestartHandler(this, restartDelay);
        rs.start(timer.getTime(), winner.getColor(winner));
        for (Player p : winner.getPlayers()) {
            if (PlayerMeta.getMeta(p).getTeam() == winner)
                    if(p.hasPermission("vip.guardian")) {
                  StatsUtil.addStat(StatsType.GEMAS, p.getUniqueId().toString(), 300);
                                        p.sendMessage("Їa+300 Gemas");
                        }else{
                        	StatsUtil.addStat(StatsType.GEMAS, p.getUniqueId().toString(), 100);
                                        p.sendMessage("Їa+100 Gemas");
                    }
        }
    }
	
    public void reset() {
            sb.resetScoreboard(ChatColor.GREEN + "/voto [nombre mapa] para votar");
        maps.reset();
        timer.reset();
        PlayerMeta.reset();
        for (Player p : getServer().getOnlinePlayers()) {
            PlayerMeta.getMeta(p).setTeam(GameTeam.NONE);
            p.teleport(maps.getAnnihilationSpawnPoint());
            
            ActionApi.sendActionBar(p, "Ї5Esperando Ї8| Ї7Bienvenido Їb" + p.getDisplayName(), 50);    
            p.setMaxHealth(20D);
            p.setHealth(20D);
            p.setFoodLevel(20);
            p.setSaturation(20F);
            p.setFlying(false);
            p.setGameMode(GameMode.ADVENTURE);
        }
        if (!portal.isEmpty()) {
            portal.clear();
        }
        voting.start();
        sb.update();

        for (Player p : Bukkit.getOnlinePlayers()) {
            for (Player pp : Bukkit.getOnlinePlayers()) {
                p.showPlayer(pp);
                pp.showPlayer(p);
            }
        }

        Bukkit.getScheduler().runTaskLater(this, new Runnable() {
            @Override
            public void run() {
                for (Player p : getServer().getOnlinePlayers()) {
                    PlayerInventory inv = p.getInventory();
                    inv.setHelmet(null);
                    inv.setChestplate(null);
                    inv.setLeggings(null);
                    inv.setBoots(null);

                    p.getInventory().clear();

                    for (PotionEffect effect : p.getActivePotionEffects()) {
                        p.removePotionEffect(effect.getType());
                    }

                    p.setLevel(0);
                    p.setExp(0);
                    p.setSaturation(20F);
                    Util.giveClassSelector(p);
                    Util.giveMapSelector(p);
                    Util.giveShopSelector(p);
                    Util.giveleaveItem(p);
                    p.getInventory().setItem(2, EQUIPOS());
                    p.updateInventory();
                }

                for (GameTeam t : GameTeam.values()) {
                    if (t != GameTeam.NONE) {
                        sign.updateSigns(t);
                    }
                }

                checkStarting();
            }
        }, 2L);
    }

    public void checkWin() {
        int alive = 0;
        GameTeam aliveTeam = null;
        for (GameTeam t : GameTeam.teams()) {
            if (t.getNexus().isAlive()) {
                alive++;
                aliveTeam = t;
            }
        }
        if (alive == 1) {
            endGame(aliveTeam);
            
        }
    }

    public SignManager getSignHandler() {
        return sign;
    }

    public void setSignHandler(SignManager sign) {
        this.sign = sign;
    }

    public void checkStarting() {
        if (!timer.isRunning()) {
            if (Bukkit.getOnlinePlayers().size() >= getConfig().getInt(
                    "requiredToStart")) {
                timer.start();
            }
        }
    }

    public BossManager getBossManager() {
        return boss;
    }

    public PhaseManager getPhaseManager() {
        return timer;
    }

    

	@SuppressWarnings("deprecation")
	public void joinTeam(Player player, String team) {
        PlayerMeta meta = PlayerMeta.getMeta(player);
        if (meta.getTeam() != GameTeam.NONE && !player.hasPermission("anni.team") || 
        		meta.getTeam() != GameTeam.NONE && !player.hasPermission("vip.heroe")) {
            player.sendMessage(ChatColor.GRAY + "No puedes cambiar de equipo");
            return;
        }
        GameTeam target;
        try {
            target = GameTeam.valueOf(team.toUpperCase());
        } catch (IllegalArgumentException e) {
            player.sendMessage(ChatColor.RED + "Este equipo no existe");
            ListTeams(player);
            return;
        }
        if (target == null) {
            player.sendMessage(ChatColor.DARK_PURPLE + "El equipo esta lleno prueba con otro");
            ListTeams(player);
            return;
        }
        if (Util.getTeamAllowEnter(target)
                && !player.hasPermission("anni.team")  || Util.getTeamAllowEnter(target)
                && !player.hasPermission("vip.heroe")) {
            player.sendMessage(ChatColor.DARK_PURPLE + "El equipo esta lleno prueba con otro");
            ListTeams(player);
            return;
        }

        if (target.getNexus() != null) {
            if (target.getNexus().getHealth() == 0 && getPhase() > 1) {
                player.sendMessage( ChatColor.RED + "No puedes entrar por que el nexo esta destruido");
                return;
            }
        }

        if (getPhase() > lastJoinPhase && !player.hasPermission("anni.phase") ||
        		getPhase() > lastJoinPhase && !player.hasPermission("vip.superheroe")) {
            player.kickPlayer(ChatColor.RED
                    + "No puedes entrar en el equipo en esta Fase!");
            return;
        }

        player.sendMessage(ChatColor.GRAY + "тттттттттттттттттттт");
    player.sendMessage(ChatColor.GRAY + "тттт" + target.color() + "тттттттттттт" + ChatColor.GRAY + "тттт");
    player.sendMessage(ChatColor.GRAY + "тттт" + target.color() + "тттттттттттт" + ChatColor.GRAY + "тттт");
    player.sendMessage(ChatColor.GRAY + "тттттттт" + target.color() + "тттт" + ChatColor.GRAY + "тттттттт");
    player.sendMessage(ChatColor.GRAY + "тттттттт" + target.color() + "тттт" + ChatColor.GRAY + "тттттттт Has entrado en el equipo");
    player.sendMessage(ChatColor.GRAY + "тттттттт" + target.color() + "тттт" + ChatColor.GRAY + "тттттттт " + target.coloredName());
    player.sendMessage(ChatColor.GRAY + "тттттттт" + target.color() + "тттт" + ChatColor.GRAY + "тттттттт");
    player.sendMessage(ChatColor.GRAY + "тттттттт" + target.color() + "тттт" + ChatColor.GRAY + "тттттттт");
    player.sendMessage(ChatColor.GRAY + "тттттттт" + target.color() + "тттт" + ChatColor.GRAY + "тттттттт");
    player.sendMessage(ChatColor.GRAY + "тттттттттттттттттттт");
    player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SHOOT, 1, 0);
    meta.setTeam(target);

        
        
        
        getScoreboardHandler().sb.getTeam(team.toUpperCase()).addPlayer(player);
        
        
        PermissionUser user = PermissionsEx.getUser(player.getName());
        String rank = user.getPrefix();
        String ranks = "";
        if (!rank.equals(""))
            ranks = rank + " &r";
        
        player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', ranks  + getScoreboardHandler().sb.getPlayerTeam(player).getPrefix() + player.getName()));

        if (getPhase() > 0) {
        	PlayerInventory inv = player.getInventory();
            inv.setHelmet(null);
            inv.setChestplate(null);
            inv.setLeggings(null);
            inv.setBoots(null);
            player.getInventory().clear();
            for (PotionEffect effect : player.getActivePotionEffects()) {
              player.removePotionEffect(effect.getType());
            }
            Util.sendPlayerToGame(player, this); 
        }
        final String playerName = player.getName();
        ItemStack[] items = player.getInventory().getContents();
        Double health = player.getHealth();
        ItemStack[] armor = player.getInventory().getArmorContents();
        Float satur = player.getSaturation();
        int level = player.getLevel();
        int gm = player.getGameMode().getValue();
        int food = player.getFoodLevel();
        float exauth = player.getExhaustion();
        float exp = player.getExp();
        boolean bol = true;
        String wname = Bukkit.getPlayer(playerName).getWorld().getName();
        PlayerSerializer.PlayerToConfig(playerName, items, armor, health, satur, level, gm, food, exauth, exp, target, bol, wname);
        getSignHandler().updateSigns(GameTeam.RED);
        getSignHandler().updateSigns(GameTeam.BLUE);
        getSignHandler().updateSigns(GameTeam.GREEN);
        getSignHandler().updateSigns(GameTeam.YELLOW);
    }

    public HashMap<Player, String> getPortalPlayers() {
        return portal;
    }

    public HashMap<String, Entity> getZombies() {
        return zombies;
    }

    public HashMap<Player, String> getLogoutPlayers() {
        return logoutPlayers;
    }

    public HashMap<String, String> getNpcPlayers() {
        return npcP;
    }

    public HashMap<Player, String> getJoiningPlayers() {
        return joining;
    }

    public PlayerMeta getPlayer(String name) {
        return PlayerMeta.getMeta(name);
    }
    
   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent event){
       Player player = event.getPlayer();
       for (Player p : Bukkit.getOnlinePlayers())
            player.showPlayer(p);
       
   }
   
@EventHandler
   public void onPlayerJoin1(PlayerJoinEvent event){
       Player player = event.getPlayer();
            player.setFlying(false);
       
   }
   @EventHandler
   public void PlayerQuitEvent(PlayerQuitEvent event) {
       Player player = event.getPlayer();
       for (Player p : Bukkit.getOnlinePlayers())
            player.showPlayer(p);
       
   }
   
@EventHandler
     public void PlayerQuitEvent1(PlayerQuitEvent event) {
       Player player = event.getPlayer();
            player.setFlying(false);
   }

public static ItemStack EQUIPOS() {
        ItemStack item = new ItemStack(Material.BEACON, 1);
        ItemMeta im = item.getItemMeta();
     
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&',  "&bSelector de Equipos &7(Click Derecho)"));
          List<String> lore = new ArrayList<String>();  
        lore.add(ChatColor.GRAY + "Elige un equipo y lucha!");
 
        im.setLore(lore);
        item.setItemMeta(im);
        return item;
        
    }

     @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            public void run() {
               p.getInventory().setItem(2, EQUIPOS());
                p.updateInventory();
            }
        }, 5L);
    } 
    
@EventHandler
public void onPlayerInteract(PlayerInteractEvent event) { 
    if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
        Player player = event.getPlayer();
        if(player.getInventory().getItemInMainHand() != null) {
            ItemStack iteminhand = player.getInventory().getItemInMainHand();
            if(iteminhand.hasItemMeta()){
                if(iteminhand.getItemMeta().hasDisplayName() && iteminhand.getItemMeta().hasLore()){
                    if(iteminhand.equals(EQUIPOS())) {
                        menu.show(player);
                    }
                }
            }
        }
    }
}

    
	  @EventHandler
	  public void AutoLapis(InventoryOpenEvent e) {
		  Inventory inv = e.getInventory();
		  if(inv.getType().equals(InventoryType.ENCHANTING)) {
			  
			  int lapilazuli = 351;
	        	 
	        	 inv.setItem(1, ItemStackBuilder.getint(lapilazuli, 64, (short) 4, ChatColor.BLUE + "UltraLapis", Arrays.asList("Lapislazuli para encantar")));
			  
		  } else {
			  
		  }
	  }
		  
	 @EventHandler
	  public void closeInventoryEvent(InventoryCloseEvent e) {
		 Inventory inv = e.getInventory();
		 if(inv.getType().equals(InventoryType.ENCHANTING)) {
			 
			 int lapilazuli = 351;
      	 
      	 ItemStack lapilaz = ItemStackBuilder.getint(lapilazuli, 64, (short) 4, ChatColor.BLUE + "UltraLapis", Arrays.asList("Lapislazuli para encantar"));
			 
			 inv.remove(lapilaz);
			 
		 } else {
			 
		 }
		 
	 }
	 
		 @EventHandler
		  public void enchantItemEvent(EnchantItemEvent e) {
       Inventory inv = e.getInventory();
       if(inv.getType().equals(InventoryType.ENCHANTING)) {
      	 
      	 int lapilazuli = 351;
      	 
      	 inv.setItem(1, ItemStackBuilder.getint(lapilazuli, 64, (short) 4, ChatColor.BLUE + "UltraLapis", Arrays.asList("Lapislazuli para encantar")));
      	 
       } else {
      	 
       }
		 }
       
       @EventHandler
       public void inventoryClickEvent(InventoryClickEvent e) {
      	 Inventory inv = e.getInventory();     	 
         
      	 if(inv.getType().equals(InventoryType.ENCHANTING) && e.getSlot() == 1) {
      		 
      		 e.setCancelled(true);
      		 
      	 } else {
      		 
      	 }
      	 
	 }
       public static Plugin getPlugin() {
    	   return plugin;
       }
       
       public void ListTeams(Player p) {
    	   int blue = GameTeam.BLUE.getPlayers().size();
    	   int red = GameTeam.RED.getPlayers().size();
    	   int yellow = GameTeam.YELLOW.getPlayers().size();
    	   int green = GameTeam.GREEN.getPlayers().size();
    	   p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9Equipo Azul: &r" + blue));
    	   p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cEquipo Rojo: &r" + red));
    	   p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Equipo Amarillo: &r" + yellow));
    	   p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2Equipo Verde: &r" + green ));
       }
       
}

    
    