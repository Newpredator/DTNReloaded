package me.newpredator.Annihilation.chat;

import me.newpredator.Annihilation.Annihilation;
import me.newpredator.Annihilation.object.GameTeam;
import me.newpredator.Annihilation.object.PlayerMeta;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    private final Annihilation plugin;

    public ChatListener(Annihilation plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(final AsyncPlayerChatEvent e) {
        Player sender = e.getPlayer();
        PlayerMeta meta = PlayerMeta.getMeta(sender);
        GameTeam team = meta.getTeam();
        boolean isAll = false;
        boolean dead = !meta.isAlive() && plugin.getPhase() > 0;
        String msg = e.getMessage();

        if (e.getMessage().startsWith("!") && !e.getMessage().equalsIgnoreCase("!")) {
            isAll = true;
            msg = msg.substring(1);
        }

        if (team == GameTeam.NONE)
            isAll = true;

        if (isAll)
            ChatUtil.allMessage(team, sender, msg, dead);
        else
            ChatUtil.teamMessage(team, sender, msg, dead);

        e.setCancelled(true);
    }
}
