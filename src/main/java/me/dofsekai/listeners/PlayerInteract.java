package me.dofsekai.listeners;

import me.dofsekai.core.PlayerState;
import me.dofsekai.core.Profile;
import me.dofsekai.core.Team;
import me.dofsekai.menus.TeamMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class PlayerInteract implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getCurrentItem() == null) return;
        final InventoryView invview = e.getView();
        final Player player = (Player) e.getWhoClicked();
        final ItemStack currentItem = e.getCurrentItem();

        switch(invview.getTitle()) {
            case "Menu Team":
                e.setCancelled(true);
                switch(currentItem.getItemMeta().getDisplayName()) {
                    case "Création de Team":
                        player.closeInventory();
                        if (Team.getTeamOf(player.getUniqueId()) != null) {
                            player.sendMessage("Vous êtes déjà dans une team.");
                            break;
                        }
                        player.sendMessage("Ecrit ta team dans le chat.");
                        Profile.getProfileOfPlayer(player.getUniqueId()).setPlayerstate(PlayerState.CREATING_TEAM);
                    case "Inviter dans la Team":
                        player.closeInventory();
                        Profile.getProfileOfPlayer(player.getUniqueId()).setPlayerstate(PlayerState.INVITE_PLAYER);
                        TeamMenu.Invite(player);
                    default:
                        break;
                }
            default:
                break;
        }
    }
}
