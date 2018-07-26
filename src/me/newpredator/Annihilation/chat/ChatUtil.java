package me.newpredator.Annihilation.chat;

import me.newpredator.Annihilation.Annihilation;
import me.newpredator.Annihilation.api.TitleApi;
import me.newpredator.Annihilation.manager.PhaseManager;
import me.newpredator.Annihilation.object.Boss;
import me.newpredator.Annihilation.object.GameTeam;
import me.newpredator.Annihilation.object.Kit;
import me.newpredator.Annihilation.object.PlayerMeta;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ChatUtil {

    private static final String DARK_GRAY = ChatColor.DARK_GRAY.toString();
    private static final String DARK_PURPLE = ChatColor.DARK_PURPLE.toString();
    private static final String DARK_RED = ChatColor.DARK_RED.toString();
    private static final String GRAY = ChatColor.GRAY.toString();
    private static final String RED = ChatColor.RED.toString();
	private static final String GOLD = ChatColor.GOLD.toString();
	private static final String WHITE = ChatColor.WHITE.toString();
    private static final String AQUA = ChatColor.AQUA.toString();
    private static final String BOLD = ChatColor.BOLD.toString();
	private final Annihilation plugin;

    public ChatUtil(Annihilation pl) {
        plugin = pl;
        plugin.configManager.getConfig("config.yml");
    }

    public static void setRoman(boolean b) {
    }

    public static void allMessage(GameTeam team, Player sender, String message,
            boolean dead) {

        String group = null;
        String username;
        
        PermissionUser user = PermissionsEx.getUser(sender.getName());
        String rank = user.getPrefix();
        if (!rank.equals(""))
        group += " " + rank.replaceAll("&", "�");
        
        if (team == GameTeam.NONE) {
            group = GRAY + "(All) " + GRAY + "[" + DARK_PURPLE + "Annihilation" + GRAY + "]" + rank.replaceAll("&", "�");
            username = sender.getName();
        } else {
            group = GRAY + "(All) " + GRAY + "[" + team.coloredName() + GRAY + "]" + rank.replaceAll("&", "�");
            username = sender.getName();
            if (dead) {
                group = DARK_GRAY + "[" + DARK_RED + "MUERTO" + DARK_GRAY + " "
                        + group + rank.replaceAll("&", "�");
            }
        }
        String msg = message;
        
        String toSend = group + " �r" + username + DARK_GRAY + ": " + WHITE + msg;
        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(toSend);
    }

    public static void teamMessage(GameTeam team, Player sender,
            String message, boolean dead) {
        String group;
        if (team == GameTeam.NONE) {
            allMessage(team, sender, message, false);
            return;
        } else {
            group = GRAY + "(Equipo) " + GRAY + "[" + team.coloredName() + GRAY + "]";
            if (dead) {
                group = DARK_GRAY + "[" + DARK_RED + "MUERTO" + DARK_GRAY + "]" + group;
            }
        }
        PermissionUser user = PermissionsEx.getUser(sender.getName());
        String rank = user.getPrefix();
        if (!rank.equals(""))
            group += " " + rank.replaceAll("&", "�");
        String toSend = group + " �r" + sender.getName() + ChatColor.DARK_GRAY + ": " + ChatColor.GOLD + message;
        for (Player player : team.getPlayers())
            player.sendMessage(toSend);
    }

	public static void broadcast(String message) {
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                message));
    }

    public static void nexusDestroyed(GameTeam attacker, GameTeam victim,
            Player p) {
            broadcast(GRAY + "��������������������");
            broadcast(GRAY + "����" + victim.color().toString() + "����������" + GRAY + "������");
            broadcast(GRAY + "����" + victim.color().toString() + "��" + GRAY + "��������" + victim.color().toString() + "��" + GRAY + "����");
            broadcast(GRAY + "����" + victim.color().toString() + "��" + GRAY + "��������" + victim.color().toString() + "��" + GRAY + "����");
            broadcast(GRAY + "����" + victim.color().toString() + "��" + GRAY + "��������" + victim.color().toString() + "��" + GRAY + "���� El nexus " + victim.coloredName() + DARK_GRAY + " �" + RED + "" + BOLD + " FUE ELIMINADO");
            broadcast(GRAY + "����" + victim.color().toString() + "��" + GRAY + "��������" + victim.color().toString() + "��" + GRAY + "���� por " + attacker.color().toString() + p.getName());
            broadcast(GRAY + "����" + victim.color().toString() + "��" + GRAY + "��������" + victim.color().toString() + "��" + GRAY + "����");
            broadcast(GRAY + "����" + victim.color().toString() + "��" + GRAY + "��������" + victim.color().toString() + "��" + GRAY + "����");
            broadcast(GRAY + "����" + victim.color().toString() + "����������" + GRAY + "������");
            broadcast(GRAY + "��������������������");
    }

    public static String nexusBreakMessage(Player breaker, GameTeam attacker,
            GameTeam victim) {
        return colorizeName(breaker, attacker) + GRAY + " ha da�ado el "
                + victim.coloredName() + " team's Nexus!";
    }

    private static String colorizeName(Player player, GameTeam team) {
        return team.color() + player.getName();
    }

    @SuppressWarnings("static-access")
	public static void phaseMessage(int phase) {
        switch (phase) {
            case 1:
                
            broadcast(GRAY + "��������������������");
            broadcast(GRAY + "��������" + RED + "����" + GRAY + "��������");
            broadcast(GRAY + "����" + RED + "��������" + GRAY + "��������");
            broadcast(GRAY + "��������" + RED + "����" + GRAY + "��������");
            broadcast(GRAY + "��������" + RED + "����" + GRAY + "�������� El juego ha" + GOLD + " empezado!");
            broadcast(GRAY + "��������" + RED + "����" + GRAY + "�������� El nexus es" + GOLD + " invencible");
            broadcast(GRAY + "��������" + RED + "����" + GRAY + "��������");
            broadcast(GRAY + "��������" + RED + "����" + GRAY + "��������");
            broadcast(GRAY + "����" + RED + "������������" + GRAY + "����");
            broadcast(GRAY + "��������������������");
            break;
            case 2:
            broadcast(GRAY + "��������������������");
            broadcast(GRAY + "������" + RED + "��������" + GRAY + "������");
            broadcast(GRAY + "����" + RED + "��" + GRAY + "��������" + RED + "��" + GRAY + "����");
            broadcast(GRAY + "��������������" + RED + "��" + GRAY + "����");
            broadcast(GRAY + "������������" + RED + "��" + GRAY + "������ " + GOLD + "Phase2" + GRAY + " ha empezado");
            broadcast(GRAY + "����������" + RED + "��" + GRAY + "�������� El nexus ha " + GOLD + "perdido" + GRAY + " la " + GOLD + "invencibilidad");
            broadcast(GRAY + "��������" + RED + "��" + GRAY + "���������� Los " + GOLD + "bosses" + GRAY + " han " + GOLD + "aparecido");
            broadcast(GRAY + "������" + RED + "��" + GRAY + "������������");
            broadcast(GRAY + "����" + RED + "������������" + GRAY + "����");
            broadcast(GRAY + "��������������������");
            Annihilation.getInstance().getScoreboardHandler().score.get(ChatColor.DARK_PURPLE + "Fase Actual: ").setScore(PhaseManager.getPhase());
            break;
            case 3:
            broadcast(GRAY + "��������������������");
            broadcast(GRAY + "������" + RED + "��������" + GRAY + "������");
            broadcast(GRAY + "����" + RED + "��" + GRAY + "��������" + RED + "��" + GRAY + "����");
            broadcast(GRAY + "��������������" + RED + "��" + GRAY + "����");
            broadcast(GRAY + "��������" + RED + "������" + GRAY + "������ " + GOLD + "Phase3" + GRAY + " ha empezado");
            broadcast(GRAY + "��������������" + RED + "��" + GRAY + "���� " + AQUA + "Diamantes" + GRAY + " han aparecido en");
            broadcast(GRAY + "��������������" + RED + "��" + GRAY + "���� el medio del mapa");
            broadcast(GRAY + "����" + RED + "��" + GRAY + "��������" + RED + "��" + GRAY + "����");
            broadcast(GRAY + "������" + RED + "��������" + GRAY + "������");
            broadcast(GRAY + "��������������������");
            Annihilation.getInstance().getScoreboardHandler().score.get(ChatColor.DARK_PURPLE + "Fase Actual: ").setScore(PhaseManager.getPhase());
            break;
            case 4:
            broadcast(GRAY + "��������������������");
            broadcast(GRAY + "������������" + RED + "��" + GRAY + "������");
            broadcast(GRAY + "����������" + RED + "����" + GRAY + "������");
            broadcast(GRAY + "��������" + RED + "��" + GRAY + "��" + RED + "��" + GRAY + "������");
            broadcast(GRAY + "������" + RED + "��" + GRAY + "����" + RED + "��" + GRAY + "������ " + GOLD + "Phase4" + GRAY + " ha empezado");
            broadcast(GRAY + "����" + RED + "��" + GRAY + "������" + RED + "��" + GRAY + "������");
            broadcast(GRAY + "����" + RED + "������������" + GRAY + "����");
            broadcast(GRAY + "������������" + RED + "��" + GRAY + "������");
            broadcast(GRAY + "������������" + RED + "��" + GRAY + "������");
            broadcast(GRAY + "��������������������");    
            Annihilation.getInstance().getScoreboardHandler().score.get(ChatColor.DARK_PURPLE + "Fase Actual: ").setScore(PhaseManager.getPhase());
            break;
            case 5:
                broadcast(GRAY + "��������������������");
            broadcast(GRAY + "����" + RED + "������������" + GRAY + "����");
            broadcast(GRAY + "����" + RED + "��" + GRAY + "��������������");
            broadcast(GRAY + "����" + RED + "��" + GRAY + "��������������");
            broadcast(GRAY + "����" + RED + "����������" + GRAY + "������ " + GOLD + "Phase5" + GRAY + " ha empezado");
            broadcast(GRAY + "��������������" + RED + "��" + GRAY + "���� " + GOLD + "Daño extra" + GRAY + " al picar el" + GOLD + " Nexus");
            broadcast(GRAY + "����" + RED + "��" + GRAY + "��������" + RED + "��" + GRAY + "���� cuando lo rompes");
            broadcast(GRAY + "����" + RED + "��" + GRAY + "��������" + RED + "��" + GRAY + "���� " + RED + "x2" + GOLD + " da�o extra");
            broadcast(GRAY + "������" + RED + "��������" + GRAY + "������");
            broadcast(GRAY + "��������������������");;
            Annihilation.getInstance().getScoreboardHandler().score.get(ChatColor.DARK_PURPLE + "Fase Actual: ").setScore(PhaseManager.getPhase());
                        
        }
    }

    public static void winMessage(GameTeam winner) {
            broadcast(GRAY + "��������������������");
            broadcast(GRAY + "������" + winner.color().toString() + "����������" + GRAY + "����");
            broadcast(GRAY + "����" + winner.color().toString() + "��" + GRAY + "��������������");
            broadcast(GRAY + "����" + winner.color().toString() + "��" + GRAY + "��������������");
            broadcast(GRAY + "����" + winner.color().toString() + "��" + GRAY + "�������������� " + GRAY + "El equipo " + winner.coloredName() + GRAY + " ha ganado");
            broadcast(GRAY + "����" + winner.color().toString() + "��" + GRAY + "����" + winner.color().toString() + "������" + GRAY + "���� " + GRAY + "Se reiniciara en " + DARK_PURPLE + "120" + GRAY + " segundos");
            broadcast(GRAY + "����" + winner.color().toString() + "��" + GRAY + "��������" + winner.color().toString() + "��" + GRAY + "����");
            broadcast(GRAY + "����" + winner.color().toString() + "��" + GRAY + "��������" + winner.color().toString() + "��" + GRAY + "����" );
            broadcast(GRAY + "������" + winner.color().toString() + "��������" + GRAY + "������");
            broadcast(GRAY + "��������������������");
            for(Player p : Bukkit.getOnlinePlayers()) {
            TitleApi.sendFullTitle(p, 40, 40, 2, GRAY + "El equipo " + winner.coloredName() , GRAY + "ha ganado");
            }

    }

    public static void bossDeath(Boss b, Player killer, GameTeam team) {
        broadcast(ChatColor.DARK_PURPLE + "MATARON AL BOSS - " + GRAY + b.getBossName() + GRAY + "  "
                + colorizeName(killer, team));
    }

    public static void bossRespawn(Boss b) {
            broadcast(GRAY + "��������������������");
            broadcast(GRAY + "������" + RED + "��������" + GRAY + "������");
            broadcast(GRAY + "����" + RED + "��" + GRAY + "��������" + RED + "��" + GRAY + "����");
            broadcast(GRAY + "����" + RED + "��" + GRAY + "��������" + RED + "��" + GRAY + "����");
            broadcast(GRAY + "����" + RED + "��" + GRAY + "������" + RED + "��" + GRAY + "������" + GOLD + "El Boss" + RED + b.getBossName());
            broadcast(GRAY + "����" + RED + "��" + GRAY + "��������" + RED + "��" + GRAY + "����" + GOLD + "Ha aparecido, a por el!");
            broadcast(GRAY + "����" + RED + "��" + GRAY + "��������" + RED + "��" + GRAY + "����" + GRAY + "Ahora dan cosas chetadas");
            broadcast(GRAY + "����" + RED + "��" + GRAY + "��������" + RED + "��" + GRAY + "����");
            broadcast(GRAY + "������" + RED + "��������" + GRAY + "������");
            broadcast(GRAY + "��������������������");
    }

    public static String formatDeathMessage(Player victim, Player killer,
            String original) {
        Player p = killer.getPlayer();
        p.getName();
        PlayerMeta meta = PlayerMeta.getMeta(p);
        Kit kit = meta.getKit();
        GameTeam killerTeam = PlayerMeta.getMeta(killer).getTeam();
        String killerColor = killerTeam != null ? killerTeam.color().toString()
                : ChatColor.DARK_PURPLE.toString();
        String killerName = killerColor + killer.getName() + "(" + kit + ")" + ChatColor.GRAY;

        String message = ChatColor.GRAY + formatDeathMessage(victim, original);
        message = message.replace(killer.getName(), killerName);

        return message;
    }

    public static String formatDeathMessage(Player victim, String original) {
        Player p = victim.getPlayer();
        p.getName();
        PlayerMeta meta = PlayerMeta.getMeta(p);
        Kit kit = meta.getKit();
        GameTeam victimTeam = PlayerMeta.getMeta(victim).getTeam();
        String victimColor = victimTeam != null ? victimTeam.color().toString()
                : ChatColor.DARK_PURPLE.toString();
        String victimName = victimColor + victim.getName() + "(" + kit  + ")" + ChatColor.GRAY;

        String message = ChatColor.GRAY + original;
        message = message.replace(victim.getName(), victimName);

        if (message.contains(" �8�")) {
            String[] arr = message.split(" �8�");
            message = arr[0];
        }

        return message.replace("ha sido asesinado por", "ha muerto por");
    }
    
  
}