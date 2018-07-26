package me.newpredator.Annihilation.listeners;

import me.newpredator.Annihilation.Annihilation;
import me.newpredator.Annihilation.object.Kit;
import me.newpredator.Annihilation.object.PlayerMeta;

import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

public class PvPA implements Listener {

    private Annihilation plugin;
    public List<Player> PvPPlayers;

	public PvPA(Annihilation pl) {
        plugin = pl;
        PvPPlayers = new ArrayList<Player>();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAtt(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            final Player att = (Player) event.getDamager();
            final Player def = (Player) event.getEntity();
            if (def.getWorld().getName().equals("lobby")) {
                event.setCancelled(true);
                return;
            }
            if (plugin.getPhase() < 1) {
                event.setCancelled(true);
                return;
            }
            if (getDistance(def, att) > 3) {
                event.setCancelled(true);
                return;
            }
            double damage = 0.0;
            if (PlayerMeta.getMeta(att).getKit() == Kit.GUERRERO) {
                ItemStack hand = att.getInventory().getItemInMainHand();
                if (hand != null) {
                    if (isSword(hand)) {
                        damage += 1.0;
                    }
                }
            }
            double dam = modDamage(att, damage);
            event.setDamage(damage + dam);
        }
    }

    private Double modDamage(Player att, double damage) {
        ItemStack attHand = att.getInventory().getItemInMainHand();
        if (!isSword(attHand)) {
            damage = 1.0;
            return damage;
        }
        damage = getSwordDamage(attHand);
        if (!attHand.getEnchantments().isEmpty()) {
            if (attHand.getEnchantments().containsKey(Enchantment.DAMAGE_ALL)) {
                int eLevel = attHand.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
                switch (eLevel) {
                    case 1:
                        damage += 0.5;
                    case 2:
                        damage += 1.0;
                    case 3:
                        damage += 1.5;
                    case 4:
                        damage += 2.0;
                    case 5:
                        damage += 3.0;
                }
            }
        }
        return damage;
    }

    private Boolean isSword(ItemStack i) {
        ItemStack S1 = new ItemStack(Material.WOOD_SWORD);
        ItemStack S2 = new ItemStack(Material.GOLD_SWORD);
        ItemStack S3 = new ItemStack(Material.STONE_SWORD);
        ItemStack S4 = new ItemStack(Material.IRON_SWORD);
        ItemStack S5 = new ItemStack(Material.DIAMOND_SWORD);
        if (i.isSimilar(S1) || i.isSimilar(S2) || i.isSimilar(S3) || i.isSimilar(S4) || i.isSimilar(S5)) {
            return true;
        }
        return false;
    }

    private Double getSwordDamage(ItemStack i) {
        ItemStack S1 = new ItemStack(Material.WOOD_SWORD);
        ItemStack S2 = new ItemStack(Material.GOLD_SWORD);
        ItemStack S3 = new ItemStack(Material.STONE_SWORD);
        ItemStack S4 = new ItemStack(Material.IRON_SWORD);
        ItemStack S5 = new ItemStack(Material.DIAMOND_SWORD);
        if (i.isSimilar(S1)) {
            return 2.0;
        }
        if (i.isSimilar(S2)) {
            return 3.0;
        }
        if (i.isSimilar(S3)) {
            return 4.0;
        }
        if (i.isSimilar(S4)) {
            return 5.0;
        }
        if (i.isSimilar(S5)) {
            return 6.0;
        }
        return 0.0;
    }

    public int getDistance(Player player, Player pl) {
        Player playersender = (Player) pl;

        int playerx = (int) player.getLocation().getX();
        int playery = (int) player.getLocation().getY();

        int senderx = (int) playersender.getLocation().getX();
        int sendery = (int) playersender.getLocation().getY();

        int finalx = playerx - senderx;
        int finaly = playery - sendery;
        if (finalx <= 0) {
            finalx = -finalx;
        }
        if (finaly <= 0) {
            finaly = -finaly;
        }
        int c = (int) Math.sqrt(Math.pow(finaly, 2) + Math.pow(finalx, 2));
        return c;
    }

    public double getDistanceDouble(Player player, Player pl) {
        Player playersender = (Player) pl;

        double playerx = (double) player.getLocation().getX();
        double playery = (double) player.getLocation().getY();

        double senderx = (double) playersender.getLocation().getX();
        double sendery = (double) playersender.getLocation().getY();

        double finalx = playerx - senderx;
        double finaly = playery - sendery;
        if (finalx <= 0) {
            finalx = -finalx;
        }
        if (finaly <= 0) {
            finaly = -finaly;
        }
        double c = (double) (Math.sqrt(Math.pow(finaly, 2) + Math.pow(finalx, 2)));
        return c;
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent event) {
        if (event.getBow() == null) {
            return;
        }
        Arrow a = (Arrow) event.getProjectile();
        Player player = (Player) a.getShooter();
        a.setVelocity(player.getLocation().getDirection().multiply(3));
    }
}
