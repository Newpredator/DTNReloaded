package me.newpredator.Annihilation.manager;

import java.util.ArrayList;
import java.util.HashMap;

import me.newpredator.Annihilation.Annihilation;
import me.newpredator.Annihilation.Util;
import me.newpredator.Annihilation.object.GameTeam;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;

public class SignManager {
    private Annihilation plugin;

    private HashMap<GameTeam, ArrayList<Location>> signs = new HashMap<GameTeam, ArrayList<Location>>();

    public SignManager(Annihilation instance) {
        this.plugin = instance;
    }

    public void loadSigns() {
        ConfigurationSection config = plugin.getConfigManager().getConfig(
                "mapas.yml");
        for (GameTeam team : GameTeam.teams()) {
            signs.put(team, new ArrayList<Location>());
            String name = team.name().toLowerCase();
            for (String l : config.getStringList("lobby.signs." + name)) {
                Location loc = Util.parseLocation(
                        Bukkit.getWorld("lobby"), l);
                if (loc != null)
                    addTeamSign(team, loc);
            }
        }
    }

    public void addTeamSign(GameTeam team, Location loc) {
        Block b = loc.getBlock();
        if (b == null)
            return;

        Material m = b.getType();
        if (m == Material.SIGN_POST || m == Material.WALL_SIGN) {
            signs.get(team).add(loc);
            updateSigns(team);
        }
    }

    public void updateSigns(GameTeam t) {
        if (t == GameTeam.NONE)
            return;

        for (Location l : signs.get(t)) {
            Block b = l.getBlock();
            if (b == null)
                return;

            Material m = b.getType();
            if (m == Material.SIGN_POST || m == Material.WALL_SIGN) {
                Sign s = (Sign) b.getState();
                s.setLine(1, " ");
                s.setLine(0, t.coloredName());
                s.setLine(2, (t.getPlayers().size() == 1 ? "Jugador: " : "Jugadores: ")
                        + t.getPlayers().size());
                if (t.getNexus() != null && plugin.getPhase() > 0)
                    s.setLine(3, "Nexus: "
                            + t.getNexus().getHealth());
                else
                    s.setLine(3, " ");
                s.update(true);
            }
        }
    }
}
