package me.newpredator.Annihilation.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import me.newpredator.Annihilation.Annihilation;
import me.newpredator.Annihilation.maps.GameMap;
import me.newpredator.Annihilation.maps.MapRollback;
import me.newpredator.Annihilation.maps.VoidGenerator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.Configuration;

public class MapManager {
	private final ArrayList<String> maps = new ArrayList<String>();
	private GameMap currentMap = null;
	private Location lobbySpawn;
	private MapRollback mapRollback;

	public MapManager(Annihilation plugin, MapRollback mapRollback, Configuration config) {
		this.mapRollback = mapRollback;
		for (String s : config.getKeys(false)) {
			if (!s.equalsIgnoreCase("lobby")) {
				maps.add(s);
			}
		}

		WorldCreator wc = new WorldCreator("lobby");
		wc.generator(new VoidGenerator());
		Bukkit.createWorld(wc);

		lobbySpawn = parseLocation(config.getString("lobby.spawn"));
	}

	private Location parseLocation(String in) {
		String[] params = in.split(",");
		if (params.length == 3 || params.length == 5) {
			double x = Double.parseDouble(params[0]);
			double y = Double.parseDouble(params[1]);
			double z = Double.parseDouble(params[2]);
			Location loc = new Location(Bukkit.getWorld("lobby"), x, y, z);
			if (params.length == 5) {
				loc.setYaw(Float.parseFloat(params[3]));
				loc.setPitch(Float.parseFloat(params[4]));
			}
			return loc;
		}
		return null;
	}

	public boolean selectMap(String mapName) {
		currentMap = new GameMap(mapRollback);
		return currentMap.loadIntoGame(mapName);
	}

	public boolean mapSelected() {
		return currentMap != null;
	}

	public GameMap getCurrentMap() {
		return currentMap;
	}

	public Location getAnnihilationSpawnPoint() {
		return lobbySpawn;
	}

	public List<String> getRandomMaps() {
		LinkedList<String> shuffledMaps = new LinkedList<String>(maps);
		Collections.shuffle(shuffledMaps);
		return shuffledMaps.subList(0, Math.min(4, shuffledMaps.size()));
	}

	public void reset() {
		currentMap = null;
	}
}