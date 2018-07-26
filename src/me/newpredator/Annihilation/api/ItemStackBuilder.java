package me.newpredator.Annihilation.api;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemStackBuilder {

		
		@SuppressWarnings("unchecked")
		public static ItemStack get(Material material, int amount, short datavalue, String displayName, @SuppressWarnings("rawtypes") List lore){
			ItemStack item = new ItemStack(material, amount, datavalue);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
			itemmeta.setLore(lore);
			item.setItemMeta(itemmeta);
			return item;
		}
		
		public static ItemStack getint(int material, int amount, short datavalue, String displayName, List<String> lore){
			@SuppressWarnings("deprecation")
			ItemStack item = new ItemStack(material, amount, datavalue);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
			itemmeta.setLore(lore);
			item.setItemMeta(itemmeta);
			return item;

		}
		
			 @SuppressWarnings("deprecation")
			public static ItemStack crearNormal(int id, int amount, int data)
			  {
			    ItemStack item = new ItemStack(id, amount, (short)data);
			    return item;
			  }
			  
			  @SuppressWarnings("deprecation")
			public static ItemStack crearItem(int id, int amount, int data, String name)
			  {
			    ItemStack item = new ItemStack(id, amount, (short)data);
			    ItemMeta meta = item.getItemMeta();

			    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
			    item.setItemMeta(meta);
			    return item;
			  }
			  
			  
			  @SuppressWarnings("deprecation")
			public static ItemStack crearItem1(int id, int amount, int data, String name, String... lore)
			  {
			    ItemStack item = new ItemStack(id, amount, (short)data);
			    
			    ItemMeta meta = item.getItemMeta();
			    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
			    ArrayList<String> color = new ArrayList<String>();
			    for(String b: lore){
			    	color.add(ChatColor.translateAlternateColorCodes('&', b));
			    meta.setLore(color);
			    }
			    item.setItemMeta(meta);
			    return item;
			  }
			  
			  @SuppressWarnings("deprecation")
			public static ItemStack crearItem2(int id, int amount, int data, String name, ArrayList<String> lore)
			  {
			    ItemStack item = new ItemStack(id, amount, (short)data);
			    ItemMeta meta = item.getItemMeta();
			    meta.setDisplayName(name);
			    ArrayList<String> color = new ArrayList<String>();
			    for(String b: lore){
			    	color.add(ChatColor.translateAlternateColorCodes('&', b));
			    meta.setLore(color);
			    }
			    item.setItemMeta(meta);
			    return item;
			  }

			  @SuppressWarnings("deprecation")
			public static ItemStack crearItem2(int id, int amount, int data, String name, String[] strings)
			  {
			    ItemStack item = new ItemStack(id, amount, (short)data);
			    ItemMeta meta = item.getItemMeta();
			    meta.setDisplayName(name);
			    ArrayList<String> color = new ArrayList<String>();
			    for(String b: strings){
			    	color.add(ChatColor.translateAlternateColorCodes('&', b));
			    meta.setLore(color);
			    }
			    item.setItemMeta(meta);
			    return item;
			  }
			 
			  @SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
			public static ItemStack toKit(String paramString)
			  {
			    String[] arrayOfString = paramString.split(",");
			    ArrayList localArrayList = new ArrayList();
			    ItemStack localItemStack = new ItemStack(Integer.parseInt(arrayOfString[0]), Integer.parseInt(arrayOfString[1]), Short.parseShort(arrayOfString[2]));
			    for (int i = 1; i < arrayOfString.length; i++)
			    {
			      ItemMeta localItemMeta;
			      if (arrayOfString[i].startsWith("lore:"))
			      {
			        localItemMeta = localItemStack.getItemMeta();
			        String str1 = arrayOfString[i].replace("lore:", "");
			        String str3 = ChatColor.translateAlternateColorCodes('&', str1);
			        localArrayList.add(str3);localItemMeta.setLore(localArrayList);localItemStack.setItemMeta(localItemMeta);
			      }
			      for (Enchantment s : Enchantment.values()) {
			        if (arrayOfString[i].toUpperCase().startsWith(s.getName().toUpperCase()))
			        {
			          String str4 = arrayOfString[i].replace(s.getName().toUpperCase() + ":", "");
			          localItemStack.addUnsafeEnchantment(s, Integer.parseInt(str4));
			        }
			      }
			      if (arrayOfString[i].startsWith("name:"))
			      {
			        localItemMeta = localItemStack.getItemMeta();
			        String str2 = arrayOfString[i].replace("name:", "");
			        localItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', str2));
			        localItemStack.setItemMeta(localItemMeta);
			      }
			    }
			    return localItemStack;
			  }
			    public static ItemStack parseItem(List<String> item) {
			        if (item.size() < 2) {
			            return null;
			        }

			        ItemStack itemStack = null;

			        try {
			        	if (item.get(0).contains(":")) {
			        		Material material = Material.getMaterial(item.get(0).split(":")[0].toUpperCase());
			        		int amount = Integer.parseInt(item.get(1));
			        		if (amount < 1) {
			        			return null;
			        		}
			        		short data = (short) Integer.parseInt(item.get(0).split(":")[1].toUpperCase());
			        		itemStack = new ItemStack(material, amount, data);
			        	} else {
			                itemStack = new ItemStack(Material.getMaterial(item.get(0).toUpperCase()), Integer.parseInt(item.get(1)));
			        	}

			        	if (item.size() > 2) {
			            	for (int x = 2; x < item.size(); x++) {
			            		if (item.get(x).split(":")[0].equalsIgnoreCase("name")) {
			            			ItemMeta itemMeta = itemStack.getItemMeta();
			            	        itemMeta.setDisplayName(item.get(x).split(":")[1]);
			            	        itemStack.setItemMeta(itemMeta);
			            		} else {
			                        itemStack.addUnsafeEnchantment(getEnchant(item.get(x).split(":")[0]), Integer.parseInt(item.get(x).split(":")[1]));
			            		}
			            	}

			            }

			        } catch (Exception ignored) {

			        }
			        return itemStack;
			    }
			    
			    public static PotionEffect parseEffect(List<String> effect) {
			        if (effect.size() < 2) {
			            return null;
			        }

			        PotionEffect potionEffect = null;

			        try {
			        	int length;
			        	int level;
			        	PotionEffectType pType = getPotionType(effect.get(0));
			           	if (Integer.parseInt(effect.get(1)) == -1) {
			        	   length = Integer.MAX_VALUE;
			           	} else {
			        	   length = 20 * Integer.parseInt(effect.get(1));
			           	}
			           	level = Integer.parseInt(effect.get(2));
			           	
			           	potionEffect = new PotionEffect(pType, length, level);

			        } catch (Exception ignored) {

			        }
			        return potionEffect;
			    }

			    
			    private static PotionEffectType getPotionType(String type) {
			    	switch (type.toLowerCase()) {
			    	case "speed": return PotionEffectType.SPEED;
			    	case "slowness": return PotionEffectType.SLOW;
			    	case "haste": return PotionEffectType.FAST_DIGGING;
			    	case "miningfatique": return PotionEffectType.SLOW_DIGGING;
			    	case "strength": return PotionEffectType.INCREASE_DAMAGE;
			    	case "instanthealth": return PotionEffectType.HEAL;
			    	case "instantdamage": return PotionEffectType.HARM;
			    	case "jumpboost": return PotionEffectType.JUMP;
			    	case "nausea": return PotionEffectType.CONFUSION;
			    	case "regeneration": return PotionEffectType.REGENERATION;
			    	case "resistance": return PotionEffectType.DAMAGE_RESISTANCE;
			    	case "fireresistance": return PotionEffectType.FIRE_RESISTANCE;
			    	case "waterbreathing": return PotionEffectType.WATER_BREATHING;
			    	case "invisibility": return PotionEffectType.INVISIBILITY;
			    	case "blindness": return PotionEffectType.BLINDNESS;
			    	case "nightvision": return PotionEffectType.NIGHT_VISION;
			    	case "hunger": return PotionEffectType.HUNGER;
			    	case "weakness": return PotionEffectType.WEAKNESS;
			    	case "poison": return PotionEffectType.POISON;
			    	case "wither": return PotionEffectType.WITHER;
			    	case "healthboost": return PotionEffectType.HEALTH_BOOST;
			    	case "absorption": return PotionEffectType.ABSORPTION;
			    	case "saturation": return PotionEffectType.SATURATION;
			    	default: return null;	
			    	}
			    }

			    private static Enchantment getEnchant(String enchant) {
			    	switch (enchant.toLowerCase()) {
			    	case "protection": return Enchantment.PROTECTION_ENVIRONMENTAL;
			    	case "projectileprotection": return Enchantment.PROTECTION_PROJECTILE;
			    	case "fireprotection": return Enchantment.PROTECTION_FIRE;
			    	case "featherfall": return Enchantment.PROTECTION_FALL;
			    	case "blastprotection": return Enchantment.PROTECTION_EXPLOSIONS;
			    	case "respiration": return Enchantment.OXYGEN;
			    	case "aquaaffinity": return Enchantment.WATER_WORKER;
			    	case "sharpness": return Enchantment.DAMAGE_ALL;
			    	case "smite": return Enchantment.DAMAGE_UNDEAD;
			    	case "baneofarthropods": return Enchantment.DAMAGE_ARTHROPODS;
			    	case "knockback": return Enchantment.KNOCKBACK;
			    	case "fireaspect": return Enchantment.FIRE_ASPECT;
			    	case "depthstrider": return Enchantment.DEPTH_STRIDER;
			    	case "looting": return Enchantment.LOOT_BONUS_MOBS;
			    	case "power": return Enchantment.ARROW_DAMAGE;
			    	case "punch": return Enchantment.ARROW_KNOCKBACK;
			    	case "flame": return Enchantment.ARROW_FIRE;
			    	case "infinity": return Enchantment.ARROW_INFINITE;
			    	case "efficiency": return Enchantment.DIG_SPEED;
			    	case "silktouch": return Enchantment.SILK_TOUCH;
			    	case "unbreaking": return Enchantment.DURABILITY;
			    	case "fortune": return Enchantment.LOOT_BONUS_BLOCKS;
			    	case "luckofthesea": return Enchantment.LUCK;
			    	case "luck": return Enchantment.LUCK;
			    	case "lure": return Enchantment.LURE;
			    	case "thorns": return Enchantment.THORNS;
			    	default: return null;	
			    	}
			    }
			    
			    public static boolean isEnchanted(ItemStack itemStack) {
			    	if (itemStack.containsEnchantment(Enchantment.ARROW_DAMAGE)) {
			    		return true;
			    	} else if (itemStack.containsEnchantment(Enchantment.ARROW_DAMAGE)) {
			    		return true;
			    	}  else if (itemStack.containsEnchantment(Enchantment.ARROW_FIRE)) {
			    		return true;
			    	}  else if (itemStack.containsEnchantment(Enchantment.ARROW_INFINITE)) {
			    		return true;
			    	}  else if (itemStack.containsEnchantment(Enchantment.ARROW_KNOCKBACK)) {
			    		return true;
			    	}  else if (itemStack.containsEnchantment(Enchantment.DAMAGE_ALL)) {
			    		return true;
			    	}  else if (itemStack.containsEnchantment(Enchantment.DAMAGE_ARTHROPODS)) {
			    		return true;
			    	}  else if (itemStack.containsEnchantment(Enchantment.DAMAGE_UNDEAD)) {
			    		return true;
			    	}  else if (itemStack.containsEnchantment(Enchantment.DIG_SPEED)) {
			    		return true;
			    	}  else if (itemStack.containsEnchantment(Enchantment.DURABILITY)) {
			    		return true;
			    	}  else if (itemStack.containsEnchantment(Enchantment.FIRE_ASPECT)) {
			    		return true;
			    	}  else if (itemStack.containsEnchantment(Enchantment.KNOCKBACK)) {
			    		return true;
			    	}  else if (itemStack.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
			    		return true;
			    	}  else if (itemStack.containsEnchantment(Enchantment.LOOT_BONUS_MOBS)) {
			    		return true;
			    	}  else if (itemStack.containsEnchantment(Enchantment.LUCK)) {
			    		return true;
			    	}  else if (itemStack.containsEnchantment(Enchantment.LURE)) {
			    		return true;
			    	}  else if (itemStack.containsEnchantment(Enchantment.OXYGEN)) {
			    		return true;
			    	} else if (itemStack.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) {
			    		return true;
			    	} else if (itemStack.containsEnchantment(Enchantment.PROTECTION_EXPLOSIONS)) {
			    		return true;
			    	} else if (itemStack.containsEnchantment(Enchantment.PROTECTION_FALL)) {
			    		return true;
			    	} else if (itemStack.containsEnchantment(Enchantment.PROTECTION_FIRE)) {
			    		return true;
			    	} else if (itemStack.containsEnchantment(Enchantment.PROTECTION_PROJECTILE)) {
			    		return true;
			    	} else if (itemStack.containsEnchantment(Enchantment.SILK_TOUCH)) {
			    		return true;
			    	} else if (itemStack.containsEnchantment(Enchantment.THORNS)) {
			    		return true;
			    	} else if (itemStack.containsEnchantment(Enchantment.WATER_WORKER)) {
			    		return true;
			    	} 
			    	return false;
			    }
			    
		}

