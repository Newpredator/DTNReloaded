package me.newpredator.Annihilation.manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.newpredator.Annihilation.Annihilation;
import me.newpredator.Annihilation.object.GameTeam;
import me.newpredator.Annihilation.object.PlayerMeta;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerSerializer {

    private static Annihilation plugin;

    public PlayerSerializer(Annihilation pl) {
        PlayerSerializer.plugin = pl;
    }

    public static void PlayerToConfig(String playerName, ItemStack[] items, ItemStack[] armor, double health, float saturation, int level, int gm, int food, float exhaut, float exp, GameTeam team, Boolean bol, String wName) {
       try {
        File file;
        FileConfiguration config;
        file = new File("plugins/DTN/users/" + playerName + ".yml");
        config = YamlConfiguration.loadConfiguration(file);
        config.set("Name", playerName);
        config.set("Health", health);
        config.set("Food", food);
        config.set("Saturation", saturation);
        config.set("Exhaustion", exhaut);
        config.set("XP-Level", level);
        config.set("Exp", exp);
        config.set("GameMode", gm);
        String teamName = GameTeam.getName(team);
        config.set("Team", teamName);
        ItemStackToConfig(config, "Armor", armor);
        ItemStackToConfig(config, "Inventaire", items);
        config.set("killed", false);
        config.set("world", wName);
        config.save(file);
        } catch (IOException ex) {
        }
    }

    public static void RetorePlayer(Player p) {
        String playerName = p.getName();
        File f = new File("plugins/DTN/users/" + playerName + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(f);
        ConfigToPlayer(p, config);
        f.delete();
    }

    public static FileConfiguration getConfig(String playerName) {
        File file;
        FileConfiguration config;
        file = new File("plugins/DTN/users/" + playerName + ".yml");
        config = YamlConfiguration.loadConfiguration(file);
        return config;
    }

    public static void removeItems(String playerName) {
        try {
            File file;
            FileConfiguration config;
            file = new File("plugins/DTN/users/" + playerName + ".yml");
            config = YamlConfiguration.loadConfiguration(file);
            config.set("killed", true);
            config.save(file);
            Logger.getLogger("Minecraft").log(Level.INFO, playerName + " eliminando items del jugador");
        } catch (IOException ex) {
        }
    }

    public static void delete(String playerName) {
        File file;
        file = new File("plugins/DTN/users/" + playerName + ".yml");
        file.delete();
    }

    public static void save(String playerName) {
        File file;
        FileConfiguration config;
        file = new File("plugins/DTN/users/" + playerName + ".yml");
        config = YamlConfiguration.loadConfiguration(file);
        try {
            config.save(file);
        } catch (IOException ex) {

        }
    }

	@SuppressWarnings("deprecation")
	public static void ConfigToPlayer(final Player p, FileConfiguration config) {
        try {
        if ((!config.contains("Name")) || (!config.getString("Name").equals(p.getName()))) {
            return;
        }
        p.updateInventory();
            PlayerMeta meta = PlayerMeta.getMeta(p);
            GameTeam team = GameTeam.getTeam(config.getString("Team"));
            if (team == GameTeam.NONE && plugin.getPhase() > plugin.lastJoinPhase) {
                p.kickPlayer(ChatColor.RED + "Tu equipo es invalido.");
                return;
            }
            meta.setTeam(team);
            meta.setAlive(true);

            if (config.getBoolean("killed") == true) {
                if (plugin.getPhase() > plugin.lastJoinPhase) {
                    p.kickPlayer(ChatColor.RED + "Tu NPC ha sido asesinado.");
                    return;
                }
                return;
            }
            p.setHealth((int) config.getDouble("Health"));
            p.setFoodLevel(config.getInt("Food"));
            p.setSaturation((float) config.getDouble("Saturation"));
            p.setExhaustion((float) config.getDouble("Exhaustion"));
            p.setLevel(config.getInt("XP-Level"));
            p.setExp((float) config.getDouble("Exp"));
            p.setGameMode(GameMode.getByValue(config.getInt("GameMode")));
            p.getInventory().clear();
            p.getInventory().setArmorContents(ConfigToItemStack(config, "Armor"));
            p.getInventory().setContents(ConfigToItemStack(config, "Inventaire"));
            p.updateInventory();
        } catch (IllegalArgumentException localException1) {
        }
    }

    public static Collection<ItemStack> dropItem(String playerName) {
        ItemStack[] i;
        ItemStack[] a;
        a = ConfigToItemStack(getConfig(playerName), "Armor");
        i = ConfigToItemStack(getConfig(playerName), "Inventaire");
        Collection<ItemStack> items = new ArrayList<ItemStack>();
        items.addAll(Arrays.asList(a));
        items.addAll(Arrays.asList(i));

        return items;
    }

    public static ItemStack[] ConfigToItemStack(FileConfiguration config, String path) {
        int nb = config.getInt(path + ".Item-Nb");
        ItemStack[] item = new ItemStack[nb];
        for (int i = 0; i < nb; i++) {
            item[i] = config.getItemStack(path + ".Item" + i);
        }
        return item;
    }

    public static ItemStack[] BossLoot() {
        File file;
        FileConfiguration config;
        file = new File("plugins/DTN/config.yml");
        config = YamlConfiguration.loadConfiguration(file);
        int nb = config.getInt("Boss-loot.Item-Nb");
        ItemStack[] item = new ItemStack[nb];
        for (int i = 0; i < nb; i++) {
            item[i] = config.getItemStack("Boss-loot.Item" + i);
        }
        return item;
    }

    public static FileConfiguration ItemStackToConfig(FileConfiguration config, String path, ItemStack[] items) {
        if (config == null) {
            return null;
        }
        config.set(path + ".Item-Nb", Integer.valueOf(items.length));
        int i = 0;
        for (ItemStack it : items) {
            config.set(path + ".Item" + i, it);

            i++;
        }
        return config;
    }

    @SuppressWarnings({ "rawtypes", "deprecation" })
	public static String InventoryToString(Inventory invInventory) {
        String serialization = invInventory.getSize() + ";";
        for (int i = 0; i < invInventory.getSize(); i++) {
            ItemStack is = invInventory.getItem(i);
            if (is != null) {
                String serializedItemStack = new String();

                String isType = String.valueOf(is.getType().getId());
                serializedItemStack = serializedItemStack + "t@" + isType;
                if (is.getDurability() != 0) {
                    String isDurability = String.valueOf(is.getDurability());
                    serializedItemStack = serializedItemStack + ":d@" + isDurability;
                }
                if (is.getAmount() != 1) {
                    String isAmount = String.valueOf(is.getAmount());
                    serializedItemStack = serializedItemStack + ":a@" + isAmount;
                }
                Map isEnch = is.getEnchantments();
                Iterator it;
                if (isEnch.size() > 0) {
                    for (it = isEnch.entrySet().iterator(); it.hasNext();) {
                        Map.Entry ench = (Map.Entry) it.next();
                        serializedItemStack = serializedItemStack + ":e@" + ((Enchantment) ench.getKey()).getId() + "@" + ench.getValue();
                    }
                }
                serialization = serialization + i + "#" + serializedItemStack + ";";
            }
        }
        return serialization;
    }

    @SuppressWarnings("deprecation")
	public static Inventory StringToInventory(String invString) {
        String[] serializedBlocks = invString.split(";");
        String invInfo = serializedBlocks[0];
        Inventory deserializedInventory = Bukkit.getServer().createInventory(null, Integer.valueOf(invInfo).intValue());
        for (int i = 1; i < serializedBlocks.length; i++) {
            String[] serializedBlock = serializedBlocks[i].split("#");
            int stackPosition = Integer.valueOf(serializedBlock[0]).intValue();
            if (stackPosition < deserializedInventory.getSize()) {
                ItemStack is = null;
                Boolean createdItemStack = Boolean.valueOf(false);

                String[] serializedItemStack = serializedBlock[1].split(":");
                for (String itemInfo : serializedItemStack) {
                    String[] itemAttribute = itemInfo.split("@");
                    if (itemAttribute[0].equals("t")) {
                        is = new ItemStack(Material.getMaterial(Integer.valueOf(itemAttribute[1]).intValue()));
                        createdItemStack = Boolean.valueOf(true);
                    } else if ((itemAttribute[0].equals("d")) && (createdItemStack.booleanValue())) {
                        is.setDurability(Short.valueOf(itemAttribute[1]).shortValue());
                    } else if ((itemAttribute[0].equals("a")) && (createdItemStack.booleanValue())) {
                        is.setAmount(Integer.valueOf(itemAttribute[1]).intValue());
                    } else if ((itemAttribute[0].equals("e")) && (createdItemStack.booleanValue())) {
                        is.addEnchantment(Enchantment.getById(Integer.valueOf(itemAttribute[1]).intValue()), Integer.valueOf(itemAttribute[2]).intValue());
                    }
                }
                deserializedInventory.setItem(stackPosition, is);
            }
        }
        return deserializedInventory;
    }
    
    public static boolean isKilled(String name){
        if(PlayerSerializer.getConfig(name).getBoolean("killed") == true){
            return true;
        }
        return false;
    }
}
