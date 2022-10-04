package me.dofsekai.listeners;

import me.dofsekai.core.PlayerState;
import me.dofsekai.core.Profile;
import me.dofsekai.core.Team;
import me.dofsekai.menus.TeamMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PlayerInteract implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getCurrentItem() == null) return;
        final InventoryView viewing = e.getView();
        final Player player = (Player) e.getWhoClicked();
        final ItemStack currentItem = e.getCurrentItem();

        switch(viewing.getTitle()) {
            case "Menu Team":
                e.setCancelled(true);
                switch(currentItem.getItemMeta().getDisplayName()) {
                    case "Création de Team":
                        player.closeInventory();
                        if (Team.getTeamOf(player) != null) {
                            player.sendMessage("T'a déjà une team toi !!");
                            break;
                        }
                        player.sendMessage("Ecrit ta team dans le chat.");
                        Profile.getProfileOfPlayer(player.getUniqueId()).setPlayerstate(PlayerState.CREATING_TEAM);
                        break;
                    case "Inviter dans la Team":
                        player.closeInventory();
                        if (Team.getTeamOf(player) == null) {
                            player.sendMessage("Tu n'as pas de team !!");
                            break;
                        }
                        Team team = Team.getTeamOf(player);
                        if (!team.getLeader().equals(player)) {
                            player.sendMessage("Le leader c'est pas toi !!");
                            break;
                        }
                        Profile.getProfileOfPlayer(player.getUniqueId()).setPlayerstate(PlayerState.INVITE_PLAYER);
                        TeamMenu.Invite(player);
                        break;
                    case "Rejoindre une Team":
                        player.closeInventory();
                        int invitations = 0;
                        if (Team.getTeamOf(player) != null) {
                            player.sendMessage("T'a déjà une team toi !!");
                            break;
                        }
                        for (Team t : Team.getAllTeams()) {
                            for (int i = 0; i < Team.getAllTeams().size(); i++) {
                                if (t.isInvited(player.getUniqueId())) invitations += 1;
                            }
                        }
                        if (invitations == 0) {
                            player.sendMessage("Aucune team t'a invité");
                            break;
                        }
                        Profile.getProfileOfPlayer(player.getUniqueId()).setPlayerstate(PlayerState.JOINING_TEAM);
                        player.openInventory(TeamMenu.Join());
                        break;
                    default:
                        break;
                }
            case "Team List":
                e.setCancelled(true);
                if (Team.getTeamByName(currentItem.getItemMeta().getDisplayName()) == null) break;
                Team team = Team.getTeamByName(currentItem.getItemMeta().getDisplayName());
                if (!team.addMembers(player)) {
                    player.sendMessage("La team est déjà complète");
                    break;
                }
                team.addMembers(player);
                player.closeInventory();
                break;
            default:
                break;
        }

        if(e.getInventory().getType() == InventoryType.ANVIL && viewing.getTitle().equalsIgnoreCase("Joueur à inviter !")) {
            if(e.getInventory().getItem(2) == null) return;
            String playerHeadInvited = e.getInventory().getItem(2).getItemMeta().getDisplayName();
            if (Bukkit.getPlayer(playerHeadInvited) == null) {
                player.sendMessage("Ce connard n'est pas connecté !");
                return;
            }
            UUID playerInvitedUUID = Bukkit.getPlayer(playerHeadInvited).getUniqueId();
            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "cteam invite " + player.getName() + " " + Bukkit.getPlayer(playerInvitedUUID).getName());
        }
    }
}
