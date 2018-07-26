package me.newpredator.Annihilation.object;
 
import java.util.ArrayList;
import java.util.List;

import me.newpredator.Annihilation.listeners.SoulboundListener;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
 
@SuppressWarnings("deprecation")
public enum Kit {
    ALDEANO(Material.WORKBENCH , 0, null) {
                {
                    spawnItems.add(new ItemStack(Material.WOOD_SWORD));
                    spawnItems.add(new ItemStack(Material.WOOD_PICKAXE));
                    spawnItems.add(new ItemStack(Material.WOOD_AXE));
                    spawnItems.add(new ItemStack(Material.WORKBENCH));
                    lore.add(ChatColor.YELLOW + "Eres un Soldado");
                    lore.add("§b ");
                    lore.add("§b§oEste kit te da las herramientas");
                    lore.add("§b§onecesarias y basicas");
                    lore.add("§b§opara poder sobrevivir");
                    lore.add("§b§olas primeras partidas.");
                    lore.add("§b§o ");
                    lore.add(ChatColor.RED + "Clase Gratis");
                    lore.add("§b§o ");
                   
                    
                }
            },
    GUERRERO(Material.STONE_SWORD, 5000, "guardian") {
                {
                    spawnItems.add(new ItemStack(Material.STONE_SWORD));
                    spawnItems.add(new ItemStack(Material.WOOD_PICKAXE));
                    spawnItems.add(new ItemStack(Material.WOOD_AXE));
                    spawnItems.add(new Potion(PotionType.INSTANT_HEAL, 1)
                            .toItemStack(1));
                    spawnItems.get(0).addEnchantment(Enchantment.KNOCKBACK, 1);
                    lore.add("§eEres un Guerrero");
                    lore.add("§b ");
                    lore.add("§bHaces de daño extra");
                    lore.add("§bNadie te va a derribar");
                    lore.add("§bTendras una espada muy buena");
                    lore.add("§b§oy una pocion extra de vida.");
                    lore.add(ChatColor.RED + "Clase Gratis disponible a partir de rango " + ChatColor.AQUA.toString() + ChatColor.BOLD + getVip().toUpperCase() +  ChatColor.RED.toString() + ChatColor.BOLD +  " VIP");
                    lore.add("§b§o ");
                }
            },
    ARQUERO(Material.BOW, 5000, "guardian") {
                {
                    spawnItems.add(new ItemStack(Material.WOOD_SWORD));
                    spawnItems.add(new ItemStack(Material.BOW));
                    spawnItems.add(new ItemStack(Material.WOOD_PICKAXE));
                    spawnItems.add(new ItemStack(Material.WOOD_AXE));
                    spawnItems.add(new ItemStack(Material.WOOD_SPADE));
                    spawnItems.add(new Potion(PotionType.INSTANT_HEAL, 1)
                            .toItemStack(1));
                    spawnItems.add(new ItemStack(Material.ARROW, 16));
                    spawnItems.get(1).addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
                    lore.add("§eEres un Arquero!");
                    lore.add("§b ");
                    lore.add("§b§oHace daño extra (x1)");
                    lore.add("§b§ocon tu arco y flechas");
                    lore.add("§b§oespeciales, Empiezas:");
                    lore.add("§b§ocon un arco encantado");
                    lore.add("§b§oy una poción de regeneración.");
                    lore.add("§b§o ");
                    lore.add(ChatColor.RED + "Clase Gratis disponible a partir de rango " + ChatColor.AQUA.toString() + ChatColor.BOLD + getVip().toUpperCase() +ChatColor.RED.toString() + ChatColor.BOLD +  " VIP");
                    lore.add("§b§o ");
                }
            },
    MINERO(Material.STONE_PICKAXE, 7000, "guardian") {
                {
                    spawnItems.add(new ItemStack(Material.WOOD_SWORD));
                    spawnItems.add(new ItemStack(Material.STONE_PICKAXE));
                    spawnItems.add(new ItemStack(Material.WOOD_AXE));
                    spawnItems.add(new ItemStack(Material.FURNACE));
                    spawnItems.get(1).addEnchantment(Enchantment.DIG_SPEED, 1);
                    lore.add("§eEres un Minero!");
                    lore.add("§b ");
                    lore.add("§b§oEl pico especial te permitira");
                    lore.add("§b§oobtener el doble de");
                    lore.add("§b§o minerales para poder armar");
                    lore.add("§b§oa tu equipo rapidamente!");
                    lore.add("§b§o ");
                    lore.add(ChatColor.RED + "Clase Gratis disponible a partir de rango " + ChatColor.AQUA.toString() + ChatColor.BOLD + getVip().toUpperCase() +ChatColor.RED.toString() + ChatColor.BOLD +  " VIP");
                    lore.add("§b§o ");
                }
            },
    TALADOR(Material.STONE_AXE, 1000, "guardian") {
                {
                    spawnItems.add(new ItemStack(Material.WOOD_SWORD));
                    spawnItems.add(new ItemStack(Material.WOOD_PICKAXE));
                    spawnItems.add(new ItemStack(Material.STONE_AXE));
                    spawnItems.get(2).addEnchantment(Enchantment.DIG_SPEED, 1);
                    lore.add("§eEres un Leñador!");
                    lore.add("§b ");
                    lore.add("§bEmpiezas con una hacha encantada");
                    lore.add("§bpara talar los arboles");
                    lore.add("§bmás rapido que los demas!");
                    lore.add("§b§o ");
                    lore.add(ChatColor.RED + "Clase Gratis disponible a partir de rango " +ChatColor.AQUA.toString() + ChatColor.BOLD + getVip().toUpperCase() + ChatColor.RED.toString() + ChatColor.BOLD +  " VIP");
                    lore.add("§b§o ");
                }
            },
         
            VAMPIRO(Material.REDSTONE, 22000, "guardian") {
               {
                   spawnItems.add(new ItemStack(Material.GOLD_SWORD));
                   spawnItems.add(new ItemStack(Material.WOOD_PICKAXE));
                   spawnItems.add(new ItemStack(Material.WOOD_AXE));
                   spawnItems.add(new ItemStack(Material.BREAD, 3));
                   
                    ItemMeta meta = spawnItems.get(1).getItemMeta();
                    meta.setDisplayName("§cColmillo");
                         spawnItems.get(1).setItemMeta(meta);
                    lore.add("§eEres un Vampiro!");
                    lore.add("§b ");
                    lore.add("§eDe noche te conviertes en vampiro");
                    lore.add(" ");
                    lore.add("Cada golpe que das a tus enemigos");
                    lore.add("tu te llevas su vida así un tal %");
                    lore.add("hasta chupar toda la vida de tu enemigo!");
                    lore.add("§b§o ");
                    lore.add(ChatColor.RED + "Clase Gratis disponible a partir de rango " +ChatColor.AQUA.toString() + ChatColor.BOLD + getVip().toUpperCase()  + ChatColor.RED.toString() + ChatColor.BOLD +  " VIP");
                    lore.add("§b§o ");
               }
           },
                  PIROMANO(Material.FIRE, 22000, "heroe") {
                {
                    spawnItems.add(new ItemStack(Material.STONE_SWORD));
                    spawnItems.add(new ItemStack(Material.STONE_PICKAXE));
                    spawnItems.add(new ItemStack(Material.STONE_AXE));
                    spawnItems.add(new ItemStack(Material.STONE_SPADE));
                    lore.add("§eEres un Piromano!");
                    lore.add("§b ");
                    lore.add("§b§oEl fuego y la lava no");
                    lore.add("§b§ote hacen daño debido a que");
                    lore.add("§b§oeres inmune pero tus enemigos no.");
                    lore.add("§b ");
                    lore.add("§b§oCada golpe que des haces arder");
                    lore.add("§b§oa tus enemigos cercados. ");
                    lore.add("§b§o ");
                    lore.add(ChatColor.RED + "Clase Gratis disponible a partir de rango " +ChatColor.AQUA.toString() + ChatColor.BOLD + getVip().toUpperCase()  + ChatColor.RED.toString() + ChatColor.BOLD +  " VIP");
                    lore.add("§b§o ");
                }      
                },
    EXPLORADOR(Material.FISHING_ROD, 30000, "heroe") {
                {
                    spawnItems.add(new ItemStack(Material.GOLD_SWORD));
                    spawnItems.add(new ItemStack(Material.FISHING_ROD));
                    spawnItems.add(new ItemStack(Material.WOOD_PICKAXE));
                    spawnItems.add(new ItemStack(Material.WOOD_AXE));
                    ItemMeta meta = spawnItems.get(1).getItemMeta();
                    meta.setDisplayName("Gancho");
                    spawnItems.get(1).setItemMeta(meta);
                    lore.add("§eEres un Trepador!");
                    lore.add("§b ");
                    lore.add("§b§oLa caña sera la magia! ");
                    lore.add("§b§ocon una caña podras ");
                    lore.add("§b§oir dando saltos y escalar ");
                    lore.add("§b§opor el mapa y huir de los ");
                    lore.add("§b§oenemigos que te siguen. ");
                    lore.add("§b§o ");
                    lore.add(ChatColor.RED + "Clase Gratis disponible a partir de rango " +ChatColor.AQUA.toString() + ChatColor.BOLD + getVip().toUpperCase()  + ChatColor.RED.toString() + ChatColor.BOLD +  " VIP");
                    lore.add("§b§o ");
                }
            },
            CONSTRUCTOR(Material.BRICK, 10000, "heroe") {
                {
                    spawnItems.add(new ItemStack(Material.WOOD_SWORD));
                    spawnItems.add(new ItemStack(Material.WOOD_PICKAXE));
                    spawnItems.add(new ItemStack(Material.WOOD_AXE));
                    spawnItems.add(new ItemStack(Material.WOOD_SPADE));
                    spawnItems.add(new ItemStack(Material.CHEST));
                    ItemMeta meta = spawnItems.get(4).getItemMeta();
                    meta.setDisplayName("Materiales");
                    spawnItems.get(4).setItemMeta(meta);
                    lore.add("§eEres el constructor!");
                    lore.add("§b ");
                    lore.add("§b§oSaca materiales de la nada! ");
                    lore.add("§b§oPodras denfender ");
                    lore.add("§b§oConstuye muros altos ");
                    lore.add("§b§oy estructuras para guardar ");
                    lore.add("§b§otu nexo. ");
                    lore.add("§b§o ");
                    lore.add(ChatColor.RED + "Clase Gratis disponible a partir de rango " +ChatColor.AQUA.toString() + ChatColor.BOLD + getVip().toUpperCase()  + ChatColor.RED.toString() + ChatColor.BOLD +  " VIP");
                    lore.add("§b§o ");
                }
            },
    FRENETICO(Material.CHAINMAIL_CHESTPLATE, 30000, "superheroe") {
                {
                    spawnItems.add(new ItemStack(Material.STONE_SWORD));
                    spawnItems.add(new ItemStack(Material.WOOD_PICKAXE));
                    spawnItems.add(new ItemStack(Material.WOOD_AXE));
                    spawnItems.add(new Potion(PotionType.INSTANT_HEAL, 1)
                            .toItemStack(1));
                    lore.add("§eEres un Guerrero!");
                    lore.add("§b ");
                    lore.add("§b§oAñades corazones");
                    lore.add("§b§ocuando asesinas");
                    lore.add("§b§onadie te derribara ");
                    lore.add("§b§otendras una buena espada");
                    lore.add("§b§ode piedra y una pocion para");
                    lore.add("§b§omatar antes a los enemigos! ");
                    lore.add("§b§o ");
                    lore.add(ChatColor.RED + "Clase Gratis disponible a partir de rango " +ChatColor.AQUA.toString() + ChatColor.BOLD + getVip().toUpperCase()  + ChatColor.RED.toString() + ChatColor.BOLD +  " VIP");
                    lore.add("§b§o ");
                }
            },
            THOR(Material.GOLD_AXE, 25000, "superheroe") {
                {
                    spawnItems.add(new ItemStack(Material.STONE_SWORD));
                    spawnItems.add(new ItemStack(Material.GOLD_PICKAXE));
                    spawnItems.add(new ItemStack(Material.APPLE, 3));
                    spawnItems.add(new ItemStack(Material.GOLD_AXE));
                    ItemMeta meta = spawnItems.get(3).getItemMeta();
                    meta.setDisplayName("§c§lMartillo §7(Click Al Enemigo)");
                    spawnItems.get(3).setItemMeta(meta);
                    lore.add("§eEres un Dios!");
                    lore.add("§b ");
                    lore.add("§b§oUna vez cada 2 minutos tu ");
                    lore.add("§b§opuedes desatar una area ");
                    lore.add("§b§ocon colpes de rayos hacia ");
                    lore.add("§b§olos enemigos y quemarlos! ");
                    lore.add("§b§o ");
                    lore.add(ChatColor.RED + "Clase Gratis disponible a partir de rango " +ChatColor.AQUA.toString() + ChatColor.BOLD + getVip().toUpperCase()  + ChatColor.RED.toString() + ChatColor.BOLD +  " VIP");
                    lore.add("§b§o ");
                }
             },
                TRANSPORTADOR(Material.QUARTZ, 20000, "superheroe") {
                {
                    spawnItems.add(new ItemStack(Material.STONE_SWORD));
                    spawnItems.add(new ItemStack(Material.WOOD_PICKAXE));
                    spawnItems.add(new ItemStack(Material.WOOD_AXE));
                    spawnItems.add(new ItemStack(Material.WORKBENCH));
                    ItemStack portal = new ItemStack(Material.QUARTZ);
                    ItemMeta m = portal.getItemMeta();
                    m.setDisplayName(ChatColor.DARK_PURPLE + "Portal");
                    portal.setItemMeta(m);
                    spawnItems.add(portal);
                    lore.add("§eEres un Teletransporte!");
                    lore.add("§b ");
                    lore.add("§b§oUne dos partes del mapa ");
                    lore.add("§b§ocon portales que puedes ");
                    lore.add("§b§ocrear para que tu ");
                    lore.add("§b§oequipo podais hacer ");
                    lore.add("§b§oTP hacia otro Nexo. ");
                    lore.add("§b§o ");
                    lore.add(ChatColor.RED + "Clase Gratis disponible a partir de rango " +ChatColor.AQUA.toString() + ChatColor.BOLD + getVip().toUpperCase()  + ChatColor.RED.toString() + ChatColor.BOLD +  " VIP");
                    lore.add("§b§o ");
 
                }
            },
               
                ACROBATA(Material.FEATHER, 20000, "superheroe") {
                {
                    spawnItems.add(new ItemStack(Material.WOOD_SWORD));
                    spawnItems.add(new ItemStack(Material.WOOD_PICKAXE));
                    spawnItems.add(new ItemStack(Material.WOOD_AXE));
                    spawnItems.add(new ItemStack(Material.BOW));
                    spawnItems.add(new ItemStack(Material.ARROW, 6));
                    lore.add("§eEres un Saltador!");
                    lore.add("§b ");
                    lore.add("§b§oAtaca a tu enemigos");
                    lore.add("§b§odesde el cielo volando");
                    lore.add("§b§o ");
                    lore.add("§b§oApareces con un arco");
                    lore.add("§b§oy 6 flechas no te ");
                    lore.add("§b§oharas daño por la caida!");
                    lore.add("");
                    lore.add(ChatColor.RED + "Clase Gratis disponible a partir de rango " +ChatColor.AQUA.toString() + ChatColor.BOLD + getVip().toUpperCase()  + ChatColor.RED.toString() + ChatColor.BOLD +  " VIP");
                    lore.add("§b§o ");
 
                }              
                },
                  WITHER(Material.CHEST, 20000, "superheroe") {
                {
                    spawnItems.add(new ItemStack(Material.GOLD_SWORD));
                    spawnItems.add(new ItemStack(Material.STONE_PICKAXE));
                    spawnItems.add(new ItemStack(Material.STONE_AXE));
                    lore.add("§eEres un Wither!");
                    lore.add("§b ");
                    lore.add("§b§oAl pegar a un enemigo");
                    lore.add("§b§oinfringes más daño");
                    lore.add("§b§oal oponente y pones efecto");
                    lore.add("§b§odel wither y ocultas corazones.");
                    lore.add("§b§o ");
                    lore.add(ChatColor.RED + "Clase Gratis disponible a partir de rango " +ChatColor.AQUA.toString() + ChatColor.BOLD + getVip().toUpperCase()  + ChatColor.RED.toString() + ChatColor.BOLD +  " VIP");
                    lore.add("§b§o ");
 
                }
                },
                CAPATAZ(Material.IRON_HELMET, 10000, "guardian") {
                {
                    spawnItems.add(new ItemStack(Material.STONE_SWORD));
                    spawnItems.add(new ItemStack(Material.STONE_PICKAXE));
                    spawnItems.add(new ItemStack(Material.STONE_AXE));
                    spawnItems.add(new ItemStack(Material.STONE_SPADE));
                    lore.add("§eEres un Capataz!");
                    lore.add(" ");
                    lore.add("§b§oEste kit te da");
                    lore.add("§b§olas herramientas ");
                    lore.add("§b§omejoradas y necesarias ");
                    lore.add("§b§opara sobrevivir!");
                    lore.add("§b§o ");
                    lore.add("§b§oEmpiezas con herramientas de piedra:"
                            + " Hacha, pico, espada y pala. ");
                    lore.add("");
                    lore.add(ChatColor.RED + "Clase Gratis disponible a partir de rango " +ChatColor.AQUA.toString() + ChatColor.BOLD + getVip().toUpperCase()  + ChatColor.RED.toString() + ChatColor.BOLD +  " VIP");
                    lore.add("§b§o ");
 
                }
                },
            ASESINO(Material.PAPER, 32000, "superheroe") { 
                {
                    spawnItems.add(new ItemStack(Material.WOOD_SWORD));
                    spawnItems.add(new ItemStack(Material.WOOD_PICKAXE));
                    spawnItems.add(new ItemStack(Material.WOOD_AXE));
                    spawnItems.add(new ItemStack(Material.PAPER, 1));
                    ItemMeta meta = spawnItems.get(3).getItemMeta();
                    meta.setDisplayName("§e Activar - Efectos");
                    spawnItems.get(3).setItemMeta(meta);
                    lore.add("§eEres un Asesino!");
                    lore.add(" ");
                    lore.add("§b§oCon este kit podras ");
                    lore.add("§b§oentrar a la base enemiga ");
                    lore.add("§b§osin ser visto por los enemigos ");
                    lore.add("§b§opuedes activar los efectos de");
                    lore.add("§b§ovelocidad , salto, invisibilidad");
                    lore.add("§b§ocada 50 segundos con el azucar!");
                    lore.add("§b§o ");
                    lore.add(ChatColor.RED + "Clase Gratis disponible a partir de rango " +ChatColor.AQUA.toString() + ChatColor.BOLD + getVip().toUpperCase()  + ChatColor.RED.toString() + ChatColor.BOLD +  " VIP");
                    lore.add("§b§o ");
 
                }
                },
            RAPIDO(Material.SUGAR, 20000, "superheroe") { 
                {
                    spawnItems.add(new ItemStack(Material.WOOD_SWORD));
                    spawnItems.add(new ItemStack(Material.WOOD_PICKAXE));
                    spawnItems.add(new ItemStack(Material.WOOD_AXE));
                    spawnItems.add(new ItemStack(Material.SUGAR, 1));
                    ItemMeta meta = spawnItems.get(3).getItemMeta();
                    meta.setDisplayName("§2 Incrementa Velocidad");
                    spawnItems.get(3).setItemMeta(meta);
                    lore.add("§eEres un Corredor!");
                    lore.add(" ");
                    lore.add("§b§oCon este kit podras ");
                    lore.add("§b§oCorrer cuando etes rodeado ");
                    lore.add("§b§ode los enemigos así no te ");
                    lore.add("§b§opodran seguir/pillar y matarte");
                    lore.add("§b§oPodras incrementare tu velocidad");
                    lore.add("§b§odurante 4 segundos, tiempo de regarga: 20s.");
                    lore.add("§b§o ");
                    lore.add(ChatColor.RED + "Clase Gratis disponible a partir de rango " +ChatColor.AQUA.toString() + ChatColor.BOLD + getVip().toUpperCase()  + ChatColor.RED.toString() + ChatColor.BOLD +  " VIP");
                    lore.add("§b§o ");
 
                }
                },
                ENCANTADOR(Material.EXP_BOTTLE, 30000, "heroe") { 
                {
                    spawnItems.add(new ItemStack(Material.WOOD_AXE));
                    spawnItems.add(new ItemStack(Material.WOOD_PICKAXE));
                    spawnItems.add(new ItemStack(Material.GOLD_SWORD));
                    lore.add("§eEres un Encantador!");
                    lore.add(" ");
                    lore.add("§b§oCon este kit al picar");
                    lore.add("§b§orecibes la xp más    ");
                    lore.add("§b§orapido así ­ podras entar ");
                    lore.add("§b§olas cosas más rapido. ");
                    lore.add("§b§o ");
                    lore.add(ChatColor.RED + "Clase Gratis disponible a partir de rango " +ChatColor.AQUA.toString() + ChatColor.BOLD + getVip().toUpperCase()  + ChatColor.RED.toString() + ChatColor.BOLD +  " VIP");
                    lore.add("§b§o ");
 
                }
                },
                ESPIA(Material.POTION, 35000, "heroe") {
                    {
                        spawnItems.add(new ItemStack(Material.STONE_SWORD));
                        spawnItems.add(new ItemStack(Material.STONE_PICKAXE));
                        spawnItems.add(new ItemStack(Material.STONE_AXE));
                        lore.add("Tu eres espia");
                        lore.add("");
                        lore.add("Haz Click Shift");
                        lore.add("Y consigue Invisibilidad 10 segundos!");
                        lore.add("§b§o ");
                        lore.add(ChatColor.RED + "Clase Gratis disponible a partir de rango " +ChatColor.AQUA.toString() + ChatColor.BOLD + getVip().toUpperCase()  + ChatColor.RED.toString() + ChatColor.BOLD +  " VIP");
                        lore.add("§b§o ");
                    }
                },
                DEFENSOR(Material.IRON_CHESTPLATE, 12000, "guardian") {
                    {
                        spawnItems.add(new ItemStack(Material.STONE_SWORD));
                        spawnItems.add(new ItemStack(Material.STONE_PICKAXE));
                        spawnItems.add(new ItemStack(Material.STONE_AXE));
                        lore.add("Tu eres el defensor");
                        lore.add("");
                        lore.add("Te hacen menos daño");
                        lore.add("¡Defiende con tu vida!");
                        lore.add("§b§o ");
                        lore.add(ChatColor.RED + "Clase Gratis disponible a partir de rango " +ChatColor.AQUA.toString() + ChatColor.BOLD + getVip().toUpperCase()  + ChatColor.RED.toString() + ChatColor.BOLD +  " VIP");
                        lore.add("§b§o ");

                    }
                };
    static {
        for (Kit kit : values()) {
            kit.init();
        }
    }
     private int price;
     private String vip;
    final ItemStack i = new ItemStack(Material.POTION, 1, (short)0, (byte)8238);
    private ItemStack icon;
    List<String> lore = new ArrayList<String>();
    List<ItemStack> spawnItems = new ArrayList<ItemStack>();
    ItemStack[] spawnArmor = new ItemStack[]{
        new ItemStack(Material.LEATHER_BOOTS),
        new ItemStack(Material.LEATHER_LEGGINGS),
        new ItemStack(Material.LEATHER_CHESTPLATE),
        new ItemStack(Material.LEATHER_HELMET)};
 
    Kit(Material m, int Price, String Vip) {
        icon = new ItemStack(m);
        if(m == Material.POTION){
            icon = i;
        }
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName(getName());
        icon.setItemMeta(meta);
        price = Price;
        vip = Vip;
        
        }
    
    
 
    private void init() {
        for (int i = 0; i < lore.size(); i++) {
            String s = lore.get(i);
            s = ChatColor.AQUA + s;
            lore.set(i, s);
        }
        	 ItemMeta meta = icon.getItemMeta();
        meta.setLore(lore);
        icon.setItemMeta(meta);
    
    }
    
 
    public static Kit getKit(String name) {
        for (Kit type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
 
    public void give(Player recipient, GameTeam team) {
        PlayerInventory inv = recipient.getInventory();
        inv.clear();
 
        for (ItemStack item : spawnItems) {
            ItemStack toGive = item.clone();
            SoulboundListener.soulbind(toGive);
            inv.addItem(toGive);
        }
 
        recipient.removePotionEffect(PotionEffectType.SPEED);
 
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta compassMeta = compass.getItemMeta();
        compassMeta.setDisplayName(team.color() + "Punto Del "
                + team.toString() + " Nexus");
        compass.setItemMeta(compassMeta);
        SoulboundListener.soulbind(compass);
 
        inv.addItem(compass);
 
        inv.setArmorContents(spawnArmor);
        colorizeArmor(inv, getTeamColor(team));
        recipient.updateInventory();
         
 
        for (ItemStack armor : inv.getArmorContents()) {
            SoulboundListener.soulbind(armor);
        }
 
        if (this == EXPLORADOR) {
            addScoutParticles(recipient);
        }
 
        if (this == FRENETICO) {
            recipient.setMaxHealth(14.0);
        } else {
            recipient.setMaxHealth(20.0);
        }
    }
 
    private Color getTeamColor(GameTeam team) {
        switch (team) {
            case RED:
                return Color.RED;
            case YELLOW:
                return Color.YELLOW;
            case GREEN:
                return Color.GREEN;
            case BLUE:
                return Color.BLUE;
            default:
                return Color.WHITE;
        }
    }
 
    private void colorizeArmor(PlayerInventory inv, Color color) {
        for (ItemStack item : inv.getArmorContents()) {
            if (item.getItemMeta() instanceof LeatherArmorMeta) {
                LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
                meta.setColor(color);
                item.setItemMeta(meta);
            }
        }
    }
 
    public String getName() {
        return name().substring(0, 1) + name().substring(1).toLowerCase();
    }
 
    public boolean isOwnedBy(Player p) {
        return p.isOp()
                || this == ALDEANO
                || p.hasPermission("anni.kit." + getName().toLowerCase())
                || p.hasPermission("vip." + getVip().toLowerCase());
    }
 
    public void addScoutParticles(Player p) {
        if (this != ALDEANO) {
            return;
        }
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
                Integer.MAX_VALUE, 0, true), true);
    }
 
    public ItemStack getIcon() {
        return icon;
    }
 
    public static boolean isItem(ItemStack stack, String name) {
        if (stack == null) {
            return false;
        }
        ItemMeta meta = stack.getItemMeta();
        if (meta == null) {
            return false;
        }
        if (!meta.hasDisplayName()) {
            return false;
        }
        return meta.getDisplayName().equalsIgnoreCase(name);
    }
    
    public int getPrice() {
    	return price;
    }
    public String getVip() {
    	return vip;
    }

}

