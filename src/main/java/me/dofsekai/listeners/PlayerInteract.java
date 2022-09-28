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
                        if (Team.getTeamOf(player.getUniqueId()) != null) {
                            player.sendMessage("T'a déjà une team toi !!");
                            break;
                        }
                        player.sendMessage("Ecrit ta team dans le chat.");
                        Profile.getProfileOfPlayer(player.getUniqueId()).setPlayerstate(PlayerState.CREATING_TEAM);
                        break;
                    case "Inviter dans la Team":
                        player.closeInventory();
                        if (Team.getTeamOf(player.getUniqueId()) == null) {
                            player.sendMessage("Tu n'as pas de team !!");
                            break;
                        }
                        Team team = Team.getTeamOf(player.getUniqueId());
                        if (!team.isLeader(player.getUniqueId())) {
                            player.sendMessage("Le leader c'est pas toi !!");
                            break;
                        }
                        Profile.getProfileOfPlayer(player.getUniqueId()).setPlayerstate(PlayerState.INVITE_PLAYER);
                        TeamMenu.Invite(player);
                        break;
                    default:
                        break;
                }
            default:
                break;
        }

        if(e.getInventory().getType() == InventoryType.ANVIL && viewing.getTitle().equalsIgnoreCase("Joueur à inviter !")) {
            if(e.getInventory().getItem(2) == null) return;
            String playerHeadInvited = e.getInventory().getItem(2).getItemMeta().getDisplayName();
            if (Bukkit.getPlayer(playerHeadInvited) == null) return;
            UUID playerInvitedUUID = Bukkit.getPlayer(playerHeadInvited).getUniqueId();
            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "cteam invite " + player.getName() + " " + Bukkit.getPlayer(playerInvitedUUID).getName());
        }
    }
}
