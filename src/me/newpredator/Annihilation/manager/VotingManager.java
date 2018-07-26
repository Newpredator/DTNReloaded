package me.newpredator.Annihilation.manager;

import java.util.HashMap;
import me.newpredator.Annihilation.Annihilation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class VotingManager {
    private final Annihilation plugin;
    private final HashMap<Integer, String> maps = new HashMap<Integer, String>();
    private final HashMap<String, String> votes = new HashMap<String, String>();
    private boolean running = false;

    public VotingManager(Annihilation plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings({ "deprecation", "static-access" })
	public void start() {
        maps.clear();
        votes.clear();
        int count = 0;
        int size = plugin.getMapManager().getRandomMaps().size();
        for (String map : plugin.getMapManager().getRandomMaps()) {
            count++;
            size--;
            maps.put(count, map);
            plugin.getScoreboardHandler().score.put(map, plugin
                    .getScoreboardHandler().obj.getScore(Bukkit
                    .getOfflinePlayer(map)));
            plugin.getScoreboardHandler().score.get(map).setScore(size);
            plugin.getScoreboardHandler().team.put(map,
                    plugin.getScoreboardHandler().sb.registerNewTeam(map));
            plugin.getScoreboardHandler().team.get(map).addPlayer(
                    Bukkit.getOfflinePlayer(map));
            plugin.getScoreboardHandler().team.get(map).setPrefix(
                    "§7» §b" + ChatColor.ITALIC + "");
            plugin.getScoreboardHandler().team.get(map).setSuffix(
                    ChatColor.DARK_GRAY + "  " + ChatColor.GRAY + "0" + " Votos");
        }

        running = true;

        plugin.getScoreboardHandler().update();
    }

    public boolean vote(CommandSender voter, String vote) {
        try {
            int val = Integer.parseInt(vote);

            if (maps.containsKey(val)) {
                vote = maps.get(val);
                for (String map : maps.values()) {
                    if (vote.equalsIgnoreCase(map)) {
                        votes.put(voter.getName(), map);
                        voter.sendMessage(ChatColor.GREEN + "Has votado por "
                                + ChatColor.WHITE + map);
                        updateScoreboard();
                        return true;
                    }
                }
            }
        } catch (Exception ex) {
            for (String map : maps.values()) {
                if (vote.equalsIgnoreCase(map)) {
                    votes.put(voter.getName(), map);
                    voter.sendMessage(ChatColor.GREEN + "Has votado por "
                            + ChatColor.WHITE + map);
                    updateScoreboard();
                    return true;
                }
            }
        }

        voter.sendMessage(vote + ChatColor.RED + " Ese mapa no es valido");
        return false;
    }

    public String getWinner() {
        String winner = null;
        Integer highest = -1;
        for (String map : maps.values()) {
            int totalVotes = countVotes(map);
            if (totalVotes > highest) {
                winner = map;
                highest = totalVotes;
            }
        }
        return winner;
    }

    public void end() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public HashMap<Integer, String> getMaps() {
        return maps;
    }

    private int countVotes(String map) {
        int total = 0;
        for (String vote : votes.values())
            if (vote.equals(map))
                total++;
        return total;
    }

    @SuppressWarnings("static-access")
	private void updateScoreboard() {
        for (String map : maps.values())
            plugin.getScoreboardHandler().team.get(map).setSuffix(
                    ChatColor.DARK_GRAY + "  " + ChatColor.GRAY + countVotes(map)
                             + " Voto" + (countVotes(map) == 1 ? "" : "s"));
        

    }
}
