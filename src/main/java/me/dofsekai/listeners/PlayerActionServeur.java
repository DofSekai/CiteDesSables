package me.dofsekai.listeners;

import me.dofsekai.Main;
import me.dofsekai.core.PlayerState;
import me.dofsekai.core.Profile;
import me.dofsekai.core.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import java.util.HashMap;
import java.util.UUID;

public class PlayerActionServeur implements Listener {

    private HashMap<UUID, String> messages = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        if (!Profile.hasProfile(player.getUniqueId())) {
            new Profile(player.getName(), player.getUniqueId());
            System.out.println("profile créé");
        } else {
            System.out.println("profile déjà créé");
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        final Player player = e.getPlayer();
        for (Team team : Team.getAllTeams()) {
            team.removeInvite(player.getUniqueId());
        }
    }

    @EventHandler
    public void onTalk(AsyncPlayerChatEvent e) {
        final Player player = e.getPlayer();
        final String message = e.getMessage();
        if(Team.hasSpecialCharacters(message)) {
            e.setCancelled(true);
            player.sendMessage("Erreur : ta team possède des caractères spéciaux");
            return;
        }
        if (message.length() > Team.getMaxCharactersName()) {
            e.setCancelled(true);
            player.sendMessage("Erreur : la limite de caractères est fixée à 15");
            return;
        }
        messages.put(player.getUniqueId(), message);
        final Profile profile = Profile.getProfileOfPlayer(player.getUniqueId());
        final Team team = Team.getTeamOf(player);

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

    @EventHandler
    public void onCloseInventory(InventoryCloseEvent e) {
        final Player player = (Player) e.getPlayer();
        final InventoryView viewing = e.getView();

        if (viewing.getTitle().equalsIgnoreCase("Team List")) {
            Profile.getProfileOfPlayer(player.getUniqueId()).setPlayerstate(PlayerState.NOTHING);
        }
    }
}
