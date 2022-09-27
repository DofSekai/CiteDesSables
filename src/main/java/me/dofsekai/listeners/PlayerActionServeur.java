package me.dofsekai.listeners;

import me.dofsekai.Main;
import me.dofsekai.core.Profile;
import me.dofsekai.core.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.UUID;

public class PlayerActionServeur implements Listener {

    private HashMap<UUID, String> messages = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        if (!Profile.hasProfile(player.getUniqueId())) {
            new Profile(player.getName(), player.getUniqueId());
        }
    }

    @EventHandler
    public void onTalk(AsyncPlayerChatEvent e) {
        final Player player = e.getPlayer();
        final String message = e.getMessage();
        messages.put(player.getUniqueId(), message);
        final Profile profile = Profile.getProfileOfPlayer(player.getUniqueId());
        final Team team = Team.getTeamOf(player.getUniqueId());

        switch(profile.getPlayerstate()) {
            case CREATING_TEAM:
                e.setCancelled(true);
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable() {
                    public void run() {
                        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "cteam create " + player.getName() + " " + messages.get(player.getUniqueId()));
                    }
                });
                break;
            case NOTHING:
                if (team == null) e.setFormat("[none] " + e.getFormat());
                else e.setFormat("[" + team.getName() + "] " + e.getFormat());
                break;
            default:
                break;
        }
    }
}
