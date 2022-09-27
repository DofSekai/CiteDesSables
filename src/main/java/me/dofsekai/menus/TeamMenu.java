package me.dofsekai.menus;

import me.dofsekai.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class TeamMenu {

    public static Inventory General() {
        Inventory inv = Bukkit.createInventory(null, 9, "Menu Team");
        inv.setItem(0, ItemBuilder.getItem(Material.PAPER, "Création de Team", null));
        inv.setItem(1, ItemBuilder.getItem(Material.PAPER, "Inviter dans la Team", null));
        inv.setItem(2, ItemBuilder.getItem(Material.PAPER, "Rejoindre une Team", null));
        return inv;
    }
}
