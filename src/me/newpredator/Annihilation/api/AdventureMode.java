package me.newpredator.Annihilation.api;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class AdventureMode implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(PlayerInteractEvent e) {
		Player pl = e.getPlayer();
		Material p = e.getPlayer().getInventory().getItemInMainHand().getType();
		Block b = e.getClickedBlock();
		
		
		// General Pickaxe
		
				if(!(pl.getGameMode() == GameMode.CREATIVE) && e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
				
				if(b.getType().equals(Material.STONE) || b.getType().equals(Material.COBBLESTONE) || b.getType().equals(Material.MOSSY_COBBLESTONE) ||
						b.getType().equals(Material.CLAY_BRICK)  || b.getType().equals(Material.CLAY) || b.getType().equals(Material.NETHERRACK) ||
						b.getType().equals(Material.SANDSTONE) || b.getType().equals(Material.COAL_ORE) || b.getType().equals(Material.COAL_BLOCK)
						 || b.getType().equals(Material.BRICK) || b.getType().equals(Material.BRICK_STAIRS)
						|| b.getType().equals(Material.MONSTER_EGGS) || b.getType().equals(Material.COBBLE_WALL) || 
						b.getType().equals(Material.COBBLESTONE_STAIRS) || b.getType().equals(Material.BEACON) || b.getType().equals(Material.RAILS)
						|| b.getType().equals(Material.NETHER_BRICK_STAIRS) || b.getType().equals(Material.NETHER_BRICK) || b.getType().equals(Material.ENDER_STONE)
						|| b.getType().equals(Material.NETHER_FENCE) || b.getType().equals(Material.FURNACE)|| b.getType().equals(Material.IRON_DOOR) 
						|| b.getType().equals(Material.IRON_BLOCK) || b.getType().equals(Material.QUARTZ) || b.getType().equals(Material.QUARTZ_BLOCK) 
						|| b.getType().equals(Material.QUARTZ_STAIRS) || b.getType().equals(Material.QUARTZ_ORE) || b.getType().equals(Material.SANDSTONE_STAIRS)
								|| b.getType().equals(Material.BREWING_STAND) || b.getType().equals(Material.DISPENSER) || b.getType().equals(Material.DROPPER)
								|| b.getType().equals(Material.DIAMOND_ORE) || b.getType().equals(Material.NOTE_BLOCK) || b.getType().equals(Material.POWERED_RAIL)
								|| b.getType().equals(Material.PISTON_STICKY_BASE) || b.getType().equals(Material.PISTON_BASE) || b.getType().equals(Material.PISTON_MOVING_PIECE)
								|| b.getType().equals(Material.PISTON_EXTENSION) || b.getType().equals(Material.CAULDRON)
								|| b.getType().equals(Material.DETECTOR_RAIL) || b.getType().equals(Material.GLOWSTONE) || b.getType().equals(Material.ENCHANTMENT_TABLE)
								|| b.getType().equals(Material.REDSTONE_LAMP_ON) || b.getType().equals(Material.REDSTONE_LAMP_ON) || b.getType().equals(Material.REDSTONE_COMPARATOR_ON)
								|| b.getType().equals(Material.REDSTONE_COMPARATOR_OFF) 
								|| b.getType().equals(Material.IRON_FENCE) || b.getType().equals(Material.JUKEBOX)) {
					
					if(p.equals(Material.WOOD_PICKAXE) ||
					   p.equals(Material.STONE_PICKAXE) ||
					   p.equals(Material.IRON_PICKAXE) ||
					   p.equals(Material.DIAMOND_PICKAXE) ||
					   p.equals(Material.GOLD_PICKAXE)) {	
						
					} else {
						
						e.setCancelled(true);
						return;
					}
					
				}
				
				// General Axe
				
				if(b.getType().equals(Material.WOOD) || b.getType().equals(Material.BOOKSHELF) || b.getType().equals(Material.FENCE) 
						|| b.getType().equals(Material.FENCE_GATE) || b.getType().equals(Material.LOG)
						|| b.getType().equals(Material.LOG_2) || b.getType().equals(Material.WOOD_BUTTON) || b.getType().equals(Material.WOOD_DOOR)
						|| b.getType().equals(Material.WOOD_PLATE) || b.getType().equals(Material.WOOD_STAIRS) || b.getType().equals(Material.COCOA)
						|| b.getType().equals(Material.NETHER_FENCE) || b.getType().equals(Material.PUMPKIN) || b.getType().equals(Material.LADDER)
						|| b.getType().equals(Material.SIGN) || b.getType().equals(Material.SIGN_POST) || b.getType().equals(Material.WALL_SIGN)
						|| b.getType().equals(Material.TRAP_DOOR) || b.getType().equals(Material.FENCE) || b.getType().equals(Material.CHEST)
						|| b.getType().equals(Material.WORKBENCH) || b.getType().equals(Material.CACTUS) ||   b.getType().equals(Material.DARK_OAK_STAIRS)
						|| b.getType().equals(Material.JUNGLE_WOOD_STAIRS) || 
						 b.getType().equals(Material.BIRCH_WOOD_STAIRS) || 
						 b.getType().equals(Material.SPRUCE_WOOD_STAIRS)) {
					 
		 			if(p.equals(Material.WOOD_AXE) ||
							   p.equals(Material.STONE_AXE) ||
							   p.equals(Material.IRON_AXE) ||
							   p.equals(Material.DIAMOND_AXE) ||
							   p.equals(Material.GOLD_PICKAXE)
							   ) {
						
						
					} else {
						
						e.setCancelled(true);
						return;
					}
				}
				
				// General Shovel
				
				if(b.getType().equals(Material.GRAVEL) || b.getType().equals(Material.SAND) || b.getType().equals(Material.DIRT) || b.getType().equals(Material.GRASS)) {
					
					if(p.equals(Material.WOOD_SPADE) ||
							   p.equals(Material.STONE_SPADE) ||
							   p.equals(Material.IRON_SPADE) ||
							   p.equals(Material.DIAMOND_SPADE)||
							   p.equals(Material.GOLD_SPADE)) {
					
						
						
					} else {
						e.setCancelled(true);
						return;
					}
				}
				
				// General Sword
				
				if(b.getType().equals(Material.MELON_BLOCK) || b.getType().equals(Material.LEAVES) || b.getType().equals(Material.LEAVES_2)
						|| b.getType().equals(Material.MELON_STEM) || b.getType().equals(Material.WEB)) {
					
					if(p.equals(Material.WOOD_SWORD) ||
							   p.equals(Material.STONE_SWORD) ||
							   p.equals(Material.IRON_SWORD) ||
							   p.equals(Material.GOLD_SWORD) ||
							   p.equals(Material.DIAMOND_SWORD)) {
				} else {
					e.setCancelled(true);
					return;
				}
				
				
			}
				// General hoe
				
				if(b.getType().equals(Material.WHEAT) || b.getType().equals(Material.CARROT)) {
					
					if(p.equals(Material.SHEARS)) {
						
				} else {
					e.setCancelled(true);
					return;
				}
			}
				 
				// Shears
				
				if(b.getType().equals(Material.WOOL)) {
					
					if(p.equals(Material.SHEARS)) {
					
				} else {
					e.setCancelled(true);
					return;
				}

		}
				// Cbble Picaxe
				
			       if(b.getType().equals(Material.IRON_ORE)) {
						
					
						if(p.equals(Material.STONE_PICKAXE) || p.equals(Material.IRON_PICKAXE) || p.equals(Material.DIAMOND_PICKAXE)) {
							
						
					} else {
						e.setCancelled(true);
						return;
					}
			}
				
				// Iron and diamond Pickaxe
				
		       if(b.getType().equals(Material.GOLD_ORE) || b.getType().equals(Material.DIAMOND_ORE) || b.getType().equals(Material.REDSTONE_BLOCK)
		    		   || b.getType().equals(Material.REDSTONE_ORE) || b.getType().equals(Material.REDSTONE_WIRE) || b.getType().equals(Material.REDSTONE_BLOCK)
		    				   || b.getType().equals(Material.LAPIS_ORE) || b.getType().equals(Material.LAPIS_BLOCK) || b.getType().equals(Material.EMERALD)
		    				   || b.getType().equals(Material.EMERALD_BLOCK) || b.getType().equals(Material.EMERALD_ORE)) {
					
				
					if(p.equals(Material.IRON_PICKAXE) || p.equals(Material.DIAMOND_PICKAXE)) {
						
					
				} else {
					e.setCancelled(true);
					return;
				}
		}
				// Diamond Pickaxe
				
			       if(b.getType().equals(Material.OBSIDIAN)) {
						
						if(p.equals(Material.DIAMOND_PICKAXE)) {
						
					} else {
						e.setCancelled(true);
						return;
					}
			}
			    // ALL
			       if(b.getType().equals(Material.YELLOW_FLOWER) || b.getType().equals(Material.RED_ROSE) || b.getType().equals(Material.TORCH) || b.getType().equals(Material.GLASS)
			    		   || b.getType().equals(Material.STAINED_GLASS) || b.getType().equals(Material.STAINED_GLASS_PANE) || b.getType().equals(Material.PACKED_ICE)
			    		   || b.getType().equals(Material.PAINTING) || b.getType().equals(Material.RED_MUSHROOM) || b.getType().equals(Material.BROWN_MUSHROOM) || b.getType().equals(Material.TNT) 
			    		   || b.getType().equals(Material.REDSTONE) || b.getType().equals(Material.REDSTONE_TORCH_ON) || b.getType().equals(Material.REDSTONE_TORCH_OFF)
			    		   || b.getType().equals(Material.TORCH) || b.getType().equals(Material.DOUBLE_PLANT) || b.getType().equals(Material.BED_BLOCK)
			    		   || b.getType().equals(Material.SPONGE) || b.getType().equals(Material.DEAD_BUSH) || b.getType().equals(Material.LEVER) || b.getType().equals(Material.MINECART)
			    		   || b.getType().equals(Material.BOAT) || b.getType().equals(Material.WATER_LILY)) {
						
						if(p.equals(null) || !p.equals(null)) {
						
					} else {
						e.setCancelled(true);
						return;
					}
			}
				}
			}

		
	@EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak2(BlockBreakEvent e) {
		Player pl = e.getPlayer();
		Material p = e.getPlayer().getInventory().getItemInMainHand().getType();
		Block b = e.getBlock();
		
		
		// General Pickaxe
		
		if(!(pl.getGameMode() == GameMode.CREATIVE)) {
		
		if(b.getType().equals(Material.STONE) || b.getType().equals(Material.COBBLESTONE) || b.getType().equals(Material.MOSSY_COBBLESTONE) ||
				b.getType().equals(Material.CLAY_BRICK)  || b.getType().equals(Material.CLAY) || b.getType().equals(Material.NETHERRACK) ||
				b.getType().equals(Material.SANDSTONE) || b.getType().equals(Material.COAL_ORE) || b.getType().equals(Material.COAL_BLOCK)
				 || b.getType().equals(Material.BRICK) || b.getType().equals(Material.BRICK_STAIRS)
				|| b.getType().equals(Material.MONSTER_EGGS) || b.getType().equals(Material.COBBLE_WALL) || 
				b.getType().equals(Material.COBBLESTONE_STAIRS) || b.getType().equals(Material.BEACON) || b.getType().equals(Material.RAILS)
				|| b.getType().equals(Material.NETHER_BRICK_STAIRS) || b.getType().equals(Material.NETHER_BRICK) || b.getType().equals(Material.ENDER_STONE)
				|| b.getType().equals(Material.NETHER_FENCE) || b.getType().equals(Material.FURNACE)|| b.getType().equals(Material.IRON_DOOR) 
				|| b.getType().equals(Material.IRON_BLOCK) || b.getType().equals(Material.QUARTZ) || b.getType().equals(Material.QUARTZ_BLOCK) 
				|| b.getType().equals(Material.QUARTZ_STAIRS) || b.getType().equals(Material.QUARTZ_ORE) || b.getType().equals(Material.SANDSTONE_STAIRS)
						|| b.getType().equals(Material.BREWING_STAND) || b.getType().equals(Material.DISPENSER) || b.getType().equals(Material.DROPPER)
						|| b.getType().equals(Material.DIAMOND_ORE) || b.getType().equals(Material.NOTE_BLOCK) || b.getType().equals(Material.POWERED_RAIL)
						|| b.getType().equals(Material.PISTON_STICKY_BASE) || b.getType().equals(Material.PISTON_BASE) || b.getType().equals(Material.PISTON_MOVING_PIECE)
						|| b.getType().equals(Material.PISTON_EXTENSION) || b.getType().equals(Material.CAULDRON)
						|| b.getType().equals(Material.DETECTOR_RAIL) || b.getType().equals(Material.GLOWSTONE) || b.getType().equals(Material.ENCHANTMENT_TABLE)
						|| b.getType().equals(Material.REDSTONE_LAMP_ON) || b.getType().equals(Material.REDSTONE_LAMP_ON) || b.getType().equals(Material.REDSTONE_COMPARATOR_ON)
						|| b.getType().equals(Material.REDSTONE_COMPARATOR_OFF) 
						|| b.getType().equals(Material.IRON_FENCE) || b.getType().equals(Material.JUKEBOX)) {
			
			if(p.equals(Material.WOOD_PICKAXE) ||
			   p.equals(Material.STONE_PICKAXE) ||
			   p.equals(Material.IRON_PICKAXE) ||
			   p.equals(Material.DIAMOND_PICKAXE) ||
			   p.equals(Material.GOLD_PICKAXE)) {	
				
			} else {
				
				e.setCancelled(true);
				return;
			}
			
		}
		
		// General Axe
		
		if(b.getType().equals(Material.WOOD) || b.getType().equals(Material.BOOKSHELF) || b.getType().equals(Material.FENCE) 
				|| b.getType().equals(Material.FENCE_GATE) || b.getType().equals(Material.LOG)
				|| b.getType().equals(Material.LOG_2) || b.getType().equals(Material.WOOD_BUTTON) || b.getType().equals(Material.WOOD_DOOR)
				|| b.getType().equals(Material.WOOD_PLATE) || b.getType().equals(Material.WOOD_STAIRS) || b.getType().equals(Material.COCOA)
				|| b.getType().equals(Material.NETHER_FENCE) || b.getType().equals(Material.PUMPKIN) || b.getType().equals(Material.LADDER)
				|| b.getType().equals(Material.SIGN) || b.getType().equals(Material.SIGN_POST) || b.getType().equals(Material.WALL_SIGN)
				|| b.getType().equals(Material.TRAP_DOOR) || b.getType().equals(Material.FENCE) || b.getType().equals(Material.CHEST)
				|| b.getType().equals(Material.WORKBENCH) || b.getType().equals(Material.CACTUS) ||   b.getType().equals(Material.DARK_OAK_STAIRS)
				|| b.getType().equals(Material.JUNGLE_WOOD_STAIRS) || 
				 b.getType().equals(Material.BIRCH_WOOD_STAIRS) || 
				 b.getType().equals(Material.SPRUCE_WOOD_STAIRS)) {
			 
 			if(p.equals(Material.WOOD_AXE) ||
					   p.equals(Material.STONE_AXE) ||
					   p.equals(Material.IRON_AXE) ||
					   p.equals(Material.DIAMOND_AXE) ||
					   p.equals(Material.GOLD_PICKAXE)
					   ) {
				
				
			} else {
				
				e.setCancelled(true);
				return;
			}
		}
		
		// General Shovel
		
		if(b.getType().equals(Material.GRAVEL) || b.getType().equals(Material.SAND) || b.getType().equals(Material.DIRT) || b.getType().equals(Material.GRASS)) {
			
			if(p.equals(Material.WOOD_SPADE) ||
					   p.equals(Material.STONE_SPADE) ||
					   p.equals(Material.IRON_SPADE) ||
					   p.equals(Material.DIAMOND_SPADE)||
					   p.equals(Material.GOLD_SPADE)) {
			
				
				
			} else {
				e.setCancelled(true);
				return;
			}
		}
		
		// General Sword
		
		if(b.getType().equals(Material.MELON_BLOCK) || b.getType().equals(Material.LEAVES) || b.getType().equals(Material.LEAVES_2)
				|| b.getType().equals(Material.MELON_STEM) || b.getType().equals(Material.WEB)) {
			
			if(p.equals(Material.WOOD_SWORD) ||
					   p.equals(Material.STONE_SWORD) ||
					   p.equals(Material.IRON_SWORD) ||
					   p.equals(Material.GOLD_SWORD) ||
					   p.equals(Material.DIAMOND_SWORD)) {
		} else {
			e.setCancelled(true);
			return;
		}
		
		
	}
		// General hoe
		
		if(b.getType().equals(Material.WHEAT) || b.getType().equals(Material.CARROT)) {
			
			if(p.equals(Material.SHEARS)) {
				
		} else {
			e.setCancelled(true);
			return;
		}
	}
		 
		// Shears
		
		if(b.getType().equals(Material.WOOL)) {
			
			if(p.equals(Material.SHEARS)) {
			
		} else {
			e.setCancelled(true);
			return;
		}

}
		// Cbble Picaxe
		
	       if(b.getType().equals(Material.IRON_ORE)) {
				
			
				if(p.equals(Material.STONE_PICKAXE) || p.equals(Material.IRON_PICKAXE) || p.equals(Material.DIAMOND_PICKAXE)) {
					
				
			} else {
				e.setCancelled(true);
				return;
			}
	}
		
		// Iron and diamond Pickaxe
		
       if(b.getType().equals(Material.GOLD_ORE) || b.getType().equals(Material.DIAMOND_ORE) || b.getType().equals(Material.REDSTONE_BLOCK)
    		   || b.getType().equals(Material.REDSTONE_ORE) || b.getType().equals(Material.REDSTONE_WIRE) || b.getType().equals(Material.REDSTONE_BLOCK)
    				   || b.getType().equals(Material.LAPIS_ORE) || b.getType().equals(Material.LAPIS_BLOCK) || b.getType().equals(Material.EMERALD)
    				   || b.getType().equals(Material.EMERALD_BLOCK) || b.getType().equals(Material.EMERALD_ORE)) {
			
		
			if(p.equals(Material.IRON_PICKAXE) || p.equals(Material.DIAMOND_PICKAXE)) {
				
			
		} else {
			e.setCancelled(true);
			return;
		}
}
		// Diamond Pickaxe
		
	       if(b.getType().equals(Material.OBSIDIAN)) {
				
				if(p.equals(Material.DIAMOND_PICKAXE)) {
				
			} else {
				e.setCancelled(true);
				return;
			}
	}
	    // ALL
	       if(b.getType().equals(Material.YELLOW_FLOWER) || b.getType().equals(Material.RED_ROSE) || b.getType().equals(Material.TORCH) || b.getType().equals(Material.GLASS)
	    		   || b.getType().equals(Material.STAINED_GLASS) || b.getType().equals(Material.STAINED_GLASS_PANE) || b.getType().equals(Material.PACKED_ICE)
	    		   || b.getType().equals(Material.PAINTING) || b.getType().equals(Material.RED_MUSHROOM) || b.getType().equals(Material.BROWN_MUSHROOM) || b.getType().equals(Material.TNT) 
	    		   || b.getType().equals(Material.REDSTONE) || b.getType().equals(Material.REDSTONE_TORCH_ON) || b.getType().equals(Material.REDSTONE_TORCH_OFF)
	    		   || b.getType().equals(Material.TORCH) || b.getType().equals(Material.DOUBLE_PLANT) || b.getType().equals(Material.BED_BLOCK)
	    		   || b.getType().equals(Material.SPONGE) || b.getType().equals(Material.DEAD_BUSH) || b.getType().equals(Material.LEVER) || b.getType().equals(Material.MINECART)
	    		   || b.getType().equals(Material.BOAT) || b.getType().equals(Material.WATER_LILY)) {
				
				if(p.equals(null) || !p.equals(null)) {
				
			} else {
				e.setCancelled(true);
				return;
			}
	}
		}
	}

}