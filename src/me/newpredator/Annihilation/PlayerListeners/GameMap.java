package me.newpredator.Annihilation.PlayerListeners;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import me.newpredator.Annihilation.maps.MapRollback;
import me.newpredator.Annihilation.maps.VoidGenerator;

public class GameMap {
	private World world;
	private MapRollback mapRollback;

	public GameMap(MapRollback mapRollback) {
		this.mapRollback = mapRollback;
	}

	public boolean loadIntoGame(String worldName) {
		mapRollback.loadMap(worldName);

		WorldCreator wc = new WorldCreator(worldName);
		wc.generator(new VoidGenerator());
		world = Bukkit.createWorld(wc);

		return true;
	}

	public String getName() {
		return world.getName();
	}

	public World getWorld() {
		return world;
	}
}
