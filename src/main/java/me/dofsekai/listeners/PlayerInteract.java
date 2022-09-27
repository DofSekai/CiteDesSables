package me.dofsekai.listeners;

import me.dofsekai.core.PlayerState;
import me.dofsekai.core.Profile;
import me.dofsekai.core.Team;
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
                        // new AnvilGUI.Builder()
                        //     .onClose(players -> {
                        //         players.sendMessage("closed");
                        //     })
                        //     .onComplete((players, text) -> {
                        //         if(text.equalsIgnoreCase("you")) {
                        //             players.sendMessage("You have magical powers!");
                        //             return AnvilGUI.Response.close();
                        //         } else {
                        //             return AnvilGUI.Response.text("Incorrect.");
                        //         }
                        //     })
                        //     .preventClose()
                        //     .text("What is the meaning of life?")
                        //     .itemLeft(new ItemStack(Material.IRON_SWORD))
                        //     .itemRight(new ItemStack(Material.IRON_SWORD))
                        //     .onLeftInputClick(players -> players.sendMessage("first sword"))
                        //     .onRightInputClick(players -> players.sendMessage("second sword"))
                        //     .title("Enter your answer.")
                        //     .plugin((Plugin) Main.getInstance())
                        //     .open(player);
                    default:
                        break;
                }
            default:
                break;
        }
    }
}
