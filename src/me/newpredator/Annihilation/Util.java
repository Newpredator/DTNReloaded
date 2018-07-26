package me.newpredator.Annihilation;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import me.newpredator.Annihilation.manager.VotingManager;
import me.newpredator.Annihilation.object.BlockObject;
import me.newpredator.Annihilation.object.GameTeam;
import me.newpredator.Annihilation.object.Kit;
import me.newpredator.Annihilation.object.PlayerMeta;
import me.newpredator.Annihilation.stats.StatsType;
import me.newpredator.Annihilation.stats.StatsUtil;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;

public class Util {
	
    public VotingManager voting;
    
    public enum ParticleEffects {

        HUGE_EXPLODE("hugeexplosion", 0), LARGE_EXPLODE("largeexplode", 1), FIREWORK_SPARK("fireworksSpark", 2), AIR_BUBBLE(
        "bubble", 3), SUSPEND("suspend", 4), DEPTH_SUSPEND("depthSuspend", 5), TOWN_AURA("townaura", 6), CRITICAL_HIT(
        "crit", 7), MAGIC_CRITICAL_HIT("magicCrit", 8), MOB_SPELL("mobSpell", 9), MOB_SPELL_AMBIENT(
        "mobSpellAmbient", 10), SPELL("spell", 11), INSTANT_SPELL("instantSpell", 12), BLUE_SPARKLE("witchMagic",
        13), NOTE_BLOCK("note", 14), ENDER("portal", 15), ENCHANTMENT_TABLE("enchantmenttable", 16), EXPLODE(
        "explode", 17), FIRE("flame", 18), LAVA_SPARK("lava", 19), FOOTSTEP("footstep", 20), SPLASH("splash", 21), LARGE_SMOKE(
        "largesmoke", 22), CLOUD("cloud", 23), REDSTONE_DUST("reddust", 24), SNOWBALL_HIT("snowballpoof", 25), DRIP_WATER(
        "dripWater", 26), DRIP_LAVA("dripLava", 27), SNOW_DIG("snowshovel", 28), SLIME("slime", 29), HEART("heart",
        30), ANGRY_VILLAGER("angryVillager", 31), GREEN_SPARKLE("happyVillager", 32), ICONCRACK("iconcrack", 33), TILECRACK(
        "tilecrack", 34);
        private String name;
        private int id;

        ParticleEffects(String name, int id) {
            this.name = name;
            this.id = id;
        }

        String getName() {
            return name;
        }

        int getId() {
            return id;
        }

        public static void sendToPlayer(ParticleEffects effect, Player player, Location location, float offsetX, float offsetY,
                float offsetZ, float speed, int count) {
            try {
                Object packet = createPacket(effect, location, offsetX, offsetY, offsetZ, speed, count);
                sendPacket(player, packet);
            } catch (Exception e) {
            }

        }

        public static void sendToLocation(ParticleEffects effect, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count) {
            try {
                Object packet = createPacket(effect, location, offsetX, offsetY, offsetZ, speed, count);
                for (Player player : Bukkit.getOnlinePlayers()) {
                    sendPacket(player, packet);
                }
            } catch (Exception e) {
            }
        }

        private static Object createPacket(ParticleEffects effect, Location location, float offsetX, float offsetY,
                float offsetZ, float speed, int count) throws Exception {
            if (count <= 0) {
                count = 1;
            }
            Class<?> packetClass = getCraftClass("PacketPlayOutWorldParticles");
            Object packet = packetClass.getConstructor(String.class, float.class, float.class, float.class, float.class,
                    float.class, float.class, float.class, int.class).newInstance(effect.name, (float) location.getX(),
                    (float) location.getY(), (float) location.getZ(), offsetX, offsetY, offsetZ, speed, count);
            return packet;
        }

        private static void sendPacket(Player p, Object packet) throws Exception {
            Object eplayer = getHandle(p);
            Field playerConnectionField = eplayer.getClass().getField("playerConnection");
            Object playerConnection = playerConnectionField.get(eplayer);
            for (Method m : playerConnection.getClass().getMethods()) {
                if (m.getName().equalsIgnoreCase("sendPacket")) {
                    m.invoke(playerConnection, packet);
                    return;
                }
            }
        }

        private static Object getHandle(Entity entity) {
            try {
                Method entity_getHandle = entity.getClass().getMethod("getHandle");
                Object nms_entity = entity_getHandle.invoke(entity);
                return nms_entity;
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {

                return null;
            }
        }

        private static Class<?> getCraftClass(String name) {
            String version = getVersion() + ".";
            String className = "net.minecraft.server." + version + name;
            Class<?> clazz = null;
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
            }
            return clazz;
        }

        private static String getVersion() {
            return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        }
    }

    public static Location parseLocation(World w, String in) {
        String[] params = in.split(",");
        for (String s : params) {
            s.replace("-0", "0");
        }
        if (params.length == 3 || params.length == 5) {
            double x = Double.parseDouble(params[0]);
            double y = Double.parseDouble(params[1]);
            double z = Double.parseDouble(params[2]);
            Location loc = new Location(w, x, y, z);
            if (params.length == 5) {
                loc.setYaw(Float.parseFloat(params[4]));
                loc.setPitch(Float.parseFloat(params[5]));
            }
            return loc;
        }
        return null;
    }

    public static void sendPlayerToGame(final Player player, Annihilation plugin) {
        final PlayerMeta meta = PlayerMeta.getMeta(player);
        if (meta.getTeam() != null) {
            meta.setAlive(true);
            player.teleport(meta.getTeam().getRandomSpawn());
                    meta.getKit().give(player, meta.getTeam());
                    player.setGameMode(GameMode.SURVIVAL);
                    player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                    player.setFoodLevel(20);
                    player.setSaturation(20F);
                    player.setExp(0.0F);
                    player.giveExp(0);
                

        }
    }

    public static boolean isEmptyColumn(Location loc) {
        boolean hasBlock = false;
        Location test = loc.clone();
        for (int y = 0; y < loc.getWorld().getMaxHeight(); y++) {
            test.setY(y);
            if (test.getBlock().getType() != Material.AIR) {
                hasBlock = true;
            }
        }
        return !hasBlock;
    }

    public static void showClassSelector(Player p) {
        int size = ((Kit.values().length + 8) / 9) * 9;
        PlayerMeta meta = PlayerMeta.getMeta(p);
        Kit kit1 = meta.getKit();
        Inventory inv = Bukkit.createInventory(p, size, "§5" + p.getName()+ " - " + kit1);
        for (Kit kit : Kit.values()) {
            ItemStack i = kit.getIcon().clone();
            ItemMeta im = i.getItemMeta();
            List<String> lore = im.getLore();
            if (kit.isOwnedBy(p)) {
                lore.add(ChatColor.GREEN + "œ Desbloqueada");
            } else {
                lore.add("§cœ Bloqueada §7Cuesta: " + String.valueOf(kit.getPrice()) + " §7Gemas");
                lore.add("§cœ §aActualmente Tienes: §7" + StatsUtil.getStat(StatsType.GEMAS, p.getUniqueId().toString()));
                
            }
            im.setLore(lore);
            i.setItemMeta(im);
            inv.addItem(i);
            
        	
        }
        p.openInventory(inv);
        }
    
    public static void showShopSelector(Player p) {
        int size = ((Kit.values().length + 8) / 9) * 9;
        Inventory inv = Bukkit.createInventory(p, size, "§2Compra Kits");
        for (Kit kit : Kit.values()) {
            ItemStack i = kit.getIcon().clone();
            ItemMeta im = i.getItemMeta();
            List<String> lore = im.getLore();
            if (kit.isOwnedBy(p)) {
                lore.add(ChatColor.GREEN + "œ Desbloqueada");
            } else {
                lore.add("§cœ Bloqueada §7Cuesta: " + String.valueOf(kit.getPrice()) + " §7Gemas");
             lore.add("§cœ §aActualmente Tienes: §7" + StatsUtil.getStat(StatsType.GEMAS, p.getUniqueId().toString()));
                
            }
            im.setLore(lore);
            i.setItemMeta(im);
            inv.addItem(i);
        }
        p.openInventory(inv);
    }
    public static void showMapSelector(Player p) {
    	Inventory inv = Bukkit.createInventory(p, 9, "§c§lVota Mapa");
    	HashMap<Integer, String> maps = Annihilation.getVotingManager().getMaps();
    	int slot = 0;
    	for(String name : maps.values()){
    		ItemStack book = new ItemStack(Material.BOOK);
    		ItemMeta bm = book.getItemMeta();
    		bm.setDisplayName(ChatColor.GREEN + name);
    		book.setItemMeta(bm);
    		inv.setItem(slot, book);
    		slot += 1;
    	}
    	p.openInventory(inv);
    }



    public static void spawnFirework(Location loc) {
        Random colour = new Random();

        Firework fw = loc.getWorld().spawn(loc, Firework.class);
        FireworkMeta fwMeta = fw.getFireworkMeta();

        Type fwType = Type.BALL_LARGE;

        int c1i = colour.nextInt(17) + 1;
        int c2i = colour.nextInt(17) + 1;

        Color c1 = getFWColor(c1i);
        Color c2 = getFWColor(c2i);

        FireworkEffect effect = FireworkEffect.builder().withFade(c2).withColor(c1).with(fwType).build();

        fwMeta.addEffect(effect);
        fwMeta.setPower(1);
        fw.setFireworkMeta(fwMeta);
    }

    public static void spawnFirework(Location loc, Color c1, Color c2) {
        Firework fw = loc.getWorld().spawn(loc, Firework.class);
        FireworkMeta fwMeta = fw.getFireworkMeta();

        Type fwType = Type.BALL_LARGE;

        FireworkEffect effect = FireworkEffect.builder().withFade(c2).withColor(c1).with(fwType).build();

        fwMeta.addEffect(effect);
        fwMeta.setPower(1);
        fw.setFireworkMeta(fwMeta);
    }

    public static Color getFWColor(int c) {
        switch (c) {
            case 1:
                return Color.TEAL;
            default:
            case 2:
                return Color.WHITE;
            case 3:
                return Color.YELLOW;
            case 4:
                return Color.AQUA;
            case 5:
                return Color.BLACK;
            case 6:
                return Color.BLUE;
            case 7:
                return Color.FUCHSIA;
            case 8:
                return Color.GRAY;
            case 9:
                return Color.GREEN;
            case 10:
                return Color.LIME;
            case 11:
                return Color.MAROON;
            case 12:
                return Color.NAVY;
            case 13:
                return Color.OLIVE;
            case 14:
                return Color.ORANGE;
            case 15:
                return Color.PURPLE;
            case 16:
                return Color.RED;
            case 17:
                return Color.SILVER;
        }
    }

    public static String getPhaseColor(int phase) {
        switch (phase) {
            case 1:
                return ChatColor.BLUE.toString();
            case 2:
                return ChatColor.GREEN.toString();
            case 3:
                return ChatColor.YELLOW.toString();
            case 4:
                return ChatColor.GOLD.toString();
            case 5:
                return ChatColor.RED.toString();
            default:
                return ChatColor.WHITE.toString();
        }
    }

    public static GameTeam whatTeamIsBiggerThan(GameTeam thisTeam) {
        int currTeamSize = thisTeam.getPlayers().size();
        if (currTeamSize != 0) {
            for (GameTeam t : GameTeam.teams()) {
                if (t != thisTeam) {
                    int teamSize = t.getPlayers().size();
                    if (teamSize > (currTeamSize + 3)) {
                        return t;
                    }
                }
            }
        }
        return thisTeam;
    }

    public static boolean getTeamAllowEnter(GameTeam t) {
        int blue = GameTeam.BLUE.getPlayers().size();
        int red = GameTeam.RED.getPlayers().size();
        int green = GameTeam.GREEN.getPlayers().size();
        int yellow = GameTeam.YELLOW.getPlayers().size();

        if (t == GameTeam.BLUE) {
            if (isBiggerThan(blue, red) || isBiggerThan(blue, green) || isBiggerThan(blue, yellow)) {
                return true;
            }
        } else if (t == GameTeam.RED) {
            if (isBiggerThan(red, blue) || isBiggerThan(red, green) || isBiggerThan(red, yellow)) {
                return true;
            }
        } else if (t == GameTeam.GREEN) {
            if (isBiggerThan(green, red) || isBiggerThan(green, blue) || isBiggerThan(green, yellow)) {
                return true;
            }
        } else if (t == GameTeam.YELLOW) {
            if (isBiggerThan(yellow, red) || isBiggerThan(yellow, green) || isBiggerThan(yellow, blue)) {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }

    public static boolean isBiggerThan(int i, int i2) {
        int iF = i2 + 3;
        if (i >= iF) {
            return true;
        }
        return false;
    }

	public static boolean tooClose(Location loc, Annihilation p) {
        double x = loc.getX();
        double z = loc.getZ();

        for (GameTeam team : GameTeam.teams()) {
            Location nexusLoc = team.getNexus().getLocation();
            double nX = nexusLoc.getX();
            double nZ = nexusLoc.getZ();
            if (Math.abs(nX - x) <= p.build
                    && Math.abs(nZ - z) <= p.build) {
                return true;
            }
        }
        return false;
    }

    public static void giveEffect(final Player player) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Annihilation.getInstance(), new Runnable() {
            @Override
            public void run() {
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 80));
            }
        }, 20L);
    }

    public static boolean playerPlayed(Player p) {
        String playerName = p.getName();
        File playerdataFile = new File("plugins/DTN/users/" + playerName + ".yml");
        if (playerdataFile.exists()) {
            return true;
        }
        return false;
    }

    public static String replaceTeamColor(String s) {
        s = s.replaceAll("(§([a-fk-or0-9]))", "");
        return s;
    }

    public static boolean getTeam(GameTeam team) {
        for (Map.Entry<Player, BlockObject> bo : Annihilation.getInstance().crafting.entrySet()) {
            PlayerMeta meta = PlayerMeta.getMeta(bo.getKey());
            if (meta.getTeam() == team) {
                return true;
            }
        }
        return false;
    }

    public static void playSounds(Player p) {
        for (int i = 0; i < 4; i++) {
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, i, i);
        }
    }

	public static boolean hasSignAttached(Block block) {
        for (BlockFace attachedOn : new BlockFace[]{BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.SELF}) {
            BlockState state = block.getRelative(attachedOn).getState();
            if (state instanceof Sign) {
                BlockFace attachedTo = ((org.bukkit.material.Sign) state.getData()).getAttachedFace();
                switch (attachedOn) {
                    case SELF:
                        return true;
                    case NORTH:
                        return attachedTo == BlockFace.SOUTH;
                    case SOUTH:
                        return attachedTo == BlockFace.NORTH;
                    case WEST:
                        return attachedTo == BlockFace.EAST;
                    case EAST:
                        return attachedTo == BlockFace.WEST;
                    case UP:
                        return attachedTo == BlockFace.DOWN;
				default:
					break;
                }
            }
        }
        return false;
    }

	public static boolean isShopSignAttached(Block block) {
        for (BlockFace attachedOn : new BlockFace[]{BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.SELF}) {
            BlockState state = block.getRelative(attachedOn).getState();
            if (state instanceof Sign) {
                BlockFace attachedTo = ((org.bukkit.material.Sign) state.getData()).getAttachedFace();
                switch (attachedOn) {
                    case SELF:
                        return true;
                    case NORTH:
                        if (attachedTo == BlockFace.SOUTH) {
                            Sign s = (Sign) state;
                            String st = s.getLine(0);
                            if (st.startsWith("[Shop]")) {
                                return true;
                            }
                        }
                    case SOUTH:
                        if (attachedTo == BlockFace.NORTH) {
                            Sign s = (Sign) state;
                            String st = s.getLine(0);
                            if (st.startsWith("[Shop]")) {
                                return true;
                            }
                        }
                    case WEST:
                        if (attachedTo == BlockFace.EAST) {
                            Sign s = (Sign) state;
                            String st = s.getLine(0);
                            if (st.startsWith("[Shop]")) {
                                return true;
                            }
                        }
                    case EAST:
                        if (attachedTo == BlockFace.WEST) {
                            Sign s = (Sign) state;
                            String st = s.getLine(0);
                            if (st.startsWith("[Shop]")) {
                                return true;
                            }
                        }
                    case UP:
                        if (attachedTo == BlockFace.DOWN) {
                            Sign s = (Sign) state;
                            String st = s.getLine(0);
                            if (st.startsWith("[Shop]")) {
                                return true;
                            }
                        }
				default:
					break;
                }
            }
        }
        return false;
    }

    public static LivingEntity getTargetEntity(Player player, Integer RANGE) {
        List<Entity> nearby = player.getNearbyEntities(RANGE, RANGE, RANGE);
        LivingEntity target = null;
        BlockIterator bit = new BlockIterator(player, RANGE);
        while (bit.hasNext()) {
            Block b = bit.next();
            for (Entity e : nearby) {
                if (e instanceof LivingEntity) {
                    if (nearBlock(b, (LivingEntity) e)) {
                        target = (LivingEntity) e;
                    }
                }
            }
        }
        return target;
    }

    public static boolean nearBlock(Block b, LivingEntity e) {
        Location bLoc = b.getLocation();
        Location eLoc = e.getLocation();
        double bx = bLoc.getX(), by = bLoc.getY(), bz = bLoc.getZ();
        double ex = eLoc.getX(), ey = eLoc.getY(), ez = eLoc.getZ();
        return Math.abs(bx - ex) < 0.5 && by - ey < e.getEyeHeight()
                && by - ey > -0.5 && Math.abs(bz - ez) < 0.5;
    }
    

    
    public static void giveClassSelector(Player player){
        ItemStack selector = new ItemStack(Material.FEATHER);
        ItemMeta itemMeta = selector.getItemMeta();
        itemMeta.setDisplayName("§aSelecciona Clase §7(Click Derecho)");
        selector.setItemMeta(itemMeta);
        player.getInventory().setItem(0, selector);
        player.updateInventory();
    }
    
	public static void giveMapSelector(Player player){
        ItemStack selector = new ItemStack(Material.BOOK);
        ItemMeta itemMeta = selector.getItemMeta();
        itemMeta.setDisplayName("§cVota Mapa §7(Click Derecho)");
        selector.setItemMeta(itemMeta);
        player.getInventory().setItem(1, selector);
        player.updateInventory();
    
}
	public static void giveShopSelector(Player player){
        ItemStack selector = new ItemStack(Material.GOLD_INGOT);
        ItemMeta itemMeta = selector.getItemMeta();
        itemMeta.setDisplayName("§dCompra kits §7(Click Derecho)");
        selector.setItemMeta(itemMeta);
        player.getInventory().setItem(7, selector);
        player.updateInventory();
    
}
	public static void giveleaveItem(Player player){
        ItemStack selector = new ItemStack(Material.FIREBALL);
        ItemMeta itemMeta = selector.getItemMeta();
        itemMeta.setDisplayName("§eSal §7(Click Derecho)");
        selector.setItemMeta(itemMeta);
        player.getInventory().setItem(8, selector);
        player.updateInventory();
    
}
}
