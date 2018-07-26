package me.newpredator.Annihilation.manager;

import java.util.HashMap;

import me.newpredator.Annihilation.object.GameTeam;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardManager {
    public Scoreboard sb;
    public Objective obj;
    
    public HashMap<String, Score> score = new HashMap<String, Score>();
    public HashMap<String, Team> team = new HashMap<String, Team>();
    
    public void update() {
        for (Player p : Bukkit.getOnlinePlayers())
        {
            try
            {
                p.setScoreboard(sb);
            }
            catch(IllegalStateException ex)
            {

            }
        }
    }
    
    public void resetScoreboard(String objName) {
        sb = null;
        obj = null;
        
        score.clear();
        team.clear();
        
        for (Player p : Bukkit.getOnlinePlayers()) 
            p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        
        sb = Bukkit.getScoreboardManager().getNewScoreboard();
        obj = sb.registerNewObjective("anni", "dummy");
        
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(objName);
        
        setTeam(GameTeam.RED);
        setTeam(GameTeam.BLUE);
        setTeam(GameTeam.GREEN);
        setTeam(GameTeam.YELLOW);
        setTeam(GameTeam.NONE);
        
    }
    
    public void setTeam(GameTeam t) {
    	
       
        team.put(t.name(), sb.registerNewTeam(t.name()));
        Team sbt = team.get(t.name());
        sbt.setAllowFriendlyFire(false);
        sbt.setCanSeeFriendlyInvisibles(true);
        sbt.setPrefix(t.color().toString());
        

        }
    }


    
