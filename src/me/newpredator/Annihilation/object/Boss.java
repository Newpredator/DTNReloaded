package me.newpredator.Annihilation.object;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import me.newpredator.Annihilation.Util;
import me.newpredator.Annihilation.manager.EffectsManager;
import me.newpredator.Annihilation.manager.PlayerSerializer;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Boss {

    private String configName;
    private int health;
    private String bossName;
    private Location spawn;
    private Location chest;
    private boolean alive;
	private int lootItems;
    private int ingots;

    public Boss(String configName, int health, String bossName, Location spawn,
            Location chest) {
        this.configName = configName;
        this.health = health;
        this.bossName = bossName;
        this.spawn = spawn;
        this.chest = chest;
        this.setAlive(false);
    }

    public void spawnLootChest() {
        chest.getBlock().setType(Material.CHEST);
        ItemStack randomItem = getRandomItem();
        Location l = chest.getBlock().getLocation();
        double y = l.getY() -1;
        Location lf = new Location(l.getWorld(), l.getBlockX(), y, l.getBlockZ());
        Util.spawnFirework(lf);
        for(int i = 0; i < 5; i++){
            EffectsManager.playEffectLoc(chest.getWorld(), Effect.ENDER_SIGNAL, chest.getBlock().getLocation());
        }
        Chest c = (Chest) chest.getBlock().getState();
        Inventory inv = c.getBlockInventory();
        Random r = new Random();
        inv.setItem(r.nextInt(inv.getSize()), randomItem);
        if (lootItems > inv.getSize() - 2) {
            lootItems = inv.getSize() - 2;
        }
        for (int i = 0; i < lootItems; i++) {
            int slot = r.nextInt(inv.getSize());
            if (isEmpty(inv, slot)) {
                inv.setItem(slot, randomItem);
            } else {
                i--;
            }
        }
        for (int i = 0; i < ingots; i++) {
            int slot = r.nextInt(inv.getSize());
            ItemStack stack = inv.getItem(slot);
            if (isEmpty(inv, slot)) {
                inv.setItem(slot, new ItemStack(Material.IRON_INGOT));
            } else if (stack.getType() == Material.IRON_INGOT) {
                inv.getItem(slot).setAmount(stack.getAmount() + 1);
            } else {
                i--;
            }
        }
    }
    
    public ItemStack getRandomItem() {
        int randomIndex = new Random().nextInt(rItems().size());
        return (ItemStack) rItems().toArray()[randomIndex];
    }

    public Collection<ItemStack> rItems() {
        Collection<ItemStack> items = new ArrayList<ItemStack>();
        ItemStack[] ritems = PlayerSerializer.BossLoot();
        for (ItemStack i : ritems) {
            items.add(i);
        }
        return items;
    }

    public String getConfigName() {
        return configName;
        
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getBossName() {
        return bossName;
    }

    public void setBossName(String bossName) {
        this.bossName = bossName;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public Location getChest() {
        return chest;
    }

    public void setChest(Location chest) {
        this.chest = chest;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

	private static boolean isEmpty(Inventory inv, int slot) {
        ItemStack stack = inv.getItem(slot);
        if (stack == null) {
            return true;
        }
        if (stack.getType() == Material.AIR) {
            return true;
        }
        return false;
    }
}
