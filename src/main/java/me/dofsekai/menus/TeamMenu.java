package me.dofsekai.menus;

import me.dofsekai.Main;
import me.dofsekai.core.PlayerState;
import me.dofsekai.core.Profile;
import me.dofsekai.core.Team;
import me.dofsekai.utils.ItemBuilder;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TeamMenu {

    public static Inventory General() {
        Inventory inv = Bukkit.createInventory(null, 9, "Menu Team");
        inv.setItem(0, ItemBuilder.getItem(Material.PAPER, "Création de Team", null));
        inv.setItem(1, ItemBuilder.getItem(Material.PAPER, "Inviter dans la Team", null));
        inv.setItem(2, ItemBuilder.getItem(Material.PAPER, "Rejoindre une Team", null));
        return inv;
    }

    public static void Invite(Player player) {
        new AnvilGUI.Builder()
                .onClose(players -> {
                    Profile.getProfileOfPlayer(players.getUniqueId()).setPlayerstate(PlayerState.NOTHING);
                })
                .onComplete((players, text) -> {
                    if(text == null) return AnvilGUI.Response.close();
                    return AnvilGUI.Response.close();
                })
                .text("Entrer le joueur ici")
                .itemLeft(new ItemStack(Material.PLAYER_HEAD))
                .title("Joueur à inviter !")
                .plugin(Main.getPlugin(Main.class))
                .open(player);
    }

    public static Inventory Join() {
        int invSize = (int) (Team.getAllTeams().size() / 9) + 1;
        Inventory inv = Bukkit.createInventory(null, invSize * 9, "Team List");
        List<String> joinTeam = new ArrayList<>();
        joinTeam.add("Clique ici pour rejoindre");
        for (Team t : Team.getAllTeams()) {
            for (int i = 0; i < t.getInvited().size(); i++) {
                inv.setItem(i, ItemBuilder.getItem(Material.PAPER, t.getName(), joinTeam));
            }
        }
        return inv;
    }
}
